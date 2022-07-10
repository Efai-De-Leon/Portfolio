#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/select.h>
#include <assert.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <time.h>
#include <math.h>
#include <stdint.h>
#include "TestServer.h"
#include "GlobalDefs.h"
#include "user.h"
#include "send.h"

#define BUFFSIZE 512

const char *Program = NULL;
int Shutdown;
int SignUp = 0;
int SignIn = 0;

// Used to print critical error diagnostic
void FatalError(const char *ErrorMsg) {
#ifdef DEBUG
	printf("FatalError() is called!\n");
#endif
	fputs(Program, stderr);
	fputs(": ", stderr);
	perror(ErrorMsg);
	fputs(Program, stderr);
	fputs(": Exiting!\n", stderr);
	exit(20);
}

//Code from sample server
//Creates Socket on the Server 
int MakeServerSocket(uint16_t PortNo) {
#ifdef DEBUG
	printf("INIT: MakeServerSocket() is called.\n");
#endif
	int SocketFD;

	//struct is in netinet/in.h
	struct sockaddr_in ServSocketName;

	//Create the socket
	SocketFD = socket(PF_INET, SOCK_STREAM, 0);
	if (SocketFD < 0) {
		FatalError("Socket could not be created!\n");
	}

	ServSocketName.sin_family = AF_INET;
	ServSocketName.sin_port = htons(PortNo);
	ServSocketName.sin_addr.s_addr = htonl(INADDR_ANY);
	if (bind(SocketFD, (struct sockaddr*)&ServSocketName,
		sizeof(ServSocketName)) < 0)
	{
		FatalError("binding the server to a socket failed");
	}
	if (listen(SocketFD, 5) < 0) {
		FatalError("listening on socket failed");
	}
#ifdef DEBUG
	printf("INIT: MakeServerSocket() is finished.\n");
#endif
	return SocketFD;
}

//Function to handle all the requests
void ProcessRequest(int DataSocketFD, fd_set ReadFDs, RUNLIST *list) {

#ifdef DEBUG
	printf("FUNC: ProcessRequest() is called.\n");
#endif

	int l, n, check;
	char RecvBuf[BUFFSIZE];
	char SendBuf[BUFFSIZE];

	n = read(DataSocketFD, RecvBuf, sizeof(RecvBuf) - 1);
	if (n < 0) {
		FatalError("reading from data socket failed");
	}
	RecvBuf[n] = 0;
#ifdef DEBUG
	printf("Recieved Messaage: %s\n", RecvBuf);
#endif
	if (0 == strcmp(RecvBuf, "server shutdown"))
	{
		Shutdown = 1;
#ifdef DEBUG
		printf("Received 'shutdown' message from client.\n");
#endif
		strncpy(SendBuf, "server shutdown", sizeof(SendBuf) - 1);
		SendBuf[sizeof(SendBuf) - 1] = 0;
	}
	else if ((strcmp(RecvBuf, "SignUp") == 0) || (strcmp(RecvBuf, "SignIn") == 0) || (SignIn != 0) || (SignUp != 0))
	{
#ifdef DEBUG
		printf("FUNC: Client is attempting to sign up or sign in.\n");
#endif
		if (SignUp == 0 && SignIn == 0)
		{
			strncpy(SendBuf, "Plase Enter UserName and PassWord", sizeof(SendBuf) - 1);
			if (strcmp(RecvBuf, "SignUp") == 0)
			{
				SignUp = 2;
			}
			else if (strcmp(RecvBuf, "SignIn") == 0)
			{
				SignIn = 2;
			}
		}
		else
		{
			char *UP[2];
			char *temp = strtok(RecvBuf, " ");;
			int i = 0;
			while (i < 2)
			{
				UP[i] = temp;
				temp = strtok(NULL, " ");
				i++;
			}
			char *UserName = UP[0];
			char *PassWord = UP[1];

			if (SignUp != 0)
			{
				char *readuser = reading_userlist();
#ifdef DEBUG
                                printf("checking the list\n");
#endif
				int check = UsernameExists(UserName, readuser);
#ifdef DEBUG
				printf("checking\n");
#endif
				if (check == FALSE)
				{
					CreateNewAcc(UserName, PassWord);
					strncpy(SendBuf, "Created Account\n", sizeof(SendBuf) - 1);
                                        strncpy(SendBuf, "success", sizeof(SendBuf) - 1);                                
				}
				else
				{
					strncpy(SendBuf, "User name already exist, please type SignUp again and reenter a username\n", sizeof(SendBuf) - 1);
                                        strncpy(SendBuf, "failed", sizeof(SendBuf) - 1); 
				}
				SignUp = 0;

				if (readuser)
				{
					free(readuser);
				}
			}
			else if (SignIn != 0)
			{
				int check2 = AbleLogin(UserName, PassWord);
				printf("LOGIN: Successfully checked!\n");
				if (check2 == TRUE)
//signing in successful
//creates an entry
				{
					strncpy(SendBuf, "Successfully Logged In!\n", sizeof(SendBuf) - 1);
					USER *temp = CreateReturnUser(UserName);
					MSG *msglist = CreateMsgList(void);
					
					AppendEntry(temp, list, msglist, DataSocketFD);
				}
				else
				{
					strncpy(SendBuf, "User name and password do not match, please reenter choice!\n", sizeof(SendBuf) - 1);
				}
				SignIn = 0;
			}
		}
		SendBuf[sizeof(SendBuf) - 1] = 0;
	}
	else
	{
#ifdef DEBUG
		printf("FUNC: ProcessRequest() Client is attempting to send messages.\n");
#endif
		if (SendToMessage(RecvBuf) == TRUE) {
			if (WriteIntendedUser(RecvBuf, list) == 0) {
				strncpy(SendBuf, "Could not find User\n", sizeof(SendBuf) - 1);
				SendBuf[sizeof(SendBuf) - 1] = 0;
			}
			else{
				SendingMessages(RecvBuf, list, DataSocketFD);
#ifdef DEBUG
				printf("User was found\n");
#endif
				strncpy(SendBuf, "Message Sent successfully.\n", sizeof(SendBuf) - 1);
				SendBuf[sizeof(SendBuf) - 1] = 0;
			}
		}
		
		if (strcmp(RecvBuf, "request") == 0) {
			RetrieveMessages(DataSocketFD, SendBuf, list);			
		}

		if(strcmp(Recv, "addto") == 0){
			if (WriteIntendedUser(RecvBuf, list) == 0) {
                                strncpy(SendBuf, "Could not find User\n", sizeof(SendBuf) - 1);
                                SendBuf[sizeof(SendBuf) - 1] = 0;
                        }
			else{
#ifdef DEBUG
                                printf("User was found\n");
#endif
				SendFriendRequest(RecvBuf, list, DataSocketFD);		
			}
		}

		if(strcmp(RecvBuf, "acceptfriend") == 0){

		}
	}
	l = strlen(SendBuf);
	printf("Sending response: %s.\n", SendBuf);

	n = write(DataSocketFD, SendBuf, l);
	if (n < 0)
	{
		FatalError("writing to data socket failed");
	}
}

//Main Loop p
int main(int argc, char *argv[]) {
	printf("INIT: Starting Server File \"%s\"\n", Program);
	//Main variables
	int ServSocketFD;
	int PortNo;
	Shutdown = 0;
	Program = argv[0];

#ifdef DEBUG
	printf("INIT: Attempting to read arguments.\n");
#endif
	//Checking if port is entered and valid
	if (argc < 2) {
		fprintf(stderr, "No port number entered. Type \"%s portnumber\"\n", Program);
		exit(10);
	}
	PortNo = atoi(argv[1]);
	if (PortNo <= 2000) {
		fprintf(stderr, "Invalid port number. Enter a number over 2000\n");
		exit(10);
	}

//	*************Socket Creating***************
	printf("INIT: Creating server socket with port number %d\n", PortNo);
	ServSocketFD = MakeServerSocket((uint16_t)PortNo);


#ifdef DEBUG
	printf("INIT: About to start multiple client initialization.\n");
#endif
	//	************Multiple Clients handling**************** 
	// Some Logic for handling multiple clients
	int DataSocketFD; // socket for new client
	socklen_t ClientLen;
	struct sockaddr_in ClientAddress;
#ifdef DEBUG
	printf("INIT: Just passed sockaddr_in statement.\n");
#endif

	fd_set ActiveFDs;
	fd_set ReadFDs;
#ifdef DEBUG
	printf("INIT: Just Passed fd_set statements\n");
#endif 

	int Timeout = 250000;
	struct timeval TimeVal;
	TimeVal.tv_sec = Timeout / 1000000;
	TimeVal.tv_usec = Timeout % 1000000;
#ifdef DEBUG
	printf("INIT: Just passed timeval statements.\n");
#endif 

	FD_ZERO(&ActiveFDs);
	FD_SET(ServSocketFD, &ActiveFDs);
#ifdef DEBUG
	printf("INIT: Just passed FD_ZERO and FD_SET.\n");
#endif 

	RUNLIST *list = CreateRunList();
#ifdef DEBUG
	printf("INIT: Just Created RUNLIST Struct.\n");
#endif

	create_userlist();
#ifdef DEBUG
	printf("INIT: Created UserList file.\n");
#endif
	int res, i;
	
//	************ Loop starts here ************
	printf("INIT: Starting Main Loop. Server is now running.\n");
	do {
		ReadFDs = ActiveFDs;

		res = select(FD_SETSIZE, &ReadFDs, NULL, NULL, NULL);
		if (res < 0)
		{
			FatalError("wait for input or timeout (select) failed");
		}
		else {
#ifdef DEBUG
			printf("LOOP: Res just assigned and is greater than 0. \n");
#endif
			for (i = 0; i<FD_SETSIZE; i++)
			{
				if (FD_ISSET(i, &ReadFDs))
				{
					if (i == ServSocketFD)
					{
#ifdef DEBUG
						printf("LOOP: %s: Accepting new client...\n", Program);
#endif
						ClientLen = sizeof(ClientAddress);
						DataSocketFD = accept(ServSocketFD,
							(struct sockaddr*)&ClientAddress, &ClientLen);
						if (DataSocketFD < 0)
						{
							FatalError("data socket creation (accept) failed");
						}
#ifdef DEBUG
						printf("LOOP: %s: New client connected from %s:%hu.\n",
							Program, inet_ntoa(ClientAddress.sin_addr), ntohs(ClientAddress.sin_port));				
							printf("LOOP: %s: New Client has socketFD assigned to: %d...\n", Program, i);
#endif
						FD_SET(DataSocketFD, &ActiveFDs);
					}
					else
					{
#ifdef DEBUG
						printf("LOOP: %s: Dealing with client on FD%d...\n", Program, i);
#endif
						ProcessRequest(i, ReadFDs, list);


						//FD_CLR(i, &ActiveFDs);
					}
				}
			}
		}

	} while (Shutdown == 0);


	printf("%s: Shutting down.\n", argv[0]);
	close(ServSocketFD);
	return 0;
}

