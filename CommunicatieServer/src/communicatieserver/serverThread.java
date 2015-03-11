/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communicatieserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class serverThread implements Runnable
{
    private ArrayList<Client> clients;
    public serverThread()
    {
        clients = new ArrayList<>();
    }
    @Override
    public void run() 
    {
        try
        {
            ServerSocket s = new ServerSocket(8189);
            while(true)
            {
                // establish server socket
                // wait for client connection         
                Socket incoming = s.accept();
                System.out.println("connected");
                clients.add(new Client(incoming));
            }
        }
        catch (IOException e)
        {  
           e.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Client> getClients()
    {
        return this.clients;
    }
}
