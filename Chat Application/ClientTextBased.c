#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <netinet/in.h>
#include <netdb.h>
//#include <gtk/gtk.h>
#include <assert.h>
#include "TestClient.h"
//#include "send.h"

#define BUFFSIZE 512

/*** global variables ****************************************************/

const char *Program = NULL;
struct sockaddr_in ServerAddress;
int Shutdown = 0;

char *Answer;

void FatalError(const char *ErrorMsg)
{
//	fputs(Program, stderr);
	fputs(": ", stderr);
	perror(ErrorMsg);
//	fputs(Program, stderr);
	fputs(": Exiting!\n", stderr);
	exit(20);
}

void PingServer(const char *Message, char *RecvBuf, int SocketFD) {
	int n;
	
	n = write(SocketFD, Message, strlen(Message));
	if (n < 0)
	{
		FatalError("writing to socket failed");
	}
#ifdef DEBUG
	printf("Waiting for response...\n");
#endif
	n = read(SocketFD, RecvBuf, BUFFSIZE - 1);
	if (n < 0)
	{
		FatalError("reading from socket failed");
	}
	RecvBuf[n] = 0;
#ifdef DEBUG
	printf("Received response: %s\n", RecvBuf);
//	printf("%s: Closing the connection...\n", Program);
#endif
//	close(SocketFD);
//	return(RecvBuf);

}

void SendMessage(char *SendBuf, char *RecvBuf, int SocketFD) {

	PingServer(SendBuf, RecvBuf, SocketFD);
}

void CloseClient(char *Sendbuf, char *RecvBuf, int SocketFD) {
	const char *Message = "Client has requested to shutdown.";

	PingServer(Message, RecvBuf, SocketFD);

}

void CloseServer(char *Sendbuf, char *RecvBuf, int SocketFD) {
	const char *Message = "server shutdown";

	PingServer(Message, RecvBuf, SocketFD);
}

int main(int argc, char *argv[]) {
	int PortNo;
	int SocketFD;
	struct hostent *Server;
int login = 0;
	char RecvBuf[BUFFSIZE];
	char SendBuf[BUFFSIZE];

	int l;

	Program = argv[0];
#ifdef DEBUG
	printf("%s: Starting...\n", argv[0]);
#endif
	if (argc < 3)
	{
		fprintf(stderr, "Usage: %s hostname port\n", Program);
		exit(10);
	}
	Server = gethostbyname(argv[1]);
	if (Server == NULL)
	{
		fprintf(stderr, "%s: no such host named '%s'\n", Program, argv[1]);
		exit(10);
	}
	PortNo = atoi(argv[2]);
	if (PortNo <= 2000)
	{
		fprintf(stderr, "%s: invalid port number %d, should be >2000\n",
			Program, PortNo);
		exit(10);
	}
	ServerAddress.sin_family = AF_INET;
	ServerAddress.sin_port = htons(PortNo);
	ServerAddress.sin_addr = *(struct in_addr*)Server->h_addr_list[0];

	SocketFD = socket(AF_INET, SOCK_STREAM, 0);
	if (SocketFD < 0)
	{
		FatalError("socket creation failed");
	}

#ifdef DEBUG
	printf("%s: Connecting to the server at port %d...\n",
		Program, ntohs(ServerAddress.sin_port));
#endif
	if (connect(SocketFD, (struct sockaddr*)&ServerAddress,
		sizeof(struct sockaddr_in)) < 0)
	{
		FatalError("connecting to server failed");
	}

	//********** Client Loop **********
	do {
		if (login == 0) {
			printf("%s: Enter 'bye' to quit this client,\n"
				"     or 'shutdown' to quit both server and client,\n"
				"	  or 'SignUp' to sign up a new user,\n"
				"	  or 'SignIn' if you already have an account and wish to sign in,\n"
				"message: ", argv[0]);
			fgets(SendBuf, sizeof(SendBuf), stdin);

			l = strlen(SendBuf);
			if (SendBuf[l - 1] == '\n')
			{
				SendBuf[--l] = 0;
			}

			
			if (strcmp("SignUp", SendBuf) == 0)
			{
				PingServer(SendBuf, RecvBuf, SocketFD);
			}
			else if (strcmp("SignIn", SendBuf) == 0)
			{
				PingServer(SendBuf, RecvBuf, SocketFD);
			}
			else if (strcmp("bye", SendBuf) == 0) {
				CloseClient(SendBuf, RecvBuf, SocketFD);
				Shutdown = 1;
			}
			else if (strcmp("shutdown", SendBuf) == 0) {
				CloseServer(SendBuf, RecvBuf, SocketFD);
				Shutdown = 1;
			}
			else {
				SendMessage(SendBuf, RecvBuf, SocketFD);
				if (strcmp(RecvBuf, "Successfully Logged In!\n") == 0) {
					login = 1;
				}
			}
		}
		else {
			printf("%s: enter a message to send to the server,\n"
				"         or 'sendto <user> <message>' to send to that specific user,\n"
				"         or 'request' to retrieve messages sent to you, \n"
				"         or 'bye' to quit this client,\n"
				"         or 'shutdown' to quit both server and client,\n"
				"message: ", argv[0]);
			fgets(SendBuf, sizeof(SendBuf), stdin);
      l = strlen(SendBuf);
			if (SendBuf[l - 1] == '\n')
			{
				SendBuf[--l] = 0;
			}

			if(strcmp("bye", SendBuf) == 0) {
				CloseClient(SendBuf, RecvBuf, SocketFD);
				Shutdown = 1;
			}
			else if (strcmp("shutdown", SendBuf) == 0) {
				CloseServer(SendBuf, RecvBuf, SocketFD);
				Shutdown = 1;
			}
			else {
				SendMessage(SendBuf, RecvBuf, SocketFD);
			}
		}

	} while (Shutdown == 0);
	printf("%s: Exiting...\n", argv[0]);
	close(SocketFD);
	return 0;
}
