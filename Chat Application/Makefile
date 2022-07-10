# SOURCE MAKEFILE
# Roll Two Die
# VersionNo: 0.1
# VsCreator: Justin Kim

# GTK Compiler Flags 
GTKINC  = `pkg-config --cflags gtk+-2.0`
GTKLIBS = `pkg-config --libs gtk+-2.0`

#Debug Flags
DEBUG = -g -DDEBUG
CDEBUG = -g -DDEBUG -DCDEBUG

#Compiling Flags 
CFLAGS  = -Wall -ansi -std=c99 $(GTKINC) -c
LFLAGS  = -Wall -ansi -std=c99 $(GTKLIBS)

all: Server ServerText Client ClientText

test: TestServer TestClient TestServerText TestClientText

test-gui: TestServer TestClient

test-comm: TestServerText TestClientText

#---- Object Compilation ----
user.o: user.c user.h GlobalDefs.h
	gcc $(CFLAGS) user.c -o user.o

Client.o: TestClient.h TestClient.c GlobalDefs.h
	gcc $(CFLAGS) TestClient.c -o Client.o
	
send.o: send.c send.h user.h GlobalDefs.h
	gcc $(CFLAGS) send.c -o send.o
	
GTK.o: GTK.c user.h TestClient.h GlobalDefs.h
	gcc $(CFLAGS) GTK.c -o GTK.o

ServerGTK.o: TestClient.h TestServerGTK.c TestServer.h GlobalDefs.h send.h user.h
	gcc $(CFLAGS) TestServerGTK.c -o ServerGTK.o  
	
ServerText.o: TestServer.c TestServer.h send.c user.h GlobalDefs.h
	gcc $(CFLAGS) TestServer.c -o ServerText.o 

ClientText.o: ClientTextBased.c TestClient.h send.h user.h GlobalDefs.h
	gcc $(CFLAGS) ClientTextBased.c -o ClientText.o
	
#---- Executable Compilation
Server: ServerGTK.o send.o user.o
	gcc $(LFLAGS) ServerGTK.o send.o user.o -o ChatServer
	
Client: GTK.o user.o send.o Client.o
	gcc $(LFLAGS) GTK.o user.o send.o Client.o -o ChatClient

ServerText: ServerText.o user.o send.o
	gcc $(LFLAGS) user.o ServerText.o send.o -o ChatServerText
	
ClientText: ClientText.o send.o user.o
	gcc $(LFLAGS) ClientText.o send.o user.o -o ChatClientText
	
#------------------------------   DEBUG    ------------------------------------
#Object Compilation
TestUser.o: user.c user.h GlobalDefs.h
	gcc $(CFLAGS) $(DEBUG) user.c -o TestUser.o
	
TestSend.o: send.c send.h user.h GlobalDefs.h
	gcc $(CFLAGS) $(DEBUG) send.c -o TestSend.o
	
TestClient.o: TestClient.c TestClient.h GlobalDefs.h
	gcc $(CFLAGS) $(DEBUG) TestClient.c -o TestClient.o
	
TestClientGTK.o: GTK.c user.h TestClient.h GlobalDefs.h
	gcc $(CFLAGS) $(DEBUG) GTK.c -o TestClientGTK.o

TestServerGTK.o: TestClient.h TestServerGTK.c TestServer.h GlobalDefs.h send.h user.h
	gcc $(CFLAGS) $(DEBUG) TestServerGTK.c -o TestServerGTK.o  
	
TestServerText.o: TestServer.c TestServer.h send.c user.h GlobalDefs.h
	gcc $(CFLAGS) $(DEBUG) TestServer.c -o TestServerText.o 

TestClientText.o: ClientTextBased.c TestClient.h send.h user.h GlobalDefs.h
	gcc $(CFLAGS) $(DEBUG) ClientTextBased.c -o TestClientText.o

#Executable Compilation
TestServer: TestServerGTK.o TestSend.o TestUser.o
	gcc $(LFLAGS) $(DEBUG) TestServerGTK.o TestSend.o TestUser.o -o TestChatServer
	
TestClient: TestClientGTK.o TestUser.o TestSend.o TestClient.o
	gcc $(LFLAGS) $(DEBUG) TestClientGTK.o TestUser.o TestSend.o TestClient.o -o TestChatClient

TestServerText: TestServerText.o TestUser.o TestSend.o
	gcc $(LFLAGS) $(DEBUG) TestUser.o TestServerText.o TestSend.o -o TestChatServerText
	
TestClientText: TestClientText.o TestSend.o TestUser.o
	gcc $(LFLAGS) $(DEBUG) TestClientText.o TestUser.o TestSend.o -o TestChatClientText
	
#------------------------------------------------------------------------------------
clean: 
	rm -f *.o
	rm -f TestChatClient
	rm -f TestChatServer
	rm -f TestChatClientText
	rm -f TestChatServerText
	rm -f ChatClient
	rm -f ChatServer
	rm -f ChatClientText
	rm -f ChatServerText
 
	
