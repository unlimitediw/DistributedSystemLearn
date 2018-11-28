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
> Serverless Startup
  * AWS Lambda
    * Define a stateless "function" to execute for each request
    * A container will be instatiated to handle the first request
    * The same container will be used until it times out or is killed(clean up)
    * Set multi containers for multi function for easy management and check
    
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
> Two ways to scale
* Scale up(vertical)
  * Buy a bigger computer
* Scale out(hirizontal)
  * Buy multiple computers

> Does virtualization help?

> Replication
* Consistency
  * Replicating data makes it faster to access-> but need keep all data consistent
  * May a lightly out of date wikipedia page(but sometime such as facebook, stock: no)
* Different types of consistency
  * Strict
  * Sequential
  * Causal
  * Eventual

> Spread data across servers
* useful if all data does not fit on one server
* Consider a Key Value store like Memcached
  * Lots of data to store
  * Consistency is not that important
  * Might need to add or remove nodes to the cluster
    
> Distributed Hash Table
* Goals:
  * Evenly partiotn data across nodes
  * Efficient 
  * Graceful when nodes are frequently joing or leaving

* Simple Hash Table(str) -> Hash Function -> int -> value is stored in array[H % S]
* array[H%N] N is # of nodes -> which nodes to watch back
* Churn and Chord
  * Churn
    * module
  * Chord DHT Architecture: Hash Space(points)
    * Nodes pick a random ID when they join: 0 to MAX-1
    * Nodes are assigned contiguous portions of the ring starting at their ID until they reach the subsequent node
    * A small list about what each nodes are responsible for is needed
    * Because # servers changing
    * Chord lookup
     * Options 0: Key index Table
     * Options 1: Node index Table
     * Options 2: Neighbors
      * Each node tracks its successor and predecessor
     * Options 3: Finger Tables
      * Track m addtional neighbors: successor 2^0, 2^1, ..., 2^m
      * Requires minimal state and can find item log(N) steos
