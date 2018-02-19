/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package socketserver;

/**
 *
 * @author Kevin Burke
 * Date: 13/02/18
 * This file is adapted from the original SocketServer.java file
 * It also includes elements from the threaded_server.java file
 * Both available on Sulis.ul.ie (Author James Murphy)
 */

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class threadedSocketServer extends JFrame implements ActionListener {
    private JButton button;
    private JLabel label = new JLabel("Text received over socket:");
    private JPanel panel;
    private JTextArea textArea = new JTextArea();
    private ServerSocket server = null;
    private Socket client = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String line;
    private static int maxConnections = 0;
    

    public threadedSocketServer() { //Begin Constructor
        button = new JButton("Click Me");
        button.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);
        getContentPane().add(panel);
        panel.add("North", label);
        panel.add("Center", textArea);
        panel.add("South", button);

    } //End Constructor

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == button) {
            textArea.setText(line);
        }
    }

    public void listenSocket() {
        int i=0;
        try {
            server = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Could not listen on port 4444");
            System.exit(-1);
        }

        try {
            while((i++ < maxConnections) || (maxConnections == 0)){
                client = server.accept();
                doComms conn_c= new doComms(client);
                Thread t = new Thread(conn_c);
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Accept failed: 4444");
            System.exit(-1);
        }

//        try {
//            ******taken these two lines *******
//            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            out = new PrintWriter(client.getOutputStream(), true);
//        } catch (IOException e) {
//            System.out.println("Accept failed: 4444");
//            System.exit(-1);
//        }

//        while(true) {
//            try {
//                line = in.readLine();
//                String parts[] = line.split(",");       //This line splits the input at the comma
//                int numEl = Integer.parseInt(parts[0]); //Parse the first element to an integer
//                //Send data back to client
//                line = colors[numEl] + " " + parts[1];  //Set line to color + word and 
//                out.println(line);
//            } catch (IOException e) {
//                System.out.println("Read failed");
//                System.exit(-1);
//            }
//        }
    }

    protected void finalize() {
        //Clean up
        try {
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
            System.out.println("Could not close.");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        threadedSocketServer frame = new threadedSocketServer();
	frame.setTitle("Server Program");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(l);
        frame.pack();
        frame.setVisible(true);
	frame.listenSocket();
    }
}

class doComms implements Runnable {
    private Socket server;
    private String line,input;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String[] colors = {"Red","Orange","Yellow","Green","Blue","Indigo","Violet"};

    doComms(Socket server) {
      this.server=server;
    }
    
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            out = new PrintWriter(server.getOutputStream(), true);
            
            while(true) {
                try {
                    line = in.readLine();
                    String parts[] = line.split(",");       //This line splits the input at the comma
                    int numEl = Integer.parseInt(parts[0]); //Parse the first element to an integer
                    //Send data back to client
                    line = colors[numEl] + " " + parts[1];  //Set line to color + word and 
                    out.println(line);
                } catch (IOException e) {
                    System.out.println("Read failed");
                    System.exit(-1);
                }
            }   
        } catch(IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }
}