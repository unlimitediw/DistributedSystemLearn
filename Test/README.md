# HW 1: The Simplest Distributed System

#### A very simple Message Board
Our program will do the following:

The **server** acts as message board; it will print out any messages that are sent to it along with the name of the sender.

The **client** connects to the server and sends its name and the message it wants printed.  After that it disconnects. The server does not send anything back to the client: I told you this was very simple!

**IMPORTANT:** *During this exercise you* **may NOT** *go to any websites except this page, and those listed in the "Allowable Links" sections below.*  **Visiting any other website is cheating!**

## Getting the code

Before you can begin you need to get a copy of the sample code inside your CodeAnywhere container.
  * Clone your repository into your CodeAnywhere container by running: (fill in the URL from your repo website)
```
git clone YOUR_URL
```
  * Then you should right click on the CS6421 container name and "Refresh" so that the repo files appear in the File Hierarchy.

## Starting the Server
The provided server is written in Java so you will need to compile and then run the program as follows.
```
cd server              # change to the server/ directory
javac MsgServer.java   # compile the code
java MsgServer         # start the server
```

**Note:** You will need to run your own copy of the server and you will need to change the IP/hostname in the sample client code to connect to your version of the server, not mine!

## Client 0: `telnet`
The first client you will use to connect to the server won't require any coding at all--you will use `telnet`.  Telnet is a very simple program that is useful for debugging network applications and protocols.  It simply sends and receives lines of data to and from a server.  To run telnet, run: `telnet HOST PORT`

This will open a TCP connection to the specified `HOST` (either an IP or hostname) and `PORT` number.  If you type some text and hit enter, `telnet` will transmit that line of text to the server.  `telnet` is also always listening for messages coming from the server, and will print them out one line at a time.

Try sending a few messages to verify that your server is working -- use `localhost` as the host and `5555` as the port.

**Once you know the server works, you can then try the different languages below in any order.**

## Client 1: Sockets in C
To create a socket in C, you first need to get an `addrinfo` struct for the host you want to connect to. This is accomplished with the function:

```
     getaddrinfo(const char *hostname, const char *servname,
             const struct addrinfo *hints, struct addrinfo **res);
```
The first argument is a string with the host name or IP, and the second is also a string (not an int like you might expect) with the service port.  The third argument provides parameters for the type of socket you want to create, and the final parameter will be filled in by the function if it is called successfully.

Here is a sample usage:

```
struct addrinfo hints, *server;

memset(&hints, 0, sizeof hints);
hints.ai_family = AF_INET;
hints.ai_socktype = SOCK_STREAM;

if ((rc = getaddrinfo(server_ip, server_port, &hints, &server)) != 0) {
        perror(gai_strerror(rc));
        exit(-1);
}
```

Once you have a valid `addrinfo` for your server, you can create a socket and connect it:

```
int sockfd = socket(server->ai_family, server->ai_socktype, server->ai_protocol);
if (sockfd == -1) {
        perror("ERROR opening socket");
        exit(-1);
}
rc = connect(sockfd, server->ai_addr, server->ai_addrlen);
if (rc == -1) {
        perror("ERROR on connect");
        close(sockfd);
        exit(-1);
}
```

Now your socket is connected, so you can `send` and `recv` data with the socket file descriptor returned by the `socket()` function.  Here are the function definitions from `man send` and `man recv`

```
ssize_t send(int socket, const void *buffer, size_t length, int flags);

ssize_t recv(int socket, void *buffer, size_t length, int flags);
```

Both return the amount of data sent or received, and take arguments indicating the socket to send/recieve on, the data to send or a buffer to receive into, the length of data, and flags (which you can set to 0).

**Try to edit the `c/msg_client.c` file to correctly connect to your Message Board server and send it a name and message.**

Allowable links (**you may not go to any websites except these**):
  * [C for java programmers](http://www.cprogramming.com/java/c-and-c++-for-java-programmers.html)
  * [getaddrinfo API](http://beej.us/guide/bgnet/output/html/multipage/getaddrinfoman.html)
  * [socket API](http://beej.us/guide/bgnet/output/html/multipage/socketman.html)
  * [connect API](http://beej.us/guide/bgnet/output/html/multipage/connectman.html)
  * [send](http://beej.us/guide/bgnet/output/html/multipage/sendman.html) and [receive](http://beej.us/guide/bgnet/output/html/multipage/recvman.html) APIs
  * The course's Piazza message board

## Client 2: Sockets in Java
Using sockets in Java is a bit simpler than C.  You simply need to create a `Socket` object. The simplest constructor for this class takes parameters for the host name and port:
```
public Socket(String host, int port)
       throws UnknownHostException, IOException

Creates a stream socket and connects it to the specified port number on the named host.

Parameters:
    host - the host name, or null for the loopback address.
    port - the port number.
```

Be careful--since instantiating this class can cause an exception to be thrown, you will have to put this code inside a `try / catch` block.

Once your socket is created, you use this with a PrintWriter object (which you've probably used for basic File I/O in the past). This code will create a new `PrintWriter` using the output stream of the socket and set the writer to automatically flush data (i.e., send it out the socket):
```
// sock must be a Socket object
PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
```

Now that you have a `PrintWriter`, you can use functions like `out.println("Hello World")` to have it send a string plus a new line character to the server.

If you also want to read from the socket to receive data, you would need to create a `BufferedReader` object.  We'll save that for another day.

**Try to edit the `java/MsgClient.java` file to correctly connect to your Message Board server and send it a name and message.**


Allowable links:
  * [Java beginners tutorials](http://docs.oracle.com/javase/tutorial/java/index.html)
  * [Java Socket API](http://docs.oracle.com/javase/7/docs/api/java/net/Socket.html)
  * [Java PrintWriter API](http://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html)
  * [Java BufferedReader API](http://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html)
  * The course's Piazza message board

## Client 3: Sockets in Python
Python makes using sockets even easier (or at least with less code).

To create a socket in Python you create a socket object:
```
import socket

clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
```

This socket object then exposes functions to `connect`, `send`, and `recv`.  The `connect` function takes a tuple `(hostname, port)` while `send` simply takes a string of the text to be sent.

**Try to edit the `python/msgclient.py` file to correctly connect to your Message Board server and send it a name and message.**


Allowable links:
  * [A basic Python tutorial](http://www.stavros.io/tutorials/python/)
  * [Python Socket API](https://docs.python.org/2/library/socket.html#socket.socket)
  * Python [send](https://docs.python.org/2/library/socket.html#socket.socket.send) and [recv](https://docs.python.org/2/library/socket.html#socket.socket.recv)
  * [Python Socket How To](https://docs.python.org/2/howto/sockets.html#socket-howto)
  * The course's Piazza message board

# Making your code easy to run
If you reach this point you should have three working clients in three different programming languages.  Congratulations! There is one final task you must complete before you can submit your code.

You should modify each of your programs so that they will accept command line arguments that determine the host that is connected to, the username sent as the first string, and the message sent.  For example:
```
python msgclient.py "user.somehost.com"  "my name" my message"
java MsgClient "192.168.1.1" "Joe" "Hello World"
./msg_client "twood02.koding.com" "Chen"  "this is my message"
```

You can use any websites you like to learn how to use command line arguments in each of these languages.
