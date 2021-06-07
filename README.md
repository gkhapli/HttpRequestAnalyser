# HttpRequestAnalyser
 Application to parse given file and generate metrics based on data.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally 

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.ing.httpreqanalyser.HttpReqAnalyserApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Building the application and running the application

For building the application you can run the below maven command.

```shell
mvn clean install
```

Once the above command is executed the jar file would be generated in the target folder.

You can run the application by running the below command.

```shell

argument={File or Folder location where the log files are kept}
java -jar httpRequestAnalyser-0.0.1-SNAPSHOT.jar $argument

eg.
argument="\app\logfiles\data.log" -- a specific log file to be analysed
OR
argument="\app\logfiles" -- Directory where log files are kept
java -jar httpRequestAnalyser-0.0.1-SNAPSHOT.jar $argument

User can also add the file path property in the application.properties file
file.path=C:\\Users\\logfiles\\data.log -- a specific log file to be analysed
OR
file.path=C:\\Users\\logfiles -- Directory where log files are kept

java -jar httpRequestAnalyser-0.0.1-SNAPSHOT.jar

```
The application user should have access to read/write files from/into the logfiles folder.

## Project Description

This application will parse a given file and calculate some metrics like total requests, total failed requests, average response time, 95th and 99th percentiles per each endpoint and generate the output in a CSV file.
The input file contains logs of HTTP requests and consist of name, timestamp when it was send, timestamp when it was received and the status of the request. 

Log File format

Tab ("\t") is used as delimiter. Description of the REQUEST log line:
```
<TYPE> <ID> <NAME> <TIMESTAMP-START> <TIMESTAMP-END> <STATUS> <MESSAGE>
```
**TYPE** other values can be present but only “REQUEST” is of interest and should ignore the rest.<br/>
**ID** typically a number<br/>
**NAME** string denoting some endpoint name<br/>
**TIMESTAMP-START** is UNIX timestamp<br/>
**TIMESTAMP-END** is UNIX timestamp<br/>
**STATUS** can be “OK” or “KO”<br/>
**MESSAGE** is only present when the status is “KO”<br/>

The Output CSV file will be placed in the same folder where the input file/files were kept.
If a single file is specified as a input argument, the output CSV file format would be {FileName}_output_yyyyMMddHHmmss.csv.
If a folder is specified as a output argument, the output CSV file format would be output_yyyyMMddHHmmss.csv.

The output report contains the below metrics calculated for each endpoint.

**Total requests** - This field contains all the total requests that were received for a specific endpoint.
				 Only the records with TYPE as REQUEST will be taken into consideration.

**Total failed requests** - This field contains the total requests that failed for a specific endpoint. The failed requests are with status KO.

**Average Response Time (ms)** - This field contains the average response time for a specific endpoint. 
For Average response time, we first calculate the execution time and used it for calculation of average for each endpoint.
Only the requests with status OK are considered for this calculation.

**95th Percentile (ms)** - The 95th Percentile value is calculated for each specific endpoint. The Execution time values are considered for the calculation.
We are using the nearest-rank method for calculation of the Percentile value.

**99th Percentile (ms)** - The 99th Percentile value is calculated for each specific endpoint. The Execution time values are considered for the calculation.
We are using the nearest-rank method for calculation of the Percentile value.

Note:
**Execution Time (ms)** :- This time is the difference between the start and the end time of each request.
Below link is referred for the Calculation of Percentile Values.
- [Percentile] (https://en.wikipedia.org/wiki/Percentile)
