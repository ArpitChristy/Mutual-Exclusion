/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author arpit
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    long createdMillis = System.currentTimeMillis();

    public static void main(String[] args) {
        // TODO code application logic here 
        new Client();
    }
    public Client() {
        try {
            s = new Socket("127.0.0.1", 3333);

            // note down the time at which the client is connected..
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
//            sendHttppRequest();
            writeToOutput();
            listenForInput();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    

    public void listenForInput() {
        while (true) {

//            try {
////                sendHttppRequest();
////                writeToOutput();
//            } catch (IOException ex) {
//                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            listenForInput();
//            Scanner console = new Scanner(System.in);
//            while (!console.hasNextLine()) {
//                try {
//                    System.out.println("keep sleeping");
//                    Thread.sleep(1);
//                } catch (Exception e) {
//                    System.out.println("inside catch");
//                    e.printStackTrace();
//                }
//            }

//            String input =  console.nextLine();
//            System.out.println("hello");
//            if (input.toLowerCase().equals("quit")) {
//                System.out.println("client inside the quit condition");
//                break;
//            }
            try {
                String textIn = din.readUTF();
                System.out.println(textIn);
//                long threadId = Thread.currentThread().getId();
//                System.out.println("the thread id is " + threadId);
                String arr_opcode[] = textIn.split(" ", 2);

                // check if the message is about waiting from the server .. 
                if (arr_opcode[0].matches("Server")) {

                    after_waiting(createdMillis);
                }

                while (din.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        System.out.println("inside second catch condition");
                        System.out.println(e);
                    }
                }

//                String reply = din.readUTF();
//                System.out.println(reply);
            } catch (Exception e) {
                System.out.println("third inside condition");
                System.out.println(e);
                closeConnections();
                break;
            }
        }

    }
    
    
    public void writeToOutput() throws IOException {
        System.out.println("call to writeoutput");
        int randomNum = ThreadLocalRandom.current().nextInt(3, 10 + 1);
        dout.writeUTF("opcode1 " + String.valueOf(randomNum));
        dout.flush();
    }

    public void closeConnections() {
        try {
            din.close();
            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendHttppRequest() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:3333");
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("name", "don number1"));
//        nvps.add(new BasicNameValuePair("integer", "23"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//        System.out.println(httpPost + "hhello");
        try{
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
        }
        catch(IOException e){
            System.out.println("the error is  : "+e);
        }
    }

    private void after_waiting(long createdMillis) throws IOException {
//        System.out.println("inside after waiting function");
        long nowMillis = System.currentTimeMillis();
        int waiting_time = (int) ((nowMillis - createdMillis) / 1000);
        try {
            dout.writeUTF("Client waited for " + waiting_time + " seconds.");
            dout.flush();
            this.createdMillis = System.currentTimeMillis();
        } catch (IOException e) {
            System.out.println("the error : " + e);
        }
//        System.out.println("I waited for "+waiting_time+" seconds.");
        dout.flush();
        writeToOutput();
        
    }

}
