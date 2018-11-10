
### Topic 1: URL
* http://www.example.com/index.html, which indicates a protocol (http), a hostname (www.example.com), and a file name (index.html).

### Topic 2: Protocol Layers
* Application
  * FTP
    * File Transfer Protocol: a standard network protocol used for the transfer of computer files between a client and server on a computer network.
    * TCP/IP
    * Syntax: ftp://[user[:password]@]host[:port]/url-path
  * SMTP
    * Simple Mail Transfer Protocol: an Internet standard for electronic mail (email) transmission.
    * TCP/IP
  * HTTP
    * Hypertext Transfer Protocol: is an application protocol for distributed, collaborative, hypermedia information systems.
    * TCP and UDP
    * WWW: HTTP is the foundation of data communication for the World Wide Web
* Transport: data transfer
  * TCP(or TCP/IP)
    * Transmission Control Protocol: TCP provides reliable, ordered, and error-checked delivery of a stream of octets (bytes) between applications running on hosts communicating via an IP network.
    * point-to-point: one sender, one receiver
    * reliable, in-order byte stream
    * pipelined: TCP congestion and flow control set window size
    * full duplex data:
      * bi-directional data flow in same connection
      * MSS: maximum segment size
    * connection-oriented: 3 times handshake(exchange of control msgs)
      1. SYN: The active open is performed by the client sending a SYN to the server. The client sets the segment's sequence number to a random value A.
      2. SYN-ACK: In response, the server replies with a SYN-ACK. The acknowledgment number is set to one more than the received sequence number i.e. A+1, and the sequence number that the server chooses for the packet is another random number, B.
      3. ACK: Finally, the client sends an ACK back to the server. The sequence number is set to the received acknowledgement value i.e. A+1, and the acknowledgement number is set to one more than the received sequence number i.e. B+1.
  * UDP
    * User Datagram Protocol
* Network
* Link
* Physical
