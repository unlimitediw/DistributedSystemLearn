### Serverless


* Serverless Startup
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
* Two ways to scale
  * Scale up(vertical)
    * Buy a bigger computer
  * Scale out(hirizontal)
    * Buy multiple computers

* Does virtualization help?

* Replication
  * Consistency
    * Replicating data makes it faster to access-> but need keep all data consistent
    * May a lightly out of date wikipedia page(but sometime such as facebook, stock: no)
  * Different types of consistency
    * Strict
    * Sequential
    * Causal
    * Eventual

* Spread data across servers
  * useful if all data does not fit on one server
  * Consider a Key Value store like Memcached
    * Lots of data to store
    * Consistency is not that important
    * Might need to add or remove nodes to the cluster
    
* Distributed Hash Table
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
  
