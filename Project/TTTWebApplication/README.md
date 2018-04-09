Setting Up the Tic Tac Toe Web Service

1. Run xampp control panel and start a MySQL server.

2. Navigate to C:\xampp\mysql\bin and paste tttexample.sql in here and run command: mysql -u root -p

3. Create a new database called tttexample: CREATE DATABASE TTTEXAMPLE;

4. Exit the MySQL command line. Load in the MySQL dump: mysql -u root -p tttexample < tttexample.sql

5. Open NetBeans, import the zip file by: File > Import Project > From Zip..

6. Follow the steps, Clean and Build the project and Deploy. Web Server will start on localhost port 8080



