# Database_Systems_Assignment2
## Instructions for setting up and running application:
# Overview:
### This repository contains the source code for the Database Systems Assignment 2. The application is for creating connection in Java with  a PostgreSQL database, performing various operations related to the assignment requirements.

1. Clone the Repository: Download the source code.
2. Open the Project in Your IDE:
Open your preferred IDE (IntelliJ IDEA, VisualStudio Code, Eclipse, etc.).
Import the project.
3. Add PostgreSQL JDBC Driver:
Locate the postgresql-42.6.0.jar file 
Add this JAR file to your project as a library or dependency.
4. Create PostgreSQL Database:
Set up a PostgreSQL database either inside a Docker container or using local pgAdmin.
5. Create tables using SQL_Script.sql
6. Open Java files: For running application you should use Main.java
7. Creating connection with database: You should use your own url,username, and password for creating connection. Example: url="jdbc:postgresql://localhost:5432/Database_Systems_Assignment02"; username="postgresql"; password="root1234".
8. After that you are ready for running Main.java.
9. After running Main.java, you should enter input, this input should be one of the these table names:"authors","books","customers", "orders","orderdetails".
10. Required operations will be generated after your input in specific for each table
11. That is all for running and setting up application.
