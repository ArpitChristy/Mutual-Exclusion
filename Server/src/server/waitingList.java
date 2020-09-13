/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.Queue;

/**
 *
 * @author arpit
 */
public class waitingList {
    synchronized public int waitingList(Queue waitingList) throws InterruptedException, IOException {
        
        System.out.println("inside synchronized method..");
        int number = (int) waitingList.remove();
        Thread.sleep(number*1000);
        return number;
        
//        Queue<Integer> waitingList = new LinkedList<>();
//        int number = Integer.valueOf(num);
//        //Adding new elements to the waiting queue.. 
//        waitingList.add(number);
    }
}
