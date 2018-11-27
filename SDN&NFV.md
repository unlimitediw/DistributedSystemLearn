
### SDN + NFV

* Software-based Networks
  * SDN: Software Defined Networking - control plane
  * NFV: Network Function Virtualization - data plane

* Switch: Match on Destination MAC
  * MAC addresses are location independent
    * Assigned by the vendor of the interface card
    * Cannot be aggregated across hosts in LAN
  * Router: Match on IP Prefix
    * IP addresses grouped into common subnets
      * allocated by ICANN(Internet Corporation for Assigned Names and Numbers), regional registries, ISPs , and within individual organizations
      * Variable-length prefix identified by a mask length
  * Forwarding vs. Routing
    * Forwarding: data plane
      * Directing a data packet to an outgoing link
      * Individual router using a forwarding
    * Routing: control plane
      * Computing paths the packets will follow
      * Routers talking amongest themselves
      * Individual router creating a forarding table
  * Distributed Control Plane
    * Link-state routing: OSPF, IS-IS
      * Flood the entire topology to all nodes
      * Each node computes shortest paths
      * Dijkstra's algorithm
### Flexibility Problem
  * All packets arriving at a switch/router are treated the same
    * Only consider the desination IP/MAC address to decide path
  * Traffic Engineering Problem
    * Management plane: setting the weights
      * Inversely proportional to link capacity?
      * Proportional to propagation delay?
      * Network-wide optimization based on traffic?
  * Optimization
    * Inputs
      * Network topology
      * Link capacities
      * Traffic matrix
    * Output
      * Link weights
    * Objective
      * Minimize max-utilized link
      * Or, minimize a sum of link congestion
  * Transient Routing Disruptions
    * Topology changes
      * Link weight change
      * Node/link falure or recovery
    * Routing convergence
      * Nodes temporarily disagree how to route
      * Leading to transient loops and blackholes
  * Management Plane Challenges
    * Indirect control
      * Changing weights instead of paths
      * Complex optimization problem
    * Uncoordinated control
      * Cannot control which router updates first
    * Interacting protocols and mechanisms
      * Routing and forwarding
      * Naming and addressing
      * Access control
      * Quality of service

* Solution: Software Defined Networking(SDN)
  * Decouples the control plane from the data plane
  * SDN makes the network programmable
  * OSPF, DiffServ, IntServ, MPLS, RSVP
    * All such protocols can be done in software, controlled by a central instance
    * Scalable, easily manageable, better interoperability
  * SDN Components
    * Application Layer
    * Control Layer: network services
    * Infrastructure Layer: network device
    * Programmable Open API
      * Connects applications with control plane
      * Allows for programming of routing
    * Standard Communication Interface (e.g. OpenFlow)
      * Between control and data planes
      * Allows direct access to forwarding plane
    * Network Controller (logically centralized)
      * Sets up rules, actions, etc. for the network devices
      * Core element of SDN
    * Benefits
      * elastic resource allocation
      * distribution of the load on links
      * scalability
      * overhead reduction
    * OpenFlow: The SDN Protocol
      * Communication between the controller and the network devices
    * OpenFlow Basics
      * Control Program A, Control Program B...
      * Network OS
      * OpenFlow Protocol
      * Ethernet Switch
    * Plumbing Primitives
      * Match
        * Match arbitrary bits in header
        * Allows 'any' flow granularity
      * Action
        * Forward to port, drop, send to controller
        * Overwrite header with mask, push or pop
        * Forward at specific bit-rate
    * OpenFlow - Switches
      * Incoming packets are matched against rule tables
      * Find highest priority match and execute actions
        * Send out port
        * Forward to another table
        * Drop
        * Rate limit
      * Procedure
        * Packet in -> (Ingress port)Table 0 -> (Packet + Ingressport + Metadata)Table1 -> ... -> Table n -> (packet)Execute action set -> Packet out(out of OpenFlow Switch)
      * If no match in table: table miss
      * Handling: depends on table configuration: might be drop packet, forward to other table, forward to controller
      * Forward to controller allows to set up a flow entry
### SDN Workflow
  * Data plane (switches): maintains a flow table
    * Flow = one point-to-point connection (Src/Dest IP and Port)
    * Action = how switch should process the packet
  * Control plane: defines fllow table rule for switches
    * Can be based on business logic
    * Select next hop, drop, mirror
### Network Function Virtualization
  * Make an efficient, customizable data plane
    * routers, switches, firewalls, proxies, IDS, DPI
  * Run network functions (NFs) in virtual machines
    * More flexible than hardware
    * Isolates functionality, easy to deploy and manage
    * Slower than hardware
### Network Data Plane
  * Perform network functionality on custom ASICs (Application Specific Integrated Circuit)
    * Fast, expensive, inflexible
### Software Based Data Plane
  * Hardware Routers and Switches
    * Expensive, single purpose
    * Controllable with SDNs, but not flexible
  * PacketShader
    * Use commodity servers and GPUs
    * 39 Gbps processing rates
  * Netmap and DPDK (Data Plane Development Kit)
    * Libraries to provide zero-copy network processing on commodity 10gbps NICs (Network Interface Controller)
  * ClickOS and NetVM
    * VM based network services
    * Flexible deployment and composition
### Network Functions(NFs)
* Switches, routers, firewalls, NAT
  * Simple packet header analysis and forwarding
* Intrusion Detection System (IDS)
  * Deep packet inspection (DPI) beyond header to detect threats
  * Must have high scalability to observe full packet flows
* Intrusio Prevention Sytems (IPS)
  * Similar to IDS, but deployed in line, so it can actively manipulate traffic flows
  * Must be efficient to avoid adding delay 
* Cellular functions (Evolved Packet Core - EPC)
  * Mobilit management, accounting, security, etc
* Proxies, caches, load balancers, etc

### Linux Packet Processing
* Traditional networking
  * NIC(Network Interface) uses DMA(Direct Memory Access) to copy data into kernekl
  * Interrupt when packerts arrive
  * Copy packet data from kernel space to user space
  * Use system call to send data from user space
### User Space Packet Processing: Recent NICs and OS support allow user space apps to directly access packet data
  * NIC uses DMA to copy data into user space buffer (rather than kernel)
  * Use polling to find (rather than interrupt) when packets arrive
  * Use regular function call (rahter than system) to send data from user space
### Data Plane Development Kit (DPDK)
  * High performance I/O libraruy
  * Poll mode driver (PMD, Receive and transmit packets more and fast) reads packets from NIC
  * Packets bypass the OS and are copid directly into user space memory
  * Low level library does not provide:
    * Support for multiple network functions
    * SDN-based control
    * Interrupt-driven NFs
    * State management
    * TCP stack
  * Strength:
    * Applications that need high speed access to low-level packet data
    * One of the best documented open source projects
  * Line rate (PacketsPerSec/PacketSize)
    * Network Infrastructure Packet Sizes: 64bytes, 33 cycles(16.8ns)
    * Typical Server Packet Sizes: 1024bytes, 417 cycles(208.8ns)
  * How to eliminate/ hide overheads
    > Polling - Solve "Interrupt Context Switch Overhead"  
    > Huge pages - Solve "NK Paging Overhead"  
    > User Mode Driver - Solve "Kernel User Overhead"  
    > Lockless Inter Core Communication  
    > Pthread Affinity - Solve "Core To Thread Scheduling Overhead"  
    > High Throughput Bulk Mode I/O calls - Solve "PCI (Peripheral Component Interconnect) Bridge I/O Overhead"  
### Network Interrupts
    
