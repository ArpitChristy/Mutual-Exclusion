/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arpit
 */
class ServerConnection extends Thread {

    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    long threadId;
    boolean shouldRun = true;
    Server server;
    long createdMillis = System.currentTimeMillis();

    public ServerConnection(Socket socket, Server server) {
        super("ServerConnectionThread");
        this.socket = socket;
        this.server = server;
        this.threadId = threadId;

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {

//                long threadId = Thread.currentThread().getId();
            System.out.println(threadId);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            listenForInput();
//                server.notifyAll();
//            sendStringToClient();

        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendStringToClient(String text) {
        try {
//            System.out.println("inside the send function.");
            dout.writeUTF(text);
            dout.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    public void sendStringToAllClients(String text) {
//        for (int index = 0; index < server.connections.size(); index++) {
//            ServerConnection sc = server.connections.get(index);
//            sc.sendStringToClient(text);
//        }
//    }
    public void listenForInput() throws IOException, InterruptedException {
        System.out.println("listeninig for input");
        while (shouldRun) {
            while (din.available() == 0) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
//            String s;
//            while((s = din.readLine()) != null){
//                System.out.println(s);
//            }
//                System.out.println("hello here at raedUTF code");
            String textIn = din.readUTF();
//                sendStringToAllClients(textIn);
//            System.out.println(textIn);
            //get the operation code and based on the value decide which methods to call..
            String arr_opcode[] = textIn.split(" ", 2);
//            String received_opcode = arr_opcode[0].replaceAll("\\s+","");            
            switch (arr_opcode[0]) {
                case "opcode1":
                    received_Int(arr_opcode[1]);
                    break;
                default:
                    System.out.println(textIn);
                    break;
            }

//            break;
        }

    }

    public void writeToOutput() throws IOException {
        Scanner console = new Scanner(System.in);
        String input = console.nextLine();
        dout.writeUTF(input);
        dout.flush();
    }

    public void closeConnections() throws IOException {
        din.close();
        dout.close();
        socket.close();
    }

    public void received_Int(String num) throws InterruptedException, IOException {
//        System.out.println("inside received int method");
        //            int random_int = Integer.valueOf(textIn);
        System.out.println("Integer received from client:" + num);

        //using queue to add number 
        Queue<Integer> waitingList = new LinkedList<>();
        int number = Integer.valueOf(num);
        //Adding new elements to the waiting queue.. 
        waitingList.add(number);

        //calling the queue handling method..
        waitingList(waitingList);
//        int number = Integer.valueOf(num);

//        waitingList(num);
    }

    synchronized private void waitingList(Queue waitingList) throws InterruptedException, IOException {
//        System.out.println("inside synchronized method");
        int number = (int) waitingList.remove();
        Thread.sleep(number * 1000);
        System.out.println("current thread is " + threadId);
        System.out.println("Number of active threads from the given thread: " + Thread.activeCount());

        dout.writeUTF("Server waited for " + number + " seconds.");
        dout.flush();

//        Queue<Integer> waitingList = new LinkedList<>();
//        int number = Integer.valueOf(num);
//        //Adding new elements to the waiting queue.. 
//        waitingList.add(number);
    }

    public void after_wait() {
        long nowMillis = System.currentTimeMillis();
        int waiting_time = (int) ((nowMillis - this.createdMillis) / 1000);
    }

}
