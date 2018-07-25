Welcome to Sqoop2

Apache Sqoop is a tool designed for efficiently transferring bulk data between 
Apache Hadoop and structured datastores such as relational databases. You can use
Sqoop to import data from external structured datastores into Hadoop Distributed
File System or related systems like Hive and HBase. Conversely, Sqoop can be used
to extract data from Hadoop and export it to external structured datastores such
as relational databases and enterprise data warehouses.

== Documentation

Sqoop ships with documentation, please check module "docs" for additional materials.
More documentation is available online on Sqoop home page: http://sqoop.apache.org/
 
== Sqoop2 on MAPR Cluster

1. MapR-Sandbox-For-Hadoop-6.0.0 is not provided with MySQL-JDBC/MySQL-ODBC drivers. 
So, to import data form a remote MySQL, you would have to install MySQL-ODBC-DRIVER for 
connection through CLI or MySQL-JDBC-DRIVER for connection through external API. 
//TODO-ODBC-driver-name 
//TODO-JDBC-driver-name -> jar
//SQL-clinet-MariaDB
