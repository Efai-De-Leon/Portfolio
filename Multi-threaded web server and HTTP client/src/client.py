#Name: Efai De Leon
from socket import *
import sys, getopt

program_name = sys.argv[0]
serverName = sys.argv[1]
serverPort = int(sys.argv[2],10)
filename = sys.argv[3]

clientSocket = socket(AF_INET, SOCK_STREAM)

clientSocket.connect((serverName, serverPort))

clientSocket.send(bytes('GET /' + filename + ' HTTP/1.1\r\n','UTF-8'))

data = clientSocket.recv(2048)
print('From Server: ', data)
while data:
    data = clientSocket.recv(2048)
    print('From Server:', data)
#close the connection
clientSocket.close()
