# Import socket module
from socket import *
import _thread

# celintService function gets client address and client socket as inputs
# then reads the data from client socket and process the request
# in multi thread programming, each thread call this function when it is created
def clientService(clientAdd, connectionSocket):
    # If an exception occurs during the execution of try clause
    # the rest of the clause is skipped
    # If the exception type matches the word after except
    # the except clause is executed
    try:
        message = connectionSocket.recv(1024)
        message_split = message.split()
        if len(message_split) <= 1:
            # Small connection from browser - ignore
            connectionSocket.close()
            return
            
        filename = message_split[1]
        f = open(filename[1:], "rb")
        outputdata = f.read()
        
        # Send the first HTTP header line into socket
        connectionSocket.send(b'HTTP/1.1 200 OK \r\n\r\n')
        
        # Send the content of the requested file to the client
        connectionSocket.send(outputdata)
        
        # Close client socket
        connectionSocket.close()


    except IOError:
        # Send response message for file not found
        connectionSocket.send(b'HTTP/1.1 404 Not Found\r\n\r\n')
        connectionSocket.send(b'<html><head></head><body><h1>404 Not Found</h1></body></html>')
                
        # Close client socket
        connectionSocket.close()


# create an IPv4 TCP socket
serverSocket = socket(AF_INET, SOCK_STREAM)

# Prepare a sever socket
serverSocket.bind(("localhost", 6789))

# Listen to at most 10 connection at a time
serverSocket.listen(10)

# Server should be up and running and listening to the incoming connections
while True:
    print ('Ready to serve...')
    try:
        # Set up a new connection from the client
        connectionSocket, clientAddr = serverSocket.accept()
        # create a new thread to service a client by calling clientService inside the new thread
        # pass connection socket and client address into this thread to use when it calls function
        _thread.start_new_thread(clientService, (clientAddr, connectionSocket))
    except KeyboardInterrupt:
        # User pressed Ctrl+C, exit gracefully
        break
    
# Close server connection
serverSocket.close()
