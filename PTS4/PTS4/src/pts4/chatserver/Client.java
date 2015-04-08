/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pts4.chatserver;

import chat.Message;
import pts4.gui.ServerGUIController;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Leo
 */
public class Client implements Runnable
{
    private ObjectInputStream  in;
    private ObjectOutputStream out;
    private String naam;
    private Server server;
    private ServerGUIController controller;
    public Client(Socket incoming, Server server) throws IOException, ClassNotFoundException
    {
        OutputStream outStream = incoming.getOutputStream();
        InputStream inStream = incoming.getInputStream();

        in = new ObjectInputStream(inStream);
        out = new ObjectOutputStream(outStream);
        this.naam = (String) in.readObject();
        System.out.println(naam);
        this.server = server;
    }
    
    public void sendMessage(String bericht)
    {
        try 
        {
            Message message = new Message(bericht, this.naam, "Meldkamer");
            out.writeObject(message);
            out.flush();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void sendClients(ArrayList<String> clientnames)
    {
        try 
        {
            out.writeObject(clientnames);
            out.flush();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendClientName(String name)
    {
        try 
        {
            out.writeObject(name);
            out.flush();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public String getNaam()
    {
        return this.naam;
    }
    
    public void setServerController(ServerGUIController controller)
    {
        this.controller = controller;
        System.out.println("controller gezet");
    }

    @Override
    public void run() 
    {
        while(!Thread.currentThread().isInterrupted())
        {
            try 
            {
                Message message = (Message) in.readObject();
                System.out.println("bericht ontvangen");
                if(message.getOntvanger().equals("Meldkamer"))
                {
                    if(controller != null)
                    {
                        controller.AddItemListview(message.toString());
                    }
                }
                else
                {
                    server.sendMessage(message.getBericht(), message.getOntvanger());
                }
            } 
            catch (IOException | ClassNotFoundException ex) 
            {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                Thread.currentThread().interrupt();
                System.out.println( naam + " is gone");
                server.removeClient(this.naam);
            }
        }
    }
}
