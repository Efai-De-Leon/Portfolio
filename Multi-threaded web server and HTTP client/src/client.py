from socket import *
import sys

clientSocket = socket(AF_INET, SOCK_STREAM)
# initiate TCP connection
clientSocket.connect((sys.argv[1], int(sys.argv[2])))

# Send GET request for the file
clientSocket.send(b"GET /" + sys.argv[3].encode() + b" HTTP/1.1\r\n\r\n")

full_text = b""
response = clientSocket.recv(1024)
while response:
    full_text = full_text + response
    response = clientSocket.recv(1024)
    
# print server response
print(full_text.decode())
clientSocket.close()
