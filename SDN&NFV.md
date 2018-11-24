
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
* Flexibility Problem
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
    
