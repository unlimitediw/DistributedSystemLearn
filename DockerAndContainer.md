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
> VM  
  * APP -> OS -> VM -> Hypervisor -> Physical (notice that Application virtualization and Hosted virtualization are onside of OS)
  * VM: NIC(Network interface), Storage and Size  
  * Hypervisor: NIC, Storage, Agents and Kernel
  
> Container  
  * App + OS Dependences = Container -> OS -> VM -> Hypervisor -> Physical (VM may be combined using with Container  
### Docker Introduction
[LearnPage]
> Task 0: Prerequisites
  * It need a linux environment and a DockerID
> Task 1: Run some simple Docker containers
  * Three different ways to use containers:
    1. To run a single task: This could be a shell script or a custom app.
    2. Interactively: This connects you to the container similar to the way 
    3. In the background: For long-running services like websites and databases
  * Run a single task in an Alpine Linux container
    1. Run "docker container run alpine hostname". The container will start, execute hostname command then exit
        * Pull "alpine:latest" image from Docker Hub
        * The container's hostname will be displayed
    2. Check container states after hostname processing exits(container stop) by running "docker container ls --all"
        * Docker doesn't delete resources by default and the container still exist in the "Exited" state
  * Run an interactive Ubuntu container
    1. Run a Docker container and access its shell by "docker container run --interactive --tty --rm ubuntu bash"
        * "--interactive" says you want an interactive session
        * "--tty" allocates a pseudo-tty
        * "--rm" tells Docker to go ahead and remove the container when it's done exuecuting
        * bash(which is a shell, a user interface for access to an operating system) is used as main process
        * Now you are in the bash shell
        * When the container start you will drop into the bash shell with the default prompt root@<container id>:/#. Docker has attached to the shell in the container, relying input and output between your local session in the container.
    2. Run "ls /", "ps aux" and "cat/etc/issue"
        * "ls /" list the contents of the root director in the container
        * "ps aux" show running processes in the container
             * USER PID ... STAT START TIME COMMAND
             * root 1 ... SS 00:12 0:00 bash
             * root 14 ...R+ 00:22 0:00 ps aux
    3. Type "exit" to leave the shell session. This will terminate the bash process, causing the container to exit
  * Run a background MySQL container
    1. Run a new MySQL container with
       ####
           docker container run \
           --detach \
           --name mydb \
           -e MYSQL_ROOT_PASSWORD=my-secret-pw \
           mysql:latest
        * "--detach" will run the container in the background
        * "--name" will name it mydb
        * -e will use an environment variable to specify the root password

    2. List the running containers "docker container ls".
    3. Check what's happening in your containers by "docker container logs" and "docker container top".
    4. List the MySQL version using "docker exec"(which allow you command inside a container) and run "mysql --user=root --password=$MYSQL_ROOT_PASSWORD --version".
    5. You can also use "docker exec -it mydb sh" to connect to a new shell process inside an already running container
  > Task 2: Package and run a custom app using Docker  
  
  * Build a simple website image
  1. "cd ~/linux_tweet_app"
  2. "cat Dockerfile"
     * cat: concatenate command in linux
     * From: specifies the base image to use as the starting point for this new image you're creating. For this example we're starting from nginx:latest
     * COPY: copies files from the Docker host into the image, at a known location. In this example, COPY is used to copy two filesinto the image: index.html. and a graphic that will be used on our webpage
     * EXPOSE: documents which ports the application uses
     * CMD: specifies what command to run when a container is started from the image (both command and run-time arguments)
  3. "export DOCKERID=unlimitediw"
  4. "echo $DOCKERID"
  5. Use the "docker image build" command to create a new Docker image using the instructions in the Dockerfile
   * "--tag" allows us to give the image a custom name.
   * "." tells Docker to use the current directory as the build context
  6. Use the "docker container run" command to start a new container from the image you created.
    * use "--publish" to publish port 80 inside the container onto port 80 on the host. (80 port to 80 port directly in container)
    * --publish format flag "host_port:container_port"
  7. "docker container rm --force linux_tweet_app" shut down and remove
### Doing more with Docker images
> Image creation from a container  
* Running the bash shell inside the container and intalling figlet package in the container to customize things.
#
    docker container run -ti ubuntu bash  
    apt-get update
    apt-get install -y figlet
    figlet "hello docker"
* Create an image to share  
To see a list of files that were added or changed when installed figlet. (a little like logs)
#
    docker container ls -a
    docker container diff 63f7e97d2f73  
To commit the container and create an image out of it
#
    docker container commit 63f7e97d2f73  
![](https://github.com/unlimitediw/DistributedSystemLearn/blob/master/Image/ImageCreation.png)  
#
    docker image tag 1380f5719fab ourfiglet
    docker image ls
    docker container run ourfiglet figlet hello  
![](https://github.com/unlimitediw/DistributedSystemLearn/blob/master/Image/DockerOurfiglet.png)  
We can create a container and add all libraries and binaries in it and then commit it to create an image. We can use the image s pulled from the Docker Store and share this image by pushing it to a registry somewhere.  
> Image creation using a Dockerfile  
Instead of creating a static binary image, we can also use Dockerfile to create an image.Dockerfile supplies the instructions for building the image which is uselful to manage changes (how an image is built). Dockerfiles are simply text files and can be managed as source code.  
The following work will start by creating a file which I retrieve the hostname and display it.




