# Chat Application
![Alt Text](chat_application_c.gif)

# How to Run
## 1. Compile the project:
* Open a terminal in the root directory of the project.
* Type `make` and press Enter. This will compile the server and GUI components.
## 2. Start the server:
 *In the root directory, type `./ChatServer 3002` and press Enter. This will start the chat server on port 3002. You can replace 3002 with a different port number if needed.
## 3. Launch the GUI application:
* Navigate to the `GUI` folder within the project directory and type `make`
* Type `./GuiApp localhost 3002` and press Enter. This will launch the GUI application and connect it to the server running on localhost (your local machine) and port 3002.
# How to Use
## 1. Register and log in:
* In the GUI application, enter a username and password in the registration fields.
* Click the `Register` button.
* Once registered, enter the same username and password in the login fields.
Click the `Login` button.
## 2. Add a friend:
* Click the `+` button to open the friend request dialog.
* Enter the username of the friend you want to add.
* Click the `ADD` button.
## 3. Accept a friend request:
* When you receive a friend request, a notification will appear in the other chat window.
* Click the `Accept` button to add the friend to your friend list.
## 4. Start a chat:
Click on the name of the added friend in your friend list. This will open a chat window for that friend.
## 5. Send messages:
* Type your message in the chat window's text field.
* Click the `Send` button to send the message to your friend.
## 6. Receive messages:
* Messages from your friend will appear in the chat window.
* You can also click the `Request Messag` button to manually check for new messages.
