<a name="menu"></a>
# Docker and Containers 
2. [Containers](#containers)

<a name ="containers"></a>
## Containers
### What is container?
* Runtime OS (e.g linux): inside are processes. The container is used to isolate one process to other processes (sandbox)
  * The container process is tied in with the lifecycle of container.
* Container Image
  * Contains *binary* state: such as VMDK(Virtual Machine Disk) is a disk image OVA(Open Virtual Appliance) is a image for VM.
  * Tree effectively, concentrate specific things in specific places
* Dockerfile
  * Syntax(e.g)
    * From: busybox (within here we can run any number of things that we want to configure the image that the docker file is going to create.
* Runtime OS and Dockerfile can both communicate with Image.
* Docker Host(Tie OS, Image and Dockfile together)
  * Registry outside(pull and push the image you need)
  * Image cache inside (satisfy pulling and pushing of registry)
  * Client outside (Control pull, push, create, run and commit image) 
    * Client can also do network and storage configuration
    * Client manage container lifecycle
  * Deamon inside (Client talk to deamon with API) 
  * Storage inside(but beyond lifecyle) (Volume, a persistent area of storage) or network (push it out on network)
    * Use it if it wants to persist any data beyond the lifecycle of the container
    * Network will also allow persistence after container end
  * Life Cycle 
### Containers and VMs
* VM
  * APP -> OS -> VM -> Hypervisor -> Physical (notice that Application virtualization and Hosted virtualization are onside of OS)
  * VM: NIC(Network interface), Storage and Size
  * Hypervisor: NIC, Storage, Agents and Kernel
* Container
  * App + OS Dependences = Container -> OS -> VM -> Hypervisor -> Physical (VM may be combined using with Container
