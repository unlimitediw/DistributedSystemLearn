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
