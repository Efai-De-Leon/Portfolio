//Headers
#include <gtk/gtk.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "TestClient.h"
#include "GlobalDefs.h"
//#include "user.h"
/*******************    MAIN PROGRAM    ******************************/
//Variables
//GTK Widget
GtkWidget *Create_Account_Window(int *argc, char **argv[]);
GtkWidget *Login_Window(int *argc, char **argv[]);
GtkWidget *Chat_Window(int *argc, char **argv[]);
GtkWidget *Register_window, *login_window, *chat_window, *friend_list, *message;
GtkWidget *Friend_List(int *argc, char **argv[]);
GtkTextBuffer *message_buffer;

static GtkWidget *entry, *entry2;

//Function Prototypes
static void button_clicked2(GtkWidget *widget, gpointer data);
static void button_clicked(GtkWidget *widget, gpointer data);
static void update_invalid_label(GtkWidget *widget, gpointer data);
int check_valid_user_pwd(const char *s);
//Flags
int RegFlag = 0;
int ChatFlag = 0;
int FriendFlag = 0;
int loop = 1;
int valid =0;
//Username/Password Buffers
char name[30]="Empty";
char password[30] = "Empty";
char sent_message[30] = "Empty";
//File Pointer
FILE *outfile;
//TestClient Necessities

        int SocketFD;
        char RecvBuf[BUFFSIZE];
        char *answer;

//-------------------WINDOW CONTROL-------------------------
//Window Control
void create_register_window(GtkWidget *widget, gpointer data)
{
	RegFlag = 1;
	char *reg;
       // strcat(reg,PingServer("SignUp", RecvBuf, SocketFD));

}
void delete_register_window(GtkWidget *widget, gpointer data)
{
	RegFlag = 0;
	gtk_widget_hide_on_delete( Register_window );
}
void close_program(GtkWidget *widget, gpointer data)
{
	loop = 0;
}
void create_chat_window(GtkWidget *widget, gpointer data)
{
	ChatFlag = 1;
}
void delete_chat_window(GtkWidget *widget, gpointer data)
{
	ChatFlag = 0;
	gtk_widget_hide_on_delete( chat_window );
}
void create_friend_window(GtkWidget *widget, gpointer data)
{
	FriendFlag = 1;
}
void close_friend_window(GtkWidget *widget, gpointer data)
{
	FriendFlag = 0;
}
void delete_friend_window(GtkWidget *widget, gpointer data)
{
	FriendFlag = 0;
	gtk_widget_hide_on_delete( friend_list );
}		

//---------------------------------CLICK FUNCTIONS-------------------------------------------
//Send Message
static void send_message(GtkWidget *widget, gpointer data)
{
	gtk_label_set_text(GTK_LABEL(data), gtk_entry_get_text(GTK_ENTRY(message)));	
	strncpy(sent_message, gtk_label_get_text(GTK_LABEL(data)),sizeof(sent_message));

	gtk_text_buffer_insert_at_cursor(message_buffer,"\n", -1);
	gtk_text_buffer_insert_at_cursor(message_buffer,sent_message, -1);
		
}
//Button Click Functions For User/Password File
/*void writing_string(char *s)
{
        outfile = fopen(name,"w");
	fprintf(outfile,"%s\n", s);
	
} */
static void button_clicked(GtkWidget *widget, gpointer data)
{       
        gtk_label_set_text(GTK_LABEL(data),gtk_entry_get_text(GTK_ENTRY(entry)));       
        strncpy(name, gtk_label_get_text(GTK_LABEL(data)),sizeof(name));       
	printf("Your User name is: %s\n",name);
}
static void button_clicked2(GtkWidget *widget, gpointer data)
{       
        gtk_label_set_text(GTK_LABEL(data),gtk_entry_get_text(GTK_ENTRY(entry2)));      
        strncpy(password, gtk_label_get_text(GTK_LABEL(data)),sizeof(password));

	//int exists;
	//USER *user;
	//exists = UsernameExists(name,reading_userlist());

	//if (exists == FALSE){
/*		if(strcmp(password, "Empty")!= 0)
		{
			//user = CreateNewUser(name, password);
			//writing_newuser(user);
			RegFlag = 0;	
			if(check_valid_user_pwd(gtk_label_get_text(GTK_LABEL(data)))==1)
			{
				printf("invalid password\n");
				valid =1;
			}
			writing_string(name);
			fprintf(outfile,"%s\n", password);
			
			gtk_main_quit();
			gtk_widget_hide_on_delete( Register_window );
			
		}*/

	//}
	printf("Your Password is: %s\n",password);
}

static void update_invalid_label(GtkWidget *widget, gpointer data)
{
	
	if(valid ==1)
	{
		gtk_label_set_text(GTK_LABEL(data),"invalid");
		valid =0;
	}
}


//Notebook Functions
static void remove_book( GtkButton   *button,
                         GtkNotebook *notebook )
{
    gint page;

    page = gtk_notebook_get_current_page (notebook);
    gtk_notebook_remove_page (notebook, page);
    /* Need to refresh the widget --
 *  *      This forces the widget to redraw itself. */
    gtk_widget_queue_draw (GTK_WIDGET (notebook));
}


//text functions
int check_valid_user_pwd(const char *s)
{
	int i =0;
	int z=( sizeof(s) / sizeof(s[0]));
	for(i = 0;i<z; i++)
	{
		if(s[i]==' ')
			{
				return 1;
			}
	}
	return 0;
}

//---------------------WINDOWS-------------------------------
//Register Window
GtkWidget *Create_Account_Window(int *argc, char **argv[])
{
        gtk_init(argc, argv);
        GtkWidget *window, *label, *vbox2, *button, *button2,*hbutton_box;
        GtkWidget *labelb, *label2, *label3, *label4, *label_invalid;
	GtkWidget *hpaned, *hpaned2;
	GtkEntryBuffer *buffer, *buffer2;
        window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
        gtk_window_set_default_size(GTK_WINDOW(window),300,350);
        g_signal_connect(window,"delete_event",G_CALLBACK(gtk_main_quit),NULL);
	g_signal_connect(window,"delete_event",G_CALLBACK(close_program),NULL);

        hbutton_box= gtk_hbutton_box_new();

        label = gtk_label_new("Your Username is:");
        labelb = gtk_label_new("Your Password is:");
        label2 = gtk_label_new("CREATE ACCOUNT");
        label3 = gtk_label_new("New username:");
        label4 = gtk_label_new("New password:");
	
	//label to change to invalid
	label_invalid = gtk_label_new("");

	buffer = gtk_entry_buffer_new("empty",-1);
	buffer2 = gtk_entry_buffer_new("empty",-1);

        entry = gtk_entry_new();
        entry2 = gtk_entry_new();
	gtk_entry_set_buffer(GTK_ENTRY(entry),(buffer));
	gtk_entry_set_buffer(GTK_ENTRY(entry2),(buffer2));
        hpaned = gtk_hpaned_new();
	hpaned2 = gtk_hpaned_new();

        button = gtk_button_new_with_label("OK");
        button2 = gtk_button_new_with_label("CANCEL");

        g_signal_connect(button,"clicked",G_CALLBACK(button_clicked),(gpointer)label);
        g_signal_connect(button,"clicked",G_CALLBACK(button_clicked2),(gpointer)labelb);

//Checking if this username and password is valid or not
//	char *UP;
  //      strcat(name, " ");
    //    strcat(name, password);
      //  strcat(UP, name);
	//char *answer;
//	strcat(answer,PingServer(UP, RecvBuf, SocketFD));

        //calling the function to update to label to invalid when ok button is clicked
/*        if (strcmp(answer, "failed") == 0)
	{
		g_signal_connect(button,"clicked",G_CALLBACK(update_invalid_label),(gpointer)label_invalid);
        }*/

	g_signal_connect(button2,"clicked", G_CALLBACK(gtk_main_quit),NULL);
	g_signal_connect(button2, "clicked", G_CALLBACK(delete_register_window), NULL);

        vbox2 = gtk_vbox_new(0,0);

        gtk_box_pack_start(GTK_BOX(hbutton_box),button2,0,0,0);
        gtk_box_pack_start(GTK_BOX(hbutton_box),button,0,0,0);
        gtk_box_pack_start(GTK_BOX(vbox2),label2,1,0,1);
        gtk_container_add(GTK_CONTAINER(hpaned),label3);
        gtk_container_add(GTK_CONTAINER(hpaned),entry);
        gtk_box_pack_start(GTK_BOX(vbox2),hpaned,0,1,1);
        gtk_container_add(GTK_CONTAINER(hpaned2),label4);
        gtk_container_add(GTK_CONTAINER(hpaned2),entry2);
        gtk_box_pack_start(GTK_BOX(vbox2),hpaned2,0,0,0);
	
	//displaying the label on the window
	gtk_box_pack_start(GTK_BOX(vbox2),label_invalid,1,0,0);

        gtk_box_pack_start(GTK_BOX(vbox2),hbutton_box,0,0,0);
        gtk_container_add(GTK_CONTAINER(window),vbox2);
        gtk_widget_show_all(window);

        return (window);
}

//Login Window
GtkWidget *Login_Window(int *argc, char **argv[])
{
	gtk_init(argc, argv);
	GtkWidget *window, *vbox, *entry, *entry2, *label;
	GtkWidget *button, *button2, *button3, *hbutton_box, *hpaned;
	GtkEntryBuffer *buffer;	
	GtkEntryBuffer *buffer2;
	GtkWidget *table;

	window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
        gtk_window_set_default_size(GTK_WINDOW(window),300,350);
        g_signal_connect(window,"delete_event",G_CALLBACK(gtk_main_quit),NULL);
	g_signal_connect(window,"delete_event",G_CALLBACK(close_program),NULL);

	table = gtk_table_new(2,2,0);
	vbox = gtk_vbox_new(0,0);
	hpaned = gtk_hpaned_new();

	hbutton_box= gtk_hbutton_box_new();
	buffer = gtk_entry_buffer_new("Empty",-1);
	buffer2= gtk_entry_buffer_new("Empty",-1);
        label = gtk_label_new("Login");
        gtk_box_pack_start(GTK_BOX(vbox),label,1,0,0);
	label = gtk_label_new("Username:");
	gtk_table_attach(GTK_TABLE(table), label,0,1,0,1,GTK_FILL,GTK_FILL,0,0);
	label = gtk_label_new("Password:");

	gtk_table_attach(GTK_TABLE(table), label,0,1,1,2, GTK_FILL,GTK_FILL,0,0);

	entry = gtk_entry_new();
        gtk_entry_set_buffer(GTK_ENTRY(entry),(buffer));
	gtk_table_attach(GTK_TABLE(table), entry,2,6,0,1,GTK_FILL,GTK_FILL,0,0);

	entry2 = gtk_entry_new();
	gtk_entry_set_buffer(GTK_ENTRY(entry2),(buffer2));
	gtk_table_attach(GTK_TABLE(table), entry2,2,6,1,2,GTK_FILL,GTK_FILL,0,0);


	gtk_box_pack_start(GTK_BOX(vbox),table,0,0,0);
        button = gtk_button_new_with_label("Login");
        button2 = gtk_button_new_with_label("Register");
	button3 = gtk_button_new_with_label("Quit");
	
	g_signal_connect(button2, "clicked", G_CALLBACK(create_register_window), NULL);
	g_signal_connect(button2, "clicked", G_CALLBACK(gtk_main_quit), NULL);
	g_signal_connect(button3, "clicked", G_CALLBACK(gtk_main_quit), NULL);
	g_signal_connect(button3, "clicked", G_CALLBACK(close_program), NULL);
	g_signal_connect(button, "clicked", G_CALLBACK(create_friend_window), NULL);
	g_signal_connect(button, "clicked", G_CALLBACK(gtk_main_quit), NULL);
	
	gtk_box_pack_start(GTK_BOX(hbutton_box),button2,0,0,0);
        gtk_box_pack_start(GTK_BOX(hbutton_box),button,0,0,0);
        gtk_box_pack_start(GTK_BOX(vbox),hbutton_box,2,0,0);
        gtk_container_add(GTK_CONTAINER(window),vbox);
        gtk_widget_show_all(window);

	return(window);
}

//Chat Window
GtkWidget *Chat_Window( int *argc, char **argv[] )
{	
    GtkWidget *window, *scrolled_window;
    GtkWidget *button;
    GtkWidget *table;
    GtkWidget *notebook;
    GtkWidget *label;    
    GtkWidget *Text_Area;
    GtkEntryBuffer *buffer;
    GtkWidget *messagelabel;   

    gtk_init (argc, argv);

    window = gtk_window_new (GTK_WINDOW_TOPLEVEL);
    gtk_window_set_default_size(GTK_WINDOW(window),300,350);

    g_signal_connect (window, "delete_event", G_CALLBACK (gtk_main_quit), NULL);
    g_signal_connect( window, "delete_event", G_CALLBACK(close_program), NULL);

    gtk_container_set_border_width (GTK_CONTAINER (window), 10);
    // message label not to be displayed
    messagelabel = gtk_label_new(" ");
    //Table Container Created
    table = gtk_table_new (3, 6, FALSE);
    gtk_container_add (GTK_CONTAINER (window), table);

    //Create Entry
    message = gtk_entry_new();
    buffer = gtk_entry_buffer_new("Enter Message",-1);
    gtk_entry_set_buffer(GTK_ENTRY(message),(buffer));	

    gtk_table_attach_defaults(GTK_TABLE(table),message,0,4,1,2);
    gtk_widget_show(message);

    //Create a New notebook, Set Tab
    notebook = gtk_notebook_new ();
    gtk_notebook_set_tab_pos (GTK_NOTEBOOK (notebook), GTK_POS_TOP);
    gtk_table_attach_defaults (GTK_TABLE (table), notebook, 0, 6, 0, 1);
    gtk_widget_show(notebook);

    //Scrolled Window with Text Box
    Text_Area = gtk_text_view_new();

    message_buffer = gtk_text_view_get_buffer(GTK_TEXT_VIEW(Text_Area));
    gtk_text_buffer_set_text(message_buffer,"Hello!",-1);

    GtkWidget *console = gtk_table_new(3,2,FALSE);

    scrolled_window = gtk_vscrollbar_new(gtk_text_view_get_vadjustment(GTK_TEXT_VIEW(Text_Area)));

    gtk_table_attach_defaults(GTK_TABLE(console),Text_Area,0,1,0,1);
    gtk_table_attach_defaults(GTK_TABLE(console),scrolled_window,1,2,0,1);

    gtk_widget_set_size_request(Text_Area,200,200);
    gtk_text_view_set_buffer(GTK_TEXT_VIEW(Text_Area),message_buffer);
    
    //Add Page
    label = gtk_label_new("User");
    gtk_notebook_insert_page(GTK_NOTEBOOK(notebook),console,label,2);

    //Set what page to start at (page 1)  
    gtk_notebook_set_current_page(GTK_NOTEBOOK(notebook),0);

    //Create Buttons
    button = gtk_button_new_with_label("Send");
    gtk_table_attach_defaults (GTK_TABLE (table), button, 5, 6, 1, 2);
    g_signal_connect(button, "clicked", G_CALLBACK (send_message), messagelabel);
    gtk_widget_show (button);

    button = gtk_button_new_with_label ("Close");
    g_signal_connect(button, "clicked", G_CALLBACK (gtk_main_quit), NULL);
    g_signal_connect(button, "clicked", G_CALLBACK (delete_chat_window), NULL);
    g_signal_connect(button, "clicked", G_CALLBACK (create_friend_window), NULL);

    gtk_table_attach_defaults (GTK_TABLE (table), button, 0, 1, 2, 3);
    gtk_widget_show (button);
    
    button = gtk_button_new_with_label ("Next Page");
    g_signal_connect(button,"clicked",G_CALLBACK(gtk_notebook_next_page),notebook);
    gtk_table_attach_defaults (GTK_TABLE (table), button, 1, 2, 2, 3);
    gtk_widget_show (button);
    
    button = gtk_button_new_with_label ("Prev Page");
    g_signal_connect(button, "clicked",G_CALLBACK (gtk_notebook_prev_page),notebook);
    gtk_table_attach_defaults (GTK_TABLE (table), button, 2, 3, 2, 3);
    gtk_widget_show (button);

    button = gtk_button_new_with_label("Remove Friend");
    g_signal_connect(button,"clicked",G_CALLBACK(remove_book),notebook);
    gtk_table_attach_defaults(GTK_TABLE(table),button,5,6,2,3);
    gtk_widget_show(button);

    //Show Window
    gtk_widget_show_all(window);

    return (window);	
}


GtkWidget *Friend_List( int *argc, char **argv[] )
{
	gtk_init(argc, argv);

	GtkWidget *window, *button, *label, *scroll_window,*vbox, *table;
	window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
	gtk_window_set_default_size(GTK_WINDOW(window),260,300);
	g_signal_connect(window,"delete_event",G_CALLBACK(gtk_main_quit),NULL);
        g_signal_connect(window,"delete_event",G_CALLBACK(close_program),NULL);

//initialization of vbox
	table = gtk_table_new(3,10,0);

//vbox inside the scroll window


	vbox = gtk_vbox_new(0,0);
	for (int i = 0; i<10; i++){
	button = gtk_button_new_with_label("Friend 1");
	gtk_box_pack_start(GTK_BOX(vbox), button ,0,1,1);
	g_signal_connect(button,"clicked",G_CALLBACK(close_friend_window),NULL);
	g_signal_connect(button,"clicked",G_CALLBACK(create_chat_window),NULL);
	g_signal_connect(button,"clicked",G_CALLBACK(gtk_main_quit),NULL);

	}

	scroll_window = gtk_scrolled_window_new(NULL,NULL);
	gtk_scrolled_window_add_with_viewport(GTK_SCROLLED_WINDOW(scroll_window), vbox);

//vobx outside the scroll window
	vbox = gtk_vbox_new(0,0);


	button = gtk_button_new_with_label("  X  ");
	gtk_table_attach(GTK_TABLE(table), button ,0,1,0,1,GTK_FILL,GTK_FILL,40,0);

	label= gtk_label_new("Contacts");
	gtk_table_attach(GTK_TABLE(table), label,4,6,0,1,GTK_FILL,GTK_FILL,0,0);

	button = gtk_button_new_with_label("  +  ");	
	gtk_table_attach(GTK_TABLE(table), button ,9,10,0,1,GTK_FILL,GTK_FILL,40,0);
	gtk_box_pack_start(GTK_BOX(vbox), table,0,1,1);
	gtk_box_pack_start(GTK_BOX(vbox), scroll_window,1,1,0);
	
	table = gtk_table_new(3,10,0); 
	button= gtk_button_new_with_label("Next");
	gtk_table_attach(GTK_TABLE(table), button,9,10,0,1,GTK_FILL,GTK_FILL,35,0);
	g_signal_connect(button,"clicked",G_CALLBACK(close_friend_window),NULL);
	g_signal_connect(button,"clicked",G_CALLBACK(create_chat_window),NULL);
	g_signal_connect(button,"clicked",G_CALLBACK(gtk_main_quit),NULL);

	button= gtk_button_new_with_label("Previous");
        gtk_table_attach(GTK_TABLE(table), button,0,1,0,1,GTK_FILL,GTK_FILL,35,0);
	g_signal_connect(button,"clicked",G_CALLBACK(delete_friend_window),NULL);
	g_signal_connect(button,"clicked",G_CALLBACK(gtk_main_quit),NULL);

	
	gtk_box_pack_start(GTK_BOX(vbox), table,0,1,1);
	gtk_container_add(GTK_CONTAINER(window), vbox);

	gtk_widget_show_all(window);
	gtk_main();

	
	return(window);
}

//----------------------MAIN PROGRAM-------------------------------------
//Main Loop
int main(int argc, char *argv[])
{
/*	int PortNo;
//	int SocketFD;
	struct hostent *Server;
//	char RecvBuf[BUFFSIZE];
	int l, n;
	const char *Program = argv[0];

#ifdef DEBUG
	printf("%s: Starting...\n", argv[0]);
#endif
	if(argc < 3){
		fprintf(stderr, "Usage: %s hostname port\n", Program);
		exit(10);	
	}

	Server = gethostbyname(argv[1]);
	if(Server == NULL)
	{
		fprintf(stderr, "%s: no such host named '%s'\n", Program, argv[1]);
		exit(10);
	}

	PortNo = atoi(argv[2]);
	if(PortNo <= 2000){
		fprintf(stderr, "%s: invalid port number %d, shoiuld be >2000\n", Program, PortNo);
		exit(10);
	}
	
	ServerAddress.sin_family = AF_INET;
	ServerAddress.sin_port = htons(PortNo);
	ServerAddress.sin_addr = *(struct in_addr*)Server->h_addr_list[0];

	SocketFD = socket(AF_INET, SOCK_STREAM, 0);
	if(SocketFD < 0){
		FatalError("socket creation failed");
	}

	if(connect(SocketFD, (struct sockaddr*)&ServerAddress, sizeof(struct sockaddr_in)) < 0)
	{
		FatalError("connecting to server failed");
	}*/
	
	while (loop)
	{	
		if ( RegFlag == 0 && ChatFlag == 0 && FriendFlag == 0 )
		{
			login_window= Login_Window(&argc, &argv);		
		}
		if ( RegFlag == 1 )
		{
			gtk_widget_hide_on_delete( login_window );
			Register_window= Create_Account_Window(&argc, &argv);
		}
		if ( ChatFlag == 1 )
		{
			gtk_widget_hide_on_delete( friend_list );
			chat_window = Chat_Window(&argc, &argv);
		}
		if ( FriendFlag == 1 )
		{
			gtk_widget_hide_on_delete( login_window );
			friend_list = Friend_List(&argc,&argv);

		}

		gtk_main();
	}
return 0;
}
 
