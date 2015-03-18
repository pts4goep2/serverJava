/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communicatieserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class clientThread implements Runnable 
{
    private ObjectInputStream stream;
    
    public clientThread(ObjectInputStream stream)
    {
        this.stream = stream;
    }

    @Override
    public void run() 
    {
        while(true)
        {
            try 
            {
                String message = (String) stream.readObject();
                System.out.println(message);
            } 
            catch (IOException | ClassNotFoundException ex) 
            {
                Logger.getLogger(clientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}