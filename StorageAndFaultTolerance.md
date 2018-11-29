### Some Statistic
> Speed
* L1 cache reference - 0.5ns
* Branch mispredict - 5ns
* L2 cache reference - 7ns
* Mutex lock/unlock - 25ns
* Main memory reference - 1000ns
* Compress 1K bytes with Zippy - 3us
* Send 2K bytes over 1Gbps network - 20us
* SSD random read - 150us
* Read 1 MB sequentially from memory - 250us
* Round trip within same datacenter - 500us
* Read 1 MB sequentially from SDD - 1ms
* Disk seek - 10ms
* Read 1 MB sequentially from disk - 20ms
* Send packet CA -> Netherlands -> CA - 150ms

### Storage
> Storage Services
* Block storage (AWS EBS - Elastic Block Store)
  * Access to the raw bytes of a remote disk
  * Unit of access: disk block (4KB)
  * Mount as the disk for a VM
  * Pros/Cons
    * Different types of underlying storage (SSD vs HDD)
    * Pay per GB but I need to reserve the space in advance.
    * Limited to 16TB per disk
* Object storage (AWS S3 - Simple Storage Service, DynamoDB)
  * Get and Put "objects" in remote storage
  * Unit of access: a full object
  * Store static web content, data sets
  * Pros/Cos
    * Pay per object based on size, also per request and net bandwidth
    * Limit 5 TB per object
* Database (AWS RDS - Relational Database Service)
  * Store structured rows and columns of data
  * Unit of access: SQL query
  * Web applications requiring transaction support
  * Pros/Cons
    * AWS handles management
> Replication Improves
* Performance: Access the least or most geographically close replica
* Reliability: Failure only impacts one copy, can recover from others

### Fault
> Type of Faults
* Crash failure
  * power outrage
  * hardware crash, etc.
* Content failure
  * incorrect value returned
  * could be permanent or transient
  * could be independent or coordinated
* In practice, we make assumptions about how many failures we expect to occur at once

> Fault Tolerance through Replication
* Use Majority vote to tolerate (2f + 1 replicas where f is the #fault)
* But Detection is Hard
  * How long should we set a timeout?
  * How do we know heart beat messages will go through
  * What if the voter is wrong
  
> Agreement without Voters
* Have replicas reach agreement amongst themselves
  * Exchange calculated value and have each node pick winner
  * Majority voting doesn't work for 2f+1 if replica lies
  
> Byzantine Generals Solved
* Need more replicas to reach consensus
  * Requires 3f + 1 replicas to tolerate f byzantine faults
* Procedure:
  1. Send your plan to everyone
  2. Send learned plans to everyone 
  3. Detect conflicts and use majority
  
> Quorum Based Systems
* Quorum: a set of responses that agree with each other of a particular size
* Crash fault tolerance: Need a quorum of 1 (只要有一个备份就好，重启）
  * f others can fail (thus need f + 1 total replicas)
* Data fault tolerance: Need a quorum of f + 1（用于投票）
  * f others can fail (thus need 2f + 1 total replicas)
  * Need a majority to determine correctness
* Some nodes might be temporarily offline, and must wait for response from each one
* N, R and W affect:
  * Performance:
    * low R or W -> higher performance
    * for a fixed R or w: higher N gives higher performance
    * higher N means more synchronization traffic
  * Consistency
    * R + W > N guarantees consistency
    * R + W << N much less likely to be consistent
  * Durability
    * more N more durability
  * Availability
    * Higher N or W higher availability

> Dynamo DB 
* Object Store from Amazon
* Stores N replicas of all objects
  * But a replica could be out of date
  * Might saved across multiple data centers
  * Gradually pushes updates to all replicas to keep in sync
* Read and Write Quorum size:
  * R = 1 -> fastest read performance, no consistency guarantees
  * W = 1 -> fast writes, reads may no be consistent
  * R = N/2 + 1 (reading from majority)
  * R = 1, W = N -> slow writes, but reads are consistent
  * R = N, W = 1 -> slow reads, fast writes, consistent
  
