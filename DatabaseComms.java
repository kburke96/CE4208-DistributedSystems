/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToe;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Kevin
 */
public class DatabaseComms {
    
    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String dbName = "week4";
    private String dbUsername = "root";
    
    protected void connectToDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
                    
            connect = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName + "?" + "user=" + dbUsername + "&password=");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected Boolean verifyLogin(String user, String pass) {
        try {
            if (user!=null && pass!=null) {
                preparedStatement = connect.prepareStatement("select userName,password from week4.users where userName=?");

            // process query results
                preparedStatement.setString(1, user);
                resultSet = preparedStatement.executeQuery();
                String orgUname = "", orPass = "";
                while (resultSet.next()) {
                    orgUname = resultSet.getString("userName");
                    orPass = resultSet.getString("password");
                    if (orPass.equals(pass)) {
                        return true;
                    }
                } //end while
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    protected Boolean addToDB(String name, String surname, String user, String pass, String email) {
        try {
            preparedStatement = connect.prepareStatement("INSERT INTO week4.USERS values (default,?,?,?,?,?,default, default, default)");
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, user);
            preparedStatement.setString(4, pass);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
            
            return true;
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return false;
            
    }
    
    protected ArrayList<Integer> getOpenGames() {
        try {
            preparedStatement = connect.prepareStatement("SELECT autoID FROM week4.GAMES where gameState = '-1'");
            resultSet = preparedStatement.executeQuery();
            
            
            ArrayList<Integer> openGames = new ArrayList<>();

            
            while (resultSet.next()) {
                openGames.add(resultSet.getInt(1));
            }
            
            return openGames;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return (ArrayList<Integer>) Collections.EMPTY_LIST;
    }
    
}
