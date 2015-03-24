/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientguitest;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Leo
 */
public class clientThread implements Runnable 
{
    private ObjectInputStream stream;
    private chatClient cc;
    
    public clientThread(ObjectInputStream stream, chatClient cc)
    {
        this.stream = stream;
        this.cc =cc;
    }

    @Override
    public void run() 
    {
        while(!Thread.currentThread().isInterrupted())
        {
            try 
            {
                String message = (String) stream.readObject();
                cc.addMessagetoScreen(message);
            } 
            catch (IOException | ClassNotFoundException ex) 
            {
                Thread.currentThread().interrupt();
                System.out.println("Mattie de server is weg");
            }
        }
    }    
}