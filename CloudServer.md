
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

* VM
  * Type of Virtualization:
    * Application Virtualization
      * Runs application code
      * Java JVM, WINE
    * Hosted Virtualization
      * Virtualizes a full OS and apps
      * VMware Player, Virtual Box
    * Paravirtualization
      * Modify OS to simplify hypervisor
      * Xen
    * Full Virtualization
      * Runs directly on HW
      * VMware, ESXi
    * ![](https://github.com/unlimitediw/DistributedSystemLearn/blob/master/Image/VMTypes.PNG)

  * Pros of VM:
    * Consolidation
      * Can spilt a physical server to many smaller servers
    * Security
      * VMs are isolated from one another
    * Resource management
      * Can dynamically adjust a VM's CPU and memory share
    * Convenience
      * VM is abstracted away from physical hardware
      * Great for development
  * Process
    * Virtualization layer replaces an interface
    * Must intercept calls and translate them
      * Java - intercept/compile code to match host
      * Hosted VM - translate system calls for host OS
      * Full Virtualization - trap on sensitive instructions
    * Allocate resources
      * VMs must share memory and CPU time
    * Handle I/O
      * Abstraction layer separates VM from physical hardware
  * Hosted Virtualization
    * Normal OS divided into Kernel and User modes
    * Protected instructions only work in kernel mode
      * I/O, memory allocation, etc
      * Traps to kernel if run in user mode
    * CPU Rings
      * Modern CPUs support multiple protection rings
        * Ring 0 = kernel mode
        * Ring 3 = user mode
        * Ring 1-2 = drivers or unused, hosted virtualization VM OS
    * Dynamic translation
      * Preprocess all code being run by the OS indside the VM
      * Detect sensitive instructions
      * Repackage and call into parent OS
      * Return result to guest OS
  * Full Virtualization'
    * Characteristics
      * Hypervisor runs directly on hardware in Ring 0
      * Manages VMs
      * Uses dynamic translation to rewrite protected instructions
      * Host device drivers for VMs
    * HW Virtualization
      * Some CPUs have support for virtualization
        * AMD-V, Intel-VT
      * Provides an extra ring for running a hypervisor
        * Protected instructions in VM OS are trapped an passed to Ring -1(Hypervisor)
  * Hosted and Full virtualization are VM OS agnostic
    * Guest OS does not know it is being virtualized
    * Translate binary code(slow)
    * Get help from hardware(expensive)
    * Guest OS can help with device drivers
  * Paravirtualization in Xen
    * Modifies Linux so that it is virtualization aware
    * OS asks hypervisor for help to run special instructions
    * Driver VM is special management VM
      * Starts/stops VMs
      * Contains Linux device drivers
    * Very simple hypervisor(Xen hypervisor)
      * Reduces overhead
      * No need for HW virtualization
  * Trade-offs
    * Hosted Virtualization
      * easier to install, and turn off, great for testing/ development
      * neg: fewer resources available, because need host OS
    * Full Virtualization
      * With or without HW assist
      * Strong isolation
      * greater performance than hosted, better scalability
      * neg: needs drivers for all HW
    * Paravirtualization
      * neg: VM is aware it is in a virtual environment(security)
      * need to modify OS
  * Virtualizing Memory
    * System's memory must be shared by all VMs
    * Page Tables
  * Container
    * Properties
      * Processes
        * OS provides isolation
      * Isolated
        * Memory 
        * File system
        * Network
        * Devices
      * Shared
        * OS Kernel
    * Multi-process containers
      * Can run multiple processes in the same container group
    * Resources
      * Can assign CPU weights and memory limits for each group
    * Shared Kernel
      * Page tables (memory)
      * Scheduler (CPU)
      * Networking stack
      * File system virtualization
    * Distro vs Kernel
      * Kernel = core operating system functionality
      * Distribution = collection of software and kernel
    * Containers and Distros
      * Each container can have its own distribution
      * Must share the same host kernel
    * Container Packaging
      * Deployment - big benefit of containers/virtualization
        * Lets you package up an application and all of its requirements
        * Evem the distribution and 3rd party utilities
        * Very helpful for system administrators
    * Container 'image'
      * Linux distribution base files
      * Dependency libs/utils
      * Configuration files
      * Application to run
    * Can inerit files/libraries from host to reduce size of the container package
    * File System Virtualization
      * Container's file system is built by layering
        * Several containers can use the same file system layers
      * Read/Writer
        * Allow multiple containers to manipulate data on host file system
      * Copy on Write
        * Each container thinks it has its own version of the file system
        * Only duplicate the specific files that are written to 
    * Container vs VMs
      * Container Pros
        * lightweight
        * less resource consumption
        * easier to deploy
        * specify resources just for application
        * startup time
      * VMs pros
        * stronger isolation
        * different kernel versions/ OSes
        * fault tolerance/isolation
        * combine with containers
    * Containers + VMs
      * Containers can be combined with virtualization tools
      * Docker on Windows
        * Lets you run windows containers using OS isolation tools
        * Lets you run linux containers by starting a linux VM automatically for you and dividing it up into containers
    * Challenges
      * Heterogeneity: Different HW, SW, workloads
        * HW 
          * different processor architecture, memory, number of CPUs, location disks etc
          * for Iaas users need to know what they will get
          * for Paas/Saas we can hide this
        * Workloads
          * stress out hardware, need to spread request(load balancing)
          * can also help us share resources if peak are at different times
        * SW
          * need to worry about compatibility
          * affects interoperability
      * Openness: interoperability, shared protocols
        * Ranking of openness/flexible: VMs in my data center > IaaS > Containers > Paas > Saas
      * Security: confidentiality, integrity, availability
        * VMs most secure, most control
        * Containers kernel is shared, so less isolation
        * Cloud: Trust? more skilled at providing security?
      * Failure Handling: crashes, bugs, malicious
        * Iaas with Containers/VMs
          * physical failures can bring them all down
          * bugs and attacks with user
        * PaaS/SaaS
          * cloud needs to worry about bugs in their platform and malicious attacks
        * Containers are less isolated than VMs
          * fault in the kernel will bring down all containers
      * Concurrency: parallelism, consistency
        * Depends on SW runing VMs/containers
        * IaaS: depends on users
        * PaaS/Saas: cloud provider must handle concurrency so they limit the type of state you can have to simplify consistency
      * When running multiple VMs, need to worry about scheduling on CPUs
        * Kernel knows about all processes in a container, but sees the VM as a black box 
      * Quality of Service: latency, throughput
        * QoS depends on applications
        * Containers are lighter weight so should have better QoS
        * Qos affected by available HW and workload distribution
        * Tail latency: highly affected by shared resources
          * cache misses will have big impact
          * includes network costs
      * Scalability: performance gain with more resources
        * SaaS has easiest scalability since it has full control
        * IaaS harder to scale
          * User can ask for resources
          * Cloud can monitor and respond
        * Containers are more scalable because lighter weight
          * we have great control over how resources are being used
      * Transparency: abstraction layers, interfaces
        * IaaS exposes HW interface
        * PaaS exposes software library interface
        * SaaS exposes user interface for software
        * Data transparency -> storage details hidden from us
        * Logic transparency -> affects what SW we can run
        
        
      
      
      
    
      

 
  
