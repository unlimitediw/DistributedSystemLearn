### Concurrency

> Clocks and Timing
* Coordinating updates to a distributed file system
* Managing distributed locks
* Providing consistent updates in a distributed DB

> Coordinating time and Cristian's Algorithm
* Client sends a clock request to server -> Measures the round trip time -> Set clock to t + 1/2 * RTT
* Problem:
  * Come ahead or behind

> Ording 
* Sometimes we don't actually need clock time and we just care about the order of events
* event:
  * Action that occurs within a process
  * Sending a message
  * Receiving a message

> Lamport Clock
* Definitions:
  * Each process maintains a counter (specific timeline counter), L
  * Increment counter when an event happens
  * When receiving a message, take max of own and sender's counter, then increment
* Only forward is always true (e->e' then L(e) < L(e')), backward is not guaranteed. Lamport clocks makes limited guarantees.

> Vector Clocks
* Each process keeps an array of counters: e.g. V(p1, p2, p3) （只在自己的timeline上走，继承其它timline值）
  * When pi has an event, increment V(pi)
  * Send full vector clock with message
  * Update each entry to the maximum when receiving a clock
* Vector Clocks Comparison
  * VC(x) is less than VC(y) if and only if VC(x)i is less than or equal to VC(y)i for all process indices i and at least one of those relationships is strictly smaller.
  
> Version Vectors
* Based on Vector Clocks
* When a piece of data is updated:
  * Tag it with the actor *who* is modifying it and version #
  * Treat the pairs like a vector clock
* Functions:
  * Can determine a causal ordering of updates
  * Can detect concurrent updates
* Need to have a policy for resolving conflicts
  * If two versions are concurrent, they are "siblings" and return both.
  * Random, Priority based or User resolved
* Procedure:
  1. Initially all vector counters are zero
  2. Each time a replica experiences a local update event, it increments its own counter in the vector by one. (Vector Clocks)
  3. Each time two replicas a and b synchronize, they both set the elements in their copy if the vector to the maximum of the element across both counters: Va(x) = Vb(x) = max(Va(x),Vb(x)). After synchronization, the two replicas have identical version vectors.
> Multi-Tier Backup
* Consider a multi-tier web app backup system
  * Some tiers have a disk that must be protected
  * All writes to protected disks must be replicated to a backup
  * Can only send responses to a client once writes have been successfully backed up.
> Tracking Dependencies
* Use Vector Clocks to track pending writes
  * One entry per protected disk <D1,D2 ... Di>
* Node i increments Di on each write
* Use vector clocks to determine a causal ordering

> Ordered Asynchrony
* Allowing processing to proceed asynchronously provides major performance advantage
* Provide performance advantage
  * But need vector clocks to determine ordering and dependencies
  
> Time and Clocks
* Synchronizing clocks is difficult
* But often, knowing an order of event is more importatn than knowing the "wall clock" time
* Lamport and Vector Clocks provide of ways of determining a consistent ordering of events but some events might be treated as concurrent.

### Distributed Coordination

> (Distributed) Locking
* We need mutual exclusion to protect data
  * Among processes and threads
  * Among distributed servers
  * Centralized or decentralized

> Centralized Approach
* Simplest approach: put one node in charge
* Other nodes ask coordinator for each lock
  * Block until they are granted the lock
  * Send release message when done
* Coordinator can decide what order to grant lock

> Distributed Approach
* Use Lamport Clocks to order lock requests across nodes
* Send Lock message with clock
  * Wait for OKs from all nodes
* When receiving Lock msg:
  * Send OK if not interested
    * Wait for OKs from all nodes
  * If I want the lock
    * Send OK if request's clock is smaller
    * Else, put request in queue
* When done with a lock
  * Send OK to anybody in queue

> Ring Approach
* Definition
  * Nodes are ordered in a ring
  * One node has a token
  * If you have the token, you have the lock
* Can be slow by pass

> Comaprison
* Messages per lock/release
  * Centralized: 3
  * Distributed: 2(n-1) (bi dir for each)
  * Token Ring: unknown 0-1 (may get may not)
* Delay before entry
  * Centralized: 2
  * Distributed: 2(n-1)
  * Token Ring: 0 to n-1 in sequence
* Problems
  * Centrolized: Coordinator crashes
  * Distributed: anybody crashes
  * Token Ring: lost token, crashes
  
> Hard Points
* Slower, More error prone, much more complicated(complex protocol, need know all other)

> Distributed Architectures
* Elections
  * Appoint a central coordinator
    * But allow them to be replaced in a safe, distributed way (may avoid Coordinator crashes)
  * Must be able to handle simultaneous elections - reach a consistent result
> Bully Algorithm
* The biggest (ID) wins
* Any process P can initiate an election
* Procedure:
  * P sends Election messages to all process with higher Ids and awaits OK messages
  * If it receives an OK, it drops out and waits for an I won
  * If a process receives an Election msg, it returns an OK and starts an election
  * If no OK messages, P becomes leader and send I Won to all process with lower Ids
  * If process receives a I Won, it treates sender as the leader

> Ring Algorithm
* ProcedureL
  * Initiator sends and Election message around the ring
  * Add your ID to the message
  * When Initiator receives message again, it announces the winner
* What if mutiple elections occur at the same time? (ring in order)

> Comparison
* Bully Algorithm
  * Worst case: lowest ID node initiates election
    * Triggers n - 1 elections at every other node = O(n^2) messages
  * Best case: Immediate election after n - 2 messages

* Ring Algorithm
  * Always 2(n-1) message
  * Around the ring, then notify all

> ELections + Centralized Locking
* Elect a leader
* Let them make all the decisions ahout locks
