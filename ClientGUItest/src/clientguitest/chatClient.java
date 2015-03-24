/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientguitest;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxapplication5.ClienttestGUIController;

/**
 *
 * @author pieter
 */
public class chatClient 
{
    private ObjectOutputStream out;
    private ClienttestGUIController controller;
    private String naam;
    private Thread t;
    
    public chatClient(ClienttestGUIController controller)
    {
        this.controller = controller;
        OutputStream outStream = null;
        try {
            Socket s = new Socket("145.93.244.65", 8189);
            outStream = s.getOutputStream();
            InputStream inStream = s.getInputStream();
            // Let op: volgorde is van belang!
            out = new ObjectOutputStream(outStream);
            ObjectInputStream in = new ObjectInputStream(inStream);
            this.naam = "Leo";
            out.writeObject(naam);
            clientThread ct = new clientThread(in,this);
            t = new Thread(ct);
            t.start();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(chatClient.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void stopThread()
    {
        t.interrupt();
        System.out.println("De server is down");
        
        try 
        {
            out.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(chatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void sendMessage(String message)
    {
        try 
        {
            out.writeObject("[" + naam + "]: " + message);
            out.flush();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(chatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addMessagetoScreen(String message)
    {
        controller.addItemListView(message);
    }
}
