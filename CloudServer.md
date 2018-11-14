
### Topic 1: Service

* Infrastructure as a Service(IaaS)
  * Greate flexibility for cloud user and Less management handled by cloud operator
  * Virtual Machines
    * Virtualization is used to split up a physical server
      * Allows multiple customers to share one machine.
      * Simplifies management since VMs are not strictly tied to HW.
      * Provdes isolation between cloud users.
      
* Platform as a Service
  * e.g: Google App Engine, Heroku and Amazon EMR
  * The cloud provides a programming platform
  * Typically used to run highly scalable web apps
  * Cloud users write applications to run on the cloud 
    * Must write code to meet cloud API 
    * Cloud automatically scales the application based on demand 
    * Provides much greater scalability, but program must be specially written

* Software as a Service
  * The cloud provides a piece of software
    e.g: email, office, project management, customer relations, supply chain, etc ( Dropbox, icloud and GMail)
  * Provides even greater scalability
    * Entire cloud infrastructure is devoted just to one particular type of application 
    * Customer: cheaper and simpler, Provider: economy of scale

* Cloud Computing Goals
  * Offer fast services to customers worldwide
    * Need geographic diversity and high scalability
    * Low latency requests: fast responses
    * High throughput: simultaneous processing
  * Highly reliable and secure
    * Servers crash
    * Data center lose power
    * Malicious users can attack
  * As cheaply as possible
    * Users expect services for free
    * Cloud needs to pay for servers, cooling infrastructure, energy, system adminstrators, etc

### Topic 2: Virtualizing Resources

* Type of Virtualization:
  * Application Virtualization:
    * Runs application code
    * Java JVM, WINE
  * Hosted Virtualization
    * Virtualizes a full OS and apps
    * VMware Player, Virtual Box
  * Paravirtualization
    * Modify OS to simplify hypervisor
    * Xen
    
