# Super Computing

> High Performance Computing

* Super Computer
  - Entire cluster bought at same time
  - High end network, server and storage HW
  - Small number of scientists have access
  
> MPI (Message Passing Interface) (downway)

* MPI
  - Library standard defined by a committee of vendors, implementers, and parallel programmers
  - Used to create parallel programs based on message passing (tree)
  - Popular for scientific computation being performed on high performance compute cluster
  
> Amdahl's Law

* Parts of a program must be run sequentially and parts can be run in parallel.
* Speedup = 1/((1-P) + P/N) N-#of processing entities, P fraction of program taht is parallel

> Big Data Analytics

* Volume: The amount of data companies want to analyze is growing tremendously. 40 trillion GB by 2020

* Variety: Data is often unstructured and/or user generated. Tweets, videos

* Velocity: Analysis must be fast to be useful.

> Map Reduce & Hadoop

* Map Reduce (google developed)
  - Large scale analytics
  - Uses commodity servers
  - Includes a distributed storage system
  - schedule task..

* Hadoop is an open source version of Map Reduce

> Map Reudce Flow

* Input files -> Map -> Key-value -> Reudce -> Output
