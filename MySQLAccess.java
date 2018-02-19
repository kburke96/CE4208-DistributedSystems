package de.vogella.mysql.first;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class MySQLAccess {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private PreparedStatement preparedStatement2 = null;
  private ResultSet resultSet = null;

  public void readDataBase() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/feedback?"
              + "user=sqluser&password=sqluserpw");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from FEEDBACK.COMMENTS");
      writeResultSet(resultSet);

      // PreparedStatements can use variables and are more efficient
      preparedStatement = connect
          .prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
      // "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
      // Parameters start with 1
      preparedStatement.setString(1, "Test");
      preparedStatement.setString(2, "TestEmail");
      preparedStatement.setString(3, "TestWebpage");
      preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
      preparedStatement.setString(5, "TestSummary");
      preparedStatement.setString(6, "TestComment");
      preparedStatement.executeUpdate();
      
      //Add new scanner object so user can input info
      Scanner scanner = new Scanner(System.in);
      
      preparedStatement2 = connect.prepareStatement("insert into FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
      
      //Ask user for input and take next item given
      //set the string at index 1 (first ?) in preparedStatement2 to the
      //specified value
      System.out.println("Please enter a username to input to database: ");
      String inputName = scanner.next();
      preparedStatement2.setString(1, inputName);
      
      
      //Set index 2 (second ?) to value
      System.out.println("Please enter an email to input to database: ");
      String inputEmail = scanner.next();
      preparedStatement2.setString(2, inputEmail);
      
      //set index 3 (third ?) to value
      System.out.println("Please enter a webpage to input to database: ");
      String inputWebpage = scanner.next();
      preparedStatement2.setString(3, inputWebpage);
      
      //set index 4 (fourth ?) to some date
      preparedStatement2.setDate(4, new java.sql.Date(2009, 12, 11));
      
      //set index 5 (fifth ?) to value
      System.out.println("Please enter a summary to input to database: ");
      String inputSummary = scanner.next();
      preparedStatement2.setString(5, inputSummary);
      
      //set index 6 (sixth?) to value
      System.out.println("Please enter a comment to input to database: ");
      String inputComment = scanner.next();
      preparedStatement2.setString(6, inputComment);
      
      preparedStatement2.executeUpdate();

      preparedStatement = connect
          .prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
      resultSet = preparedStatement.executeQuery();
      writeResultSet(resultSet);

      // Remove again the insert comment
      preparedStatement = connect
      .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
      preparedStatement.setString(1, "Test");
      preparedStatement.executeUpdate();
      
      resultSet = statement
      .executeQuery("select * from FEEDBACK.COMMENTS");
      writeMetaData(resultSet);
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }

private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      String user = resultSet.getString("myuser");
      String website = resultSet.getString("webpage");
      String summery = resultSet.getString("summery");
      Date date = resultSet.getDate("datum");
      String comment = resultSet.getString("comments");
      System.out.println("User: " + user);
      System.out.println("Website: " + website);
      System.out.println("Summery: " + summery);
      System.out.println("Date: " + date);
      System.out.println("Comment: " + comment);
    }
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

} 
