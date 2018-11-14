
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
      
    
      

 
  
