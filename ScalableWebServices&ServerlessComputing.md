### Scalable Web Services
> Antique Web Servers
* Serve static content
  * Read a file from disk and send it back to the client
  * images, HTML
* Dynamic Content
  * CGI-Bin (Custom Gateway Interface)
    * It is a method to enhance the content of Web pages above simple HTML
    * Script code is located in the CGI-BIN directory on the server
    * The code is executed on the server
  * Executes a program
  * Not very safe or convient for development
  
> 3-tier Web Applications
* Separation of duties
  * Front-end web server for static content (Apache, lightpd, nginx)
  * Application tier for *dynamic* logic （PHP, Tomcat, node.js)
  * Database back-end holds state (MySQL, MongoDB, Postgres)
* e.g. customer -> Apache -> Tomcat -> MySQL
* LAMP = Linux, Apache, MySQL, PHP

> Stateful vs Stateless
* The multi-tier architecture is based largely around whether a tier needs to worry about state
* Front-end - totally *stateless*
  * There is no data that must be maintained by the server to handle subsequent requests
* Application tier - maintains *per-connection state*
  * There is some temporary data related to each user, e.g., my shopping cart!
  * May not be critical for reliability - might just store in memory
* Database tier - global state
  * Maintains the global data that application tier might need
  * Persists state and ensures it is consistent

> N-Tier Web Applications
* Sometimes 3 tier isn't quite right
* Database is often a bottleneck
  * Add a cache (stateful, but not persistent)
* Authentication or other security services could be another tier
* Video transcoding, upload processing, etc
* May like a direct graph

> Replicated N - Tier
* Replicate the portions of the system that are likely to become overloaded
* Scale
  * Apache serving static content
  * Tomcat Java application managing user shopping carts
  * MySQL cluster storing products and completed orders
  ![](https://github.com/unlimitediw/DistributedSystemLearn/blob/master/Image/Ntier.png)  
  
> Wikipedia: Big scale, cheap
* 5th busiest site in the world, run on about ~1000 servers
* All open source software: PHP, MariaDB, Squid proxy, memcached, Ubuntu
* Goals:
  * Store lots of content (6TB of text data as of 2018)
  * Make available worldwide
  * Do this as cheaply as possible
  * Relatively weak consistency guarantees
  ![](https://github.com/unlimitediw/DistributedSystemLearn/blob/master/Image/WikiWebArch.png)  

> Application Tier
* A monolithic application -> scales by replicating the monolith on multiple servers

> Microservices
* Multiple servers -> Microservices which puts each element of functionality into a seperate service
* And scales by distributing these services across servers, replicating as needed.

> Microservices Challeneges
* Discovery: how to find a service you want?
* Scalability: how to replicate services for speed?
* Openness: how to agree on a message protocol?
* Fault tolerance: how to handle failed services?

> Netflix
* 26th most popular website but zero of their own servers
  * All infrastructure is on AWS (2016-2018)
  * Recently starting to build out their own Content Delivery Network
* One of the first to really push microservices
  * Known for their DevOps (software development&information technology operations which is to include automation and event monitoring at all steps of the software build)
  * Fast paced, frequent updates, must always be available
* 700+ microservices and Deployed across 10,000s of VMs and containers.

> Netflix "Deathstar" and "Chaos Monkey
* Deathstar problem: Microservice architecture results in a extremely distributed application which can be very difficult to manage and understand how it working at scale.
* Idea: If my system can handle failures, then I don't need to know exactly how all the pieces themselves interact.
* Chaos Monkey:
  * Randomly terminate VMs and containers in the production environment
  * Ensure that the overall system keeps operating
  * Run this 24/7


### Serverless
> Serverless Computing
* Trendy architecture that improves the agility of microservices
* AWS Lambda, Azure Functions, APACHE OpenWhisk, Google Cloud Functions, etc.
* Key idea: only instatiate a service when a user makes a request for that functionality

> Serverless Startup
* AWS Lambda
  * Define a stateless "function" to execute for each request
  * A container will be instatiated to handle the first request
  * The same container will be used until it times out or is killed(clean up)
  * Set multi containers for multi function for easy management and check
  * Procedure:
    * Request arrives, start a container
    * Reuse that container for subsequent requests (Lambda Gateway)
    * Start new container if user needs a different function
    * Clean up old containers once not in use
* Benefit
  * Simple for developer when auto scaling up
  * Pay for exactly what we use
  * Efficient use of resources(auto scale up and down based on requests)
  * Don't worry about relability/ server management at all
  * Prebuild in AWS
* Drawbacks
  * Limited functionality(stateless-cotainer itselft not store memory/catch, limited programming language)
  * High latency for first request to each container
  * Some container layer overheads plus the lambda gateway and routing overheads
  * Potential higher and unpredictable costs.
  * Difficult to debug / monitor behavior
  * Security
    
> Two ways to scale
* Scale up(vertical)
  * Buy a bigger computer
* Scale out(hirizontal)
  * Buy multiple computers

> Does virtualization help?
* Virtualization divides something big into smaller pieces but still has features which can assist with scalability
  * Easy replication of VM images
  * Dynamic resource management
* Simplifies scale out but has limits on how much you can scale up


> Biggest Challenge: Consistency
  * Replicating data makes it faster to access-> but need keep all data consistent
  * Writes are even harder
    * Would need time stamps or a consistent ordering
    * Or, if writes are rare, just have a master coordinate
  * May a lightly out of date wikipedia page(but sometime such as facebook, stock: no)
  
> Providing Consistency
* Techniques to help
  * *Version vectors*
  * Distributed locking based on *Lamport Clock*
  * Election-based systems with a master/slave setup
  
* Different types of consistency
  * Strict - updates immediately available after a write
  * Sequential - result of parallel updates needs to have the same effect as if they had been done sequentially.
  * Causal - updates that are casually related (e.g., where vector clocks can prove the relationship) are ordered sequentially, but other may not. 
  * Eventual - updates will converge so at some point reads to any replica will get the same result.


### Partitioning
> Spread data across servers
* useful if all data does not fit on one server
* Consider a Key Value store like Memcached
  * Lots of data to store
  * Consistency is not that important
  * Might need to add or remove nodes to the cluster

> DHTs
* A Distributed Hash Table is a key-value store that can be implemented in a Peer-2-Peer fashion.
* Goals:
  * Evenly partiotn data across nodes
  * Efficient 
  * Graceful when nodes are frequently joing or leaving
* If one node can't fit all the data -> Do two hash lookups.
* But it will perform poorly reshuffling when a node leaves

> Churn
* Churn is when nodes are fequently joining or leaving
* When one node failed, all nodes may need to be reorganized

> Chord DHT Architecture
* Hash space as a ring
* Nodes pick a random ID when they join: 0 to MAX - 1
* Nodes are assigned contiguous portions of the ring starting at their ID until they reach the subsequent node.
* How to divide the hashspace:
  * If we have a lot of nodes we may divide it evenly
  * Or each node may claim multiple IDs(virtual nodes)

> Chord Churn
* What happens when a node is removed: 
  * the previous node points to where the removed node points to.
  * 2n nodes are affected (bi direction)
* What happens when a node is added:
  * Generally the same
  
> Chord Lookups
* Options 0: Key Index Table (key)
  * Store the node(holding each keys) in a central server
  * Directly access the node
  * drawbacks:
    * If we have millions of keys this table will be really big
    * The node that manages the index table will be a centralizaed bottleneck
* Options 1: Node Index Table (position)
  * Store the indices of all node IDs
  * Find which ID is closest to H
  * Drawbacks:
    * Table is still very large and may be bottleneck
    * Need to worry about consistency updating the table
* Options 2: Neighbors(double linked list like)
  * Each node tracks its successor and predecessor
  * If H > ID, ask successor; else ask predecessor
  * Requires minimal state
  * Drawback:
    * Can take a long time to traverse the ring O(n)
* Options 3: Finger Tables
  * Track m additional neighbors: successor 2^0, 2^1, 2^2 ... 2^m
  * Jump to cloest successor to find H then jump again
  * Requires minimal state
  * Can find items in log(N) steps
  * Like merge and conquer but the node don't know the size so use 2^m

* Concepts:
  * In Chord lookup protocol, rings has at most 2^m nodes, larger m to avoid collision. Some of nodes will map to machines or keys while others will be empty.
  * The successor to a node is the next node in the identifier circle in a clockwise direction (while predecessor is counter-clockwise).
  * The successor node of a key k is the first node whose ID equals to k or follows k in the identifier circle, denoted by successor(k). Every key is assigned to (stored at) its successor node, so looking up a key  k is to query  successor(k). (e.g. key k = 1-24 of successor(k) = node(25)
  * Sucessor is the clockwise neighbours while the predecessor is the counter-clockwise neighbours.

> Finger Table lookups
* Is to find successor(k)
* m is the number of bits in hash keys
* First entry of finger table is the immediate successor
* Finger table size = m, finger i is n + 2^i
* Since it is a loop, it may exceed, so remeber to mod the finger result with ring size 2^m
* Be attention, although only a little node, each finger is point to exact position of ring so it may be empty but they all have point to a successor.
* For a N node network time to find successor is O(logN)
* Every time a node wants to look up a key k, it will pass the query to the cloest successor or predecessor (depending on finger table) of k in its finger table (maybe the larger one on the circle whose ID is smaller than k), until a node find k is stored in its immediate successor

> Procedure
* You have a start point
* First Find the largest node by finger table, largest means it is right most but still small than the target.
* Do it recursively
* Finally Find the point
* The key point is finger table memo the successor(n+2^i) and find the one largest but smaller than target. Then do the same thing with the largest point until no finger table value is smaller than target than we find the result.

* Node join
  * Three invariants should be maintained
    1. Each node's successor points to its immediate successor correctly
    2. Each key is tored in successor(k)
    3. Each node's finger table should be correct
  * We need to maintain predecessor field but do not need to maintain the successor (due to finger table and the position know its sucessor):
  * Tasks:
    1. Initialize node n (the predecessor and finger tables)
    2. Notify other nodes to update their predecessors and finger tables
    3. The new node takes over its responsible keys from its successor
  * 类似于， 每个节点需要知道前一个节点以保证fingertablesearch的largest条件；每个节点需要知道finger table，而finger table知道自己位置所属节点所以就相当于知道了他的sucessor（下一个节点）。 然后就不需要知道别的了，可以完美运行。另外fingertable也是要更新的在点插入的时候。点拿走的时候就更新一下位置的successor和其他点的predecessor就行了
  
* Leave a node
  * Predecessor need to know the failed node's entry
  


