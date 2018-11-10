
### Topic 1: URL
* http://www.example.com/index.html, which indicates a protocol (http), a hostname (www.example.com), and a file name (index.html).

### Topic 2: Protocol Layers
1. [Application](#application)
1. [Transport](#transport)
1. [Network](#network)
1. [Link](#link)
1. [Physical](#physical)
<a name="application"></a>
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
<a name="transport"></a>
* Transport: data transfer
  * TCP(or TCP/IP)
    * Transmission Control Protocol: TCP provides reliable, ordered, and error-checked delivery of a stream of octets (bytes) between applications running on hosts communicating via an IP network.
    * point-to-point: one sender, one receiver
    * reliable, in-order byte stream but slow start
    * pipelined: TCP congestion and flow control set window size
    * full duplex data:
      * bi-directional data flow in same connection
      * MSS: maximum segment size
    * connection-oriented: 3 times handshake(exchange of control msgs)
      1. SYN: The active open is performed by the client sending a SYN to the server. The client sets the segment's sequence number to a random value A.
      2. SYN-ACK: In response, the server replies with a SYN-ACK. The acknowledgment number is set to one more than the received sequence number i.e. A+1, and the sequence number that the server chooses for the packet is another random number, B.
      3. ACK: Finally, the client sends an ACK back to the server. The sequence number is set to the received acknowledgement value i.e. A+1, and the acknowledgement number is set to one more than the received sequence number i.e. B+1.
    * ![](https://github.com/unlimitediw/DistributedSystemLearn/blob/master/Image/TCPFormat.PNG)
  * UDP
    * User Datagram Protocol: With UDP, computer applications can send messages, in this case referred to as datagrams, to other hosts on an Internet Protocol (IP) network.
    * no "connection" between client and server:
      * no handshaking
      * sender explicitly attaches IP address and port of destination
      * server must extract IP address, port of sender from received datagram
    * transmiited data may be received out of order or lost
    * UDP provides unreliable transfer of groups of bytes(datagrams) between client and server
<a name="network"></a>
* Network: finding routes
  * IP
    * Internet Protocol address: is a numerical label assigned to each device connected to a computer network that uses the Internet Protocol for communication. An IP address serves two principal functions: host or network interface identification and location addressing.
    * IPV4: 
      * 32bit
      * private addresses(excluded range):
        * 10.0.0.0 … 10.255.255.255
        * 172.16.0.0 … 172.31.255.255
        * 192.168.0.0 … 192.168.255.255
        * Network Address Translation(NAT): 装有NAT软件的路由器叫做NAT路由器，它至少有一个有效的外部全球IP地址。这样，所有使用本地地址的主机在和外界通信时，都要在NAT路由器上将其本地地址转换成全球IP地址，才能和因特网连接。
        * [IPV4 WIKI](https://en.wikipedia.org/wiki/IPv4)
    * IPV6
      * 在自动生成IPv6地址时，我们通常是根据MAC地址生成的，这样就是48位。
    * p.s MAC: MAC是身份证号码，用来识别网络设备本身。IP地址是居住地。具有全球唯一性。
  * routing protocols
    * Internet approach to scalable routing:
      * intra-AS(autonomous system: a collection of routers whose prefixes and routing policies are under common administrative control. or "domain")
        * routing among hosts, routers in same AS
        * all routers in AS must run same intra-domain protocol
        * routers in different AS can run different intra-domain routing protocol
        * gateway router: at "edge" of its own AS, has lik to routers in other AS'er
      * inter-AS
        * routing among AS'es
        * gateways perform inter domain routing(as well as intra-domain routing)
    * Three Major Types:
      * Interior gateway protocols -> link-state routing protocols:
        * Open Shortest Path First(OSPF): 
          * publicly available
          * link state packet dissemination, topology map at each node and Dijkstra's algorithm
          * router floods OSPF link-state advertisements to all other routers in entire AS
        * Intermediate System-to-Intemediate System(IS-IS)
          * [Only little difference with OSPF](https://community.cisco.com/t5/network-architecture-documents/ospf-and-is-is-differences/ta-p/3126940)
      * Interior gateway protocols -> distance-vector routing protocols
        * Distance vector algorithm:
          * Dx(y) = estimate of least cost from x to y. for all y belong to N
          * node x:
            * knows cost to each neighbor v: c(x,v)
            * maintains its neighbors' distance vectors. For each neighbor v,x maintains: Dv = Dv(y) for all y belong to N. Dx(y) = min(c(x,v) + Dv(y)) for all v as x' neighbours and all y belong to N
        * Routing Information Protocol(RIP)
        * RIPv2
        * Interior Gateway Protocol(IGRP)
      * Comparision of LS and DV algorithms
        * message complexity:
          * LS(link state): with n nodes, E links -> O(nE) msgs sent
          * DV: exchange between neighours at each iteration -> convergence time varies
        * speed of convergence
          * LS: O(n^2) algorithm requires (if binary heap, O(|E+n|logn, O(nE) msgs
          * DV: Propotional to the number of hops in the longest min-cost path 
        * robustness:
          * LS: node can advertise incorrect LINK cost, each node computes only its own table
          * DV: DV node can devertise incorrect PATH cost, each node's table used by other
        * [LS和DV路由协议的分析与比较](https://blog.csdn.net/hnu_lb/article/details/25025575)
      * Exterior gateway protocols
        * Are routing protocols used on the Internet for exchanging routing information between Autonomous Systems, such as Border Gateway Protocol (BGP), Path Vector Routing Protocol.
<a name="link"></a>
* Link: adjacent nodes
  * Ethernet
    * It is a family of computer networking technologies commonly used in local area networks (LAN), metropolitan area networks (MAN) and wide area networks (WAN)
    * 以太网（英语：Ethernet）是一种计算机局域网技术。IEEE组织的IEEE 802.3标准制定了以太网的技术标准，它规定了包括物理层的连线、电子信号和介质访问层协议的内容。以太网是目前应用最普遍的局域网技术，替换了其他局域网标准如令牌环、FDDI和ARCNET。
    * ![](https://github.com/unlimitediw/DistributedSystemLearn/blob/master/Image/Data_Networks_classification_by_spatial_scope.png)
  * [802.111(WIFI)](https://en.wikipedia.org/wiki/IEEE_802.11ac)
  * [PPP is a data link layer (layer 2) communications protocol used to establish a direct connection between two nodes.](https://en.wikipedia.org/wiki/Point-to-Point_Protocol)
<a name="physical"></a>
* Physical:
  * bit on wire
  * bit in the air

### Topic 3: Steps
1. Type URL and hit enter
2. DNS lookup: hostname -> IP
3. ARP lookup: IP->MAC address
  * arp displays and modifies entries in the Address Resolution Protocol (ARP) cache, which contains one or more tables that are used to store IP addresses and their resolved Ethernet or Token Ring physical addresses. 
4. Socket setup
5. Send call moves data from browser to OS
6. TCP Handshake
7. Routing lookups along path
8. HTTP request issued 
9. Parse HTTP response: HTML->DOM tree
  * The Document Object Model (DOM) is a cross-platform and language-independent application programming interface that treats an HTML, XHTML, or XML document as a tree structure where in each node is an object representing a part of the document. 
10. Make additional requests for other resources

### Topic 4: Socket

* What can a socket do
  * Send
  * Receive
  * Blocking and Non-blocking
 
