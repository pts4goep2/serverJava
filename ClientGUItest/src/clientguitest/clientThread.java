/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientguitest;

import chat.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author Leo
 */
public class ClientThread implements Runnable 
{
    private ObjectInputStream stream;
    private ChatClient cc;
    
    public ClientThread(ObjectInputStream stream, ChatClient cc)
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
                Object object = stream.readObject();
                if(object instanceof ArrayList)
                {
                    for(String s : (ArrayList<String>) object)
                    {
                        cc.addClient(s);
                    }
                }
                if(object instanceof String)
                {
                    // voeg toe aan lijst met beschikbare clients
                    System.out.println((String) object);
                    cc.addClient((String) object);
                }
                if(object instanceof Message)
                {
                    Message message = (Message) object;
                    cc.addMessage(message);
                }                
            } 
            catch (IOException | ClassNotFoundException ex) 
            {
                Thread.currentThread().interrupt();
                System.out.println("Mattie de server is weg");
            }
        }
    }    
}