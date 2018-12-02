<a name="menu"></a>
## Big Data and Machine Learning
1. [Hadoop Introduction](#introduction)
2. [Analyze Big Data with Hadoop](#bigdata)
3. [Introduction to Amazon Simple Storage Service](#s3)
4. [Introduction to Amazon Redshift](#redshift)
5. [Intro to Amazon Machine Learning](#introml)
6. [Build a Machine Learning Model](#buildml)
7. [AWS SageMaker Overview](#viewsegmaker)
8. [AWS SageMaker](#segmaker)
9. [Build a Serverless Real-Time Data Processing APP](#app)
10. [Conclusion for the Unicorn project](#unicorn)

## Docker and Containers
1. [Why Docker?](#docker)
2. [DevOps Docker Beginners Guide](#guide)

* Author: [@unlimitediw](https://github.com/unlimitediw)
* Resources: [CS 6421 Distributed Systems - Fall 2018, provided by @Prof. Tim Wood](https://gwdistsys18.github.io/)

# BIG DATA AND MACHINE LEARNING

<a name="Introduction"></a>
# Hadoop Introduction (2h)
[Back to Menu](#menu)
### Overgrowing data problem and management:
- High chances of system failure.
- Limit on bandwidth.
- High programming complexity.

Hadoop is a framework that allows for distributed processing of large data sets across clusters of commodity computers using simple programming models.

### Hadoop key characteristics:
- Economical
Ordinary computers can be used for data processing.
- Reliable
Stores copies of the data on different machines and is resistant to hardware. failure
- Scalable
Can follow both horizontal and vertical scaling.
- Flexible
Can store as much of the data and decide to use it later.

    In my project, I may not need to use Hadoop for a single video processing. However, if I need to deal with large amount of concurrent image data, I need the Hadoop with such for important characteristics.

### Traditional Database System vs. Hadoop
- Tradition Database System:
	1.	Data store in central location.
	2.	Sent data to processor at run time.

    Works well with limited data. However modern systems receive terabytes of data per day and it is difficult for the traditional computer or relational database management system such as RDBMS-Relational Database Management System.
- Hadoop:
	1.	Program goes to the data.
	2.	It initially distributes the data to multiple systems and later runs the computation whenever the data is located.

### Hadoop components
- Hadoop Ecosystem core components
	1.	Processing.
	2.	Resource management.
	3.	Storage.

- Data processing:
    * Hadoop Distributed file system (HDFS):
    	A storage layer of Hadoop.
    	Suitable for the distributed storage and processing.
    	Hadoop provides a command line interface to interact with HDFS Streaming access to file system data.
    	Provides file permissions and authentication
    * Hadoop map reduce:
    	The original Hadoop processing which is primarily Java based
    	Based on the map and reduce programming model Commonly used
    	An extensive and mature fault tolerance framework
    * Spark:
    An open-source cluster computing framework
    Provides 100 times faster performance as compared to MapReduce
    Support Machine learning, Business intelligence, Streaming, and 
    Batch processing
    Apache Spark
		* Spark Core and Resilient Distributed Database (RDDs)
		* Spark SQL
		* Spark Streaming
		* Machine Learning Library (Mlib)
		* GraphX
* Data Ingestion:
	* Sqoop:
Sqoop is a tool designed to transfer data between Hadoop and relational database servers
It is used to import data from relational databases such as, Oracle and MySQL to HDFS and export data from HDFS to relational databases.
    * Flume:
	A distributed service for ingesting streaming data.
	Ideally suited for event data from multiple systems.
* Data Analysis:
	* Pig:
		An open-source dataflow system.
		Converts pig script to Map-Reduce code.
		An alternate to writing Map-Reduce code.
		Best for ad-hoc queries like join and filter.
    * Impala:
    	High performance SQL engine which runs on Hadoop cluster.
    	Ideal for interactive analysis.
    	Very low latency – measured in milliseconds.
    	Supports a dialect of SQL (Impala SQL).
    * Hive:
    	Similar to Impala.
    	Best for data processing and ETL (extract, transformation, load).
    	Executes queries using MapReduce.
* Data Exploration:
	* Cloudera Search:
        One of Cloudera’s near-real-time access products.
        Users do not need SQL or programming skills to use Cloudera Search.
        Enables non-technical users to search and explore data stored in or ingested into Hadoop and HBase.
A fully integrated data processing platform.
    * Hue:
        Hue is an acronym for Hadoop User Experience
        Hue is an open source Web interface for analyzing data with Hadoop
        It provides SQL editors for Hive, Impala, MySQL, Oracle, PostgreSQL, Spark SQL, and SolrSQL.
* Workflow System:
	* Oozie:
		Oozie is a workflow or coordination system used to manage the 
		Hadoop jobs
####
    		Start
    			Loop actions
    				Oozie Coordinator Engine
    					Oozie Workflow Engine
    		End
* NoSQL:
	* HBase:
        Store data in HDFS.
        A NoSQL database or non-relational database Mainly used when you need random, real time, read/write access to your Big data
        Provides support to high volume of data and high throughput
        The table can have thousands of columns.

<a name="bigdata"></a>
# Analyze Big Data with Hadoop (1h)
[Back to Menu](#menu)

### Task 1: Create an Amazon S3 bucket
* What is S3? Simple Storage Service is designed to make web-scale computing easier for developers. In my opinion, simple means that we can have more time to deal with other hard tasks and boost the probability of finish a project entirely.
* In this task, I will create a bucket to store my log files and output data.
(P.S. log file is a file that records either events that occur in an operating system or other software runs, or messages between different users of a communication software.
* In my works, I will use some image label tools to process the uploading image frame and take parts of it with high confidence as training data and store them in S3 bucket. I may also store the client information and log files into the S3 bucket
### Task2: Launch an Amazon EMR (Elastic MapReduce) cluster
* Introduction:
EMR provides a managed Hadoop framework that makes it easy, fast and cost-effective to process vast amounts of data across dynamically scalable Amazon EC2 instances. EMR is a data processing tool with a broad set of big data use cases, including log analysis, web indexing, data transformations (ETL), machine learning, financial analysis, scientific simulation, and bioinformatics.
* In EMR, you can run custom Map reduce code, Spark (data processing) or frameworks such as Hive, Pig, Impala (data analysis) and HBase (NoSQL), Apache Hadoop, Ganglia, Apache Tez.
* User can build as many clusters as they need to test new ideas and terminate the clusters when they’re no longer needed. In my project, I may build several EMR with several different CNN models and test their real time image processing ability at the same time and take a comparison at the same time.
* For security demand, you can launch clusters in an VPC (Virtual Private Cloud), a logically isolated network that you define.
* Data to be Processed:
Use cluster to process log data from Amazon CloudFront which is a web service that speeds up distribution of static and dynamic web content such as .html, .css, .php, and image files.
* In this task, log data contains: Date, Time, Edge Location, Bytes, IP, Method, Host, URI (uniform resource identifier), Status, Referrer, User Agent
### Task3: Process your sample data by running a hive script
* Hive is a data analysis tool which is best for data processing and ETL.
* There are three steps for the hive operation:
    1. Create table. 
    2. Use regex to read log file. 
    3. Use HiveQL query to calculate requests by operating system.
Finally, it will give us a operating system counting table
### Task4&5: view result and terminate amazon EMR Cluster 

<a name="s3"></a>
# Introduction to Amazon Simple Storage Service (S3) (30mins) 
[Back to Menu](#menu)
### Task 1-3: 
1. Create bucket.
2. Upload image file. 
3. Open the read access manually.
### Task 4: Create a Bucket Policy
    Use AWS Policy Generator to generate S3 Bucket.
    Select S3 Bucket Policy, write ‘*’ in Principal and paste ARN (Amazon Resource Name).
    The policy looks like:
    {
      "Id": "Policy***************",
      "Version": "2012-10-17",
      "Statement": [
            {
              "Sid": "Stmt*************",
              "Action": [
                "s3:GetObject"
              ],
              "Effect": "Allow",
              "Resource": "arn:aws:s3:::********(bucket name)/*",
              "Principal": "*"
            }
        ]
    }
    After generation of policy and being applied in the Bucket Policy, the bucket is public accessible now.
### Task 5: Explore Versioning:
    Add action “s3:GetObjectVersion” in “Action” will allow access old version of a file.

<a name="redshift"></a> 
# Introduction to Amazon Redshift (1h 30mins)
[Back to Menu](#menu)
* Amazon Redshift is a data warehouse to analyze data using standard SQL and existing Business Intelligence tools.
### Task 1: Launch an Amazon Redshift Cluster
* Launch cluster to open the Redshift Cluster Creation Wizard
(Optional: node type, control encryption, VPC)
* Cluster Properties: Contains information about the Cluster including: Name, Type, Node Type, number of Nodes, Zone location, Time and version of creation as well as other information
<!-- Tables -->
| Cluster Props   | value           |
|-----------------|-----------------|
|Cluster Name     |lab              |
|Cluster Type     |Single Noed      |
|Node type        |dc2.large        |
|Nodes            |1                |
|Zone             |us-east-1e       |
|Created Time     |*                |
|Maintenance Track|Current          |
|VPC ID           |vpc-*            |
|VPC sec groups   |defa.redshift-1.0|
|Cluster Par Group|lab              |
|Cluster Name     |lab              |
|Cluster Name     |lab              |
* Cluster Status: Allows you to see the current status of the cluster whether it is available or not and also whether it is currently in Maintenance Mode
* Cluster Database Properties: Contains information on the Endpoint, which is the DNS address of the cluster, and the port number on which the database accepts connections. These are required when you want to create SQL connections. It also lets you know whether the cluster has a public IP address that can be accessed from the public internet. The JDBC URL and ODBC URL contain the URLs to connect to the cluster via a java database connection or an Oracle database connection client.
<!-- Tables -->
| Cluster DB Props| value           |
|-----------------|-----------------|
|Port             |1234             |
|Publicly Access  |Yes              |
|Database Name    |labdb            |
|Master Usernaem  |master           |
|Encrypted        |No               |
|JDBC URL         |jdbc:redshift://~|
|ODBC URL         |Driver={};       |
|ODBC URL         |Server=~         |
|ODBC URL         |DB=labdb;UID     |
|ODBC URL         |PWD=pwd;Port=1234|
* Backup, Audit Logging and Maintenance: Contains information on how many days the automaated snapshots are retaubedm whether they are automatically copied to another region, and whether logging is enabled on the cluster.
* Capacity Details: Contains information about the data ware house node type, number of EC2 Compute Units per node, memory, disk storage, I/O performance as well as the processor architecture of the node type.
* SSH Ingestion Settings: Contains information about the Public Key of the cluster as well as the Public and Private IP addresses of the node.

### Task2-3: Launch Pgweb to Communicate with Redshift cluster and create a Table
* Any SQL client that supports JDBC or ODBC can be used with Redshift. In this task, Pgweb which provides a friendly SQL interface to Redshift will be used.
* Use redshift cluster endpoint as host and port and connect on Pgweb.
* Then use SQL command to create a table in Redshift which contains name, address and details about the type of music that user likes.

### Task 4-5: Load Sample Data from Amazon S3 and Data processing
* Amazon Redshift can import data from Amazon S3. Various file formats are supported, fiexd-length fields, comma-separated values(CSV) and custom delimiters. The data for this lab is pipe-separated.
* Use SQL Query to load and show Data in Redshift.
* Analyze data with Query.

<a name="introml"></a> 
# Intro to Amazon Machine Learning (1h 30mins)
[Back to Menu](#menu)
### Task 1: Uploading Training Data
* Upload restaurant.data to a S3 bucket
* Features in order: age, gender, budget, price, cuisine_type, rating

### Task 2-3: Create a Datasouce
* Does the first line in your CSV contain the column names? Yes, which is the feature names in order.
* Use "age, gender, budget, price, cuisine_type" as input data set and "rating" as label.
* AWS also allow custom ML model with recipe.

### Task 4: Evaluate an ML Model
* In the learning task, 70% of data is used to training and 30% of data is used to validate and test.
* The AWS provided ML model is multclass classification.
* The result will be visualized by confusion matrix. In the confusion matrix, the rows represent the true values and the columns represent the predicted values. 
![](https://s3.amazonaws.com/data357/ConfusionMatrix.PNG)

<a name="buildml"></a> 
# Build a Machine Learning Model (1h 15mins)
[Back to Menu](#menu)

### Task 1: Prepare your data
* Data Resource: [UCI Machine Learning Repository](http://archive.ics.uci.edu/ml/index.php)
* Data: 
	* banking.csv (used for training and validation)
	* banking-batch.csv (used for test)
* Features:
age | job | marital | education | default | housing | loan | contact | month | day_of_week | duration | campaign | pdays | previous | poutcome | emp_var_rate | cons_price_idx | cons_conf_idx | euribor3m | nr_employed
* Label: Y = [0,1]

### Task 2-3: Create a Training Datasource and Create an ML Model
* Use [Amazon Machine Learning](https://console.aws.amazon.com/machinelearning/.) 
* Link the input data with banking.csv which is uploaded in S3 bucket
* There are three types of data: Numeric, Categorical, Binary
* Use a binary classification model

### Task 4: Review the ML Model's Predictive Performance and Set a Score Threshold
* During evaluation, Amazon ML computed an industry-standard quality metric, called the Area Under a Curve (AUC) metric, that expresses the performance quality of your ML model.
* Review the AUC metric:
	* In the label set, 11% of it is 'yes' and the other is 'no'.
	* In our prediction, True positive rate is 4% and false positive rate is 1.8%, which means that the precision is 69.0%. At the same time, the false nengative rate is 7% which means that the recall rate is 36.4%.
	* In the score threshold model, the prediction is based on the cut-off number. Although I can not see the algorithm inside AWS calssification model, it is believable that this image shows the distribution of data base on their prediction values with sigmoid function.
	![](https://s3.amazonaws.com/hadoop357/explore-screenshot-v1.png)
	* Generally speaking, the score setting is based on the client demand. If they have a lots of money and is willing to invest with high risk may be they can select a lower score to reduce the false negative rate but increase true positive rate while maintain the accuracy in a acceptable level.
	![](https://s3.amazonaws.com/hadoop357/eva.PNG)
	
### Task 5: Use the model
* record format: comma spilt
	* 32,services,divorced,basic.9y,no,unknown,yes,cellular,dec,mon,110,1,11,0,nonexistent,-1.8,94.465,-36.1,0.883,5228.1
* Real-time predictions will give us the prediction result directly
* The cost of batch prediction is 0.1$/1000prediction. To be honest, the model construction and prediction for 42000 messages will only takes few minutes on my computer. We should be care about it since it is expensive.
* There are three columns of the batch prediction result: trueLabel, bestAnswer and score. It is due to that I select the banking.csv to batch predict.
* In my model, the score larger than 0.35 will be taken as 1 and the score smaller or equal than 0.35 will be taken as 0.

<a name="viewsegmaker"></a> 
# AWS Overviwe of SageMaker (1h 30mins)
[Back to Menu](#menu)
* The most important things I believe is that you can use your own scripts to train your model.
### What is SageMaker?
* Amazon SageMaker enables you to build, train, and deploy machine learning models quickly and easily while taking care of the heavy lifting of machine learning.
	* Build: Provides hosted Jupyter notebooks that require no setup and allows to start processing the training dataset & developing algorithms immediately.
	* Train: One-click, on-demand distributed training with built-in model tuning that can automatically adjust hundreds of different combinations of algorithm parameters for greater, speed, accuracy, and data-throughput.
	* Deploy: An elastic, secure, and scalable environment to host your models, with one-click deployment.
### Overview
* Notebook instance: Explore AWS data in your notebooks, and use algorithms to create models via training jobs.
* Jobs(training): Track training jobs at your desk or remotely. Leverage high-performance AWS algorithms.
* Models(host and repository)
: Create models for hosting from job outputs, or import externally trained models into Amazon SegaMaker.
* Endpoint(deploy): Deploy endpoints for developers to use in production. A/B Test model variants via an endpoint.
### SageMaker tutorial
* Create notebook instance
	* There may have some bugs if Data and Instance not live in the same region in a S3 bucket.
* e.g Training the XGBoost model
	* Images hosted in different regions.
	* ResourceConfig: size of the training instance used to deploy model
	* HyperParameters: such as "max_depth", "eta", "gamma" etc.
		* p.s You call something a 'hyperparameter' if it cannot be learned within the estimator directly. 
	* Use API to train single or multiple instances.
		* e.g sm.create_training_job(**single_machine_job_params)
* In the job menu, you will see the progress information here.
* Then hosted the model in SageMaker for prediction and it is hosted in a container.
	* Grab some information from train model.
	* Build endpoint configuration.
	* You can also build multiple model to do the a/b testing("two-sample hypothesis testing).
* Test and get error rate and confusion matrix.
* You can also use model trained outside in SageMaker.
* After laiding all files, scripts and libraries you need to write a docker file and build a container.
* Create repository and push container to ECR(Elastic Container Registry). 
	
	

<a name="segmaker"></a> 
# AWS SageMaker (2h)
[Back to Menu](#menu)
* I lost my .md file in this episode, so the following introduction will be more concise.
### Getting Started
* Create an S3 bucket a usual.
* Create an SageMaker Notebook Instance
* Train Model and Deploy it
* p.s Some important image I used before this episode lost:
	* SageMakerLoop	
		![](https://s3.amazonaws.com/hadoop357/SageMakerLoop.PNG)
	* BatchTransform
		![](https://s3.amazonaws.com/hadoop357/batchTransform.PNG)

<a name="app"></a> 
# Build a Serverless Real-Time Data Processing APP (6h)
[Back to Menu](#menu)
### Overview
* In this project, I’ll build a serverless app to process real-time data streams and build infrastructure for a fictional ride-sharing company. In this case, I will enable operations personnel at a fictional Wild Rydes headquarters to monitor the health and status of their unicorn fleet. Each unicorn is equipped with a sensor that reports its location and vital signs.

### Procedure
* Build a data stream
	* Create a stream in Kinesis and write to and read from the stream to track Wild Rydes unicorns on the live map.
	* create an Amazon Cognito identity pool to grant live map access to your stream.
* Aggregate data
	* Build a Kinesis Data Analytics application to read from the stream and aggregate metrics like unicorn health and distance traveled each minute.
* Process streaming data
	* Persist aggregate data from the application to a backend database stored in DynamoDB and run queries against those data.
* Store & query data
	* Use Kinesis Data Firehose to flush the raw sensor data to an S3 bucket for archival purposes. Using Athena, you'll run SQL queries against the raw data for ad-hoc analyses.

### Application Architecture
![](https://s3.amazonaws.com/hadoop357/RTArchitecture.PNG)
I will go through the project in the order of the architecture
* Before going through this architecture, I will create a Cloud9 instance and download the client.tar file first
* p.s You can use "Control + C" to stop it.

### Amazon Kinesis Data Streams
![](https://s3.amazonaws.com/hadoop357/KinesisStream.PNG)
* Put record for "./producer"
![](https://s3.amazonaws.com/hadoop357/putrecord.png)
* Use "./consumer" command to start reading sensor data from the stream.
* Use Amazon Cognito and manage Identity Pools. Identity pool ID will be used later.
* Use IAM (Identity and Access Management) and add inline policy that allow read and list.
* Use Identity pool ID in Contigo to do dataprocessing.
* Use command "./producer -name Bucephalus" to generate unicorns on map

### Aggregate Data
* The Amazon Kinesis Data Analytics application processes data from the source Amazon Kinesis stream that we created in the previous module and aggregates it on a per-minute basis. Each minute, the application will emit data including the total distance traveled in the last minute as well as the minimum and maximum readings from health and magic points for each unicorn in our fleet. These data points will be sent to a destination Amazon Kinesis stream for processing by other components in our system.

* Amazon Kinesis Data Streams -> Amazon Kinesis Data Analytics -> Amazon Kinesis Data Streams (aggregated)
* Analytics Application
![](https://s3.amazonaws.com/hadoop357/KinesisAnalyticsApp.png)
* Discover in Schema
	Build an Amazon Kinesis Data Analytics application which reads from the wildrydes stream built in the previous module and emits a JSON object with the following attributes each minute:
![](https://s3.amazonaws.com/hadoop357/SchemaDiscover.png)

* Run SQL to see rows of aggregated data.
![](https://s3.amazonaws.com/hadoop357/SQLRTanalytics.png)
#
	CREATE OR REPLACE STREAM "DESTINATION_SQL_STREAM" (
	  "Name"                VARCHAR(16),
	  "StatusTime"          TIMESTAMP,
	  "Distance"            SMALLINT,
	  "MinMagicPoints"      SMALLINT,
	  "MaxMagicPoints"      SMALLINT,
	  "MinHealthPoints"     SMALLINT,
	  "MaxHealthPoints"     SMALLINT
	);

	CREATE OR REPLACE PUMP "STREAM_PUMP" AS
	  INSERT INTO "DESTINATION_SQL_STREAM"
	    SELECT STREAM "Name", "ROWTIME", SUM("Distance"), MIN("MagicPoints"),
			  MAX("MagicPoints"), MIN("HealthPoints"), MAX("HealthPoints")
	    FROM "SOURCE_SQL_STREAM_001"
	    GROUP BY FLOOR("SOURCE_SQL_STREAM_001"."ROWTIME" TO MINUTE), "Name";

Finally Connect to destination

* Read messages from the stream
	* commands: "./consumer -stream wild-summary 
	![](https://s3.amazonaws.com/hadoop357/readWildStream.png)

* Experiment
	* Exit previous unicorn
	* Run "./producer -name Bucephalus
	* Unicorn change 
	![](https://s3.amazonaws.com/hadoop357/UnicornChange.png)

### Process streaming data
* Amazon Kinesis Data Stream -> AWS Lambda -> Amazon S3 -> Amazon DynamoDB
* In previous section, the work data collecting have been finished.
* Amazon DynamoDB is a fully managed non-relational database service that provides fast and predictable performance with seamless scalability.
* Format:
	* Table name: UnicornSensorData
	* Partition key: Name
	* Sort key: StatusTime
* Create a WildRydesStreamProcessorRole with AWSLambdaKinesisExcutionRole and WildRyedsDynamoDBWritePolicy.
	* p.s Lambda run your code only when triggered, using only the compute resources needed.

* Create a Lambda function to process the stream
	* Create a Lambda function "WildRydesStreamProcessor" which will be triggered whenever a new record is available in the wildrydes stream.
![](https://s3.amazonaws.com/hadoop357/Lamdafunction1.PNG)
(I lost my file here again, I should edit .md file in local editor with auto saving function...)
* Monitor the Lambda function
	* use command "./producer -name Rocinante" again to start emiting sensor data to the stream with a unicorn name.
	* Use Monitoring AWS Lambda to get the data processing information.
![](https://s3.amazonaws.com/hadoop357/MONITOR1.PNG)
* Query the DynamoDB table
	* Explore DynamoDB table to get per-minute data point for each Unicorn.
![](https://s3.amazonaws.com/hadoop357/DynamoQuery.PNG)
	

### Store & qurey Data
* Application Architecture
	* Amazon Kinesis Data Streams -> Amazon Kinesis Data Firehose -> Amazon S3 -> Amazon Athena
![](https://s3.amazonaws.com/hadoop357/DeliveryStream.png)
* I use the previous mldata S3 bucket to store the collecting and analyzed data
* Create an Amazon Kinesis Data Firehose delivery stream
![](https://s3.amazonaws.com/hadoop357/ProcessRecord.png)
* Create an Amazon Athena table to query the raw data in place on Amazon S3 using a JSON SerDe.
	* Amazon Athena is an interactive query service that makes it easy to analyze data in Amazon S3 using standard SQL. 
	* Use the path of mldata to create a Athena table
	* Create table SQL statement:
#
	CREATE EXTERNAL TABLE IF NOT EXISTS wildrydes (
	       Name string,
	       StatusTime timestamp,
	       Latitude float,
	       Longitude float,
	       Distance float,
	       HealthPoints int,
	       MagicPoints int
	     )
	     ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'
	     LOCATION 's3://YOUR_BUCKET_NAME_HERE/';
![](https://s3.amazonaws.com/hadoop357/AthenaQuery.PNG)
* The result in S3 bucket
![](https://s3.amazonaws.com/hadoop357/Logfileforwild.PNG)
### Explore and Query the batched data files
* Verify that Firehose is delivering batched data files to the bucket. Download one of the files and open it in a text editor to see the contents.
* Use "SELECT * FROM wildrydes" to get query data file

<a name="unicorn"></a> 
# Conclusion for the Unicorn project (30mins)
[Back to Menu](#menu)
(Notice: This is just a conclusion for the unicorn project. For more details or screeshots please jump to [episode 9](#app))
### Tool list
* Amazon S3
	* Used to store data
* AWS Lambda
	* Lambda run your code only when triggered, using only the compute resources needed.
* Amazon DynamoDB
	* Amazon DynamoDB is a fully managed non-relational database service that provides fast and predictable performance with seamless scalability.
	* In this project, I query the DynamoDB table to get per-minute data point for each Unicorn.
* AWS IAM
	* Use IAM (Identity and Access Management) and add inline policy such as WildRydesDynamoDBWritePolicy.
* Amazon Kinesis Data Analytics
	* The Amazon Kinesis Data Analytics application processes data from the source Amazon Kinesis stream that we created in the previous module and aggregates it on a per-minute basis.
	* Finally it will be aggregated again to the Amazon Kinesis Data Stream
* Amazon Kinesis Data Stream
	* All our works are dealing with the data stream and storing it.
	* Configure producers to put data records into a data stream. Configure consumers to continuously process data stream records.
	* It provides the function of Data Firehose and Data Analytics and we use Data Firehose to flush the raw sensor data to an S3 bucket for archival purposes. 
* Amazon Athena
	* Amazon Athena is an interactive query service that makes it easy to analyze data in Amazon S3 using standard SQL.
### Procedure
* Aggregate Data
	* Amazon Kinesis Data Streams -> Amazon Kinesis Data Analytics -> Amazon Kinesis Data Streams (aggregated)
	* Firstly I run ./producer or ./producer -name Bucephalus to generate Unicorns on map and use ./consumer to reading the sensor data. The Unicorns data is what we need to analye and aggregate.
	* Secondly I used Schema to build an Amazon Kinesis Data Analytics application which reads from the wildrydes stream built in the previous module and emits a JSON object with the following attributes each minute.
	* These analyzed data now need Lambda to collect at run time
* Process streaming data
	* Amazon Kinesis Data Stream -> AWS Lambda -> Amazon S3 -> Amazon DynamoDB
	* Firstly I used a Lambda function "WildRydesStreamProcessor" which will be triggered whenever a new record is available in the wildrydes stream to collect data from wildryes.
	* Then use DynamoDB table to get per-minute data point for each Unicorn and store.
	
* Store & query data
	* Amazon Kinesis Data Streams -> Amazon Kinesis Data Firehose -> Amazon S3 -> Amazon Athena 
	* There is another branch for data analyzing and store which using Firehose and SQL(Athena).
	* Use Kinesis Data Firehose to flush the raw sensor data to an S3 bucket for archival purposes. Using Athena, run SQL queries against the raw data for ad-hoc analyses.


# Docker and Containers

<a name="docker"></a>
# Why Docker?
[Back to Menu](#menu)
### What is docker?
* Docker is a computer program that performs operating-system-level virtualization and is used to run containers.
  * What is container?
    * A container is a standard unit of software that packages up code and all its dependencies so the application runs quickly and reliably from one computing environment to another.

### Docker is focused on the migration experience (20 mins)
* Docker is all about speed
![](https://s3.amazonaws.com/hadoop357/docker.PNG)
* Containers Reduce Complexity. Package all of the '?'.
* 80% MAINTENANCE, 20% INNOVATION, we use docker to reduce the maintenance and complexity drains budgets.
* e.g1 Paypal
   * 18 Month project (long)
   * Migrated 700+ apps (multi platform)
   * Now 150,000 containers (need management)
   * 50% dev productivity boost (benefit from docker)
* e.g2 MetLifte
   * 70% reduction in VM costs
   * 67% fewer CPU's (contrainer is light)
   * 10x average CPU utilization (management)
   * 66% cost reduction
 
<a name="guide"></a>
# DevOps Docker Beginners Guide (1h)
[Back to Menu](#menu)

### Concepts
	* Docker engine
	* Containers & images
	* Image registries and Docker Store (AKA Docker Hub)
	* Container isolation
	
### docker container run hello-world
![](https://s3.amazonaws.com/hadoop357/hello_world.PNG)

### Docker Image
* Some Commands:
	* docker image pull alpine
		* The pull command fetches the alpine image from the Docker registry and saves it in our system. In this case the registry is Docker Store.
	* docker image ls
	* docker container run alpine ls -l
![](https://s3.amazonaws.com/hadoop357/Docker_detail.PNG)
	* docker container run -it alpine /bin/sh
		* Running a Linux shell inside the container.
	* docker container ls -a
		* list all containers that you ran.
![](https://s3.amazonaws.com/hadoop357/DockerInstance.PNG)

### Container Isolation

* Even though each docker container run command used the same alpine image, each execution was a separate, isolated container. Each container has a separate filesystem and runs in a different namespace; by default a container has no way of interacting with other containers, even those from the same image.

* In every day work, Docker users take advantage of this feature not only for security, but to test the effects of making application changes. Isolation allows users to quickly create separate, isolated test copies of an application or service and have them run side-by-side without interfering with one another. In fact, there is a whole lifecycle where users take their changes and move them up to production using this basic concept and the built-in capabilities of Docker Enteprise.
![](https://s3.amazonaws.com/hadoop357/DockerIsolation.PNG)
![](https://s3.amazonaws.com/hadoop357/DockerExec.PNG)

### Terminology
* Images - The file system and configuration of our application which are used to create containers. To find out more about a Docker image, run docker image inspect alpine. In the demo above, you used the docker image pull command to download the alpine image. When you executed the command docker container run hello-world, it also did a docker image pull behind the scenes to download the hello-world image.
* Containers - Running instances of Docker images — containers run the actual applications. A container includes an application and all of its dependencies. It shares the kernel with other containers, and runs as an isolated process in user space on the host OS. You created a container using docker run which you did using the alpine image that you downloaded. A list of running containers can be seen using the docker container ls command.
* Docker daemon - The background service running on the host that manages building, running and distributing Docker containers.
* Docker client - The command line tool that allows the user to interact with the Docker daemon.
* Docker Store - Store is, among other things, a registry of Docker images. You can think of the registry as a directory of all available Docker images. You’ll be using this later in this tutorial.
