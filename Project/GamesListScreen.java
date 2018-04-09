/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToe;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Kevin
 */
public class GamesListScreen extends JFrame {
    
    // need to enter run/main method
    // connect to the database
    // get autoID from games where p1 or p2 is null
    // for all autoID's found:
    //      create a jLabel with game ID
    //      create a jButton to join the game
    // create a button to create new game
    
    DatabaseComms dbComms = new DatabaseComms();
    private ArrayList<Integer> openGames = new ArrayList<>();
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    
    protected void run() {
        
        //create a new jframe to hold everything
        JPanel mypanel = new JPanel();
        //get autoID of available games
        openGames = dbComms.getOpenGames();
        
        for(int i=0;i<openGames.size();i++) {
            buttons.add( new JButton("Button :: " + i ) );
        }
        
        for(int i=0;i<openGames.size();i++) {
            mypanel.add(buttons.get(i));
        }
    }
    
}
