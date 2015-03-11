/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communicatieserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Leo
 */
public class ClientThread implements Runnable 
{
    private Socket incoming;
    public ClientThread(Socket incoming)
    {
        this.incoming = incoming;
    }
    @Override
    public void run() {
        try
        {
            OutputStream outStream = incoming.getOutputStream();
            InputStream inStream = incoming.getInputStream();

            ObjectInputStream in = new ObjectInputStream(inStream);
            ObjectOutputStream out = new ObjectOutputStream(outStream);

            out.writeObject("Hallo");
            out.flush();
        }
        catch (IOException ex) 
        {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }         
        finally
        {
            try 
            {
                incoming.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
