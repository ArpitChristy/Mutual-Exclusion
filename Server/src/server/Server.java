/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author arpit
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    ServerSocket ss;
//    ArrayList<ServerConnection> connections = new ArrayList<>();
    boolean shouldRun = true;
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        new Server();
    }
    public Server() throws InterruptedException{
        try{
            
            ss = new ServerSocket(3333);
            while(shouldRun){
                Socket s = ss.accept();
                System.out.println("Client has connected.");
                
                ServerConnection sc = new ServerConnection(s,this);
                sc.start();
//                sc.join();
                //sc.sendStringToClient("hello from the server");
//                connections.add(sc);
            } 
        }catch(IOException e){
            System.out.println("sdf"+e);
            e.printStackTrace();
        }
    }
    
}
