/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientguitest;

import chat.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafxapplication5.ClienttestGUIController;

/**
 *
 * @author pieter
 */
public class chatClient 
{
    private ObjectOutputStream out;
    private ClienttestGUIController controller;
    private Thread t;
    private String naam;
    private ArrayList<String> clients;
    private ObservableList<String> observableClients;
    
    public chatClient(ClienttestGUIController controller, String user)
    {
        this.controller = controller;
        OutputStream outStream = null;
        try 
        {
            clients = new ArrayList<String>();
            observableClients = observableList(clients);
            Socket s = new Socket("localhost", 8189);
            outStream = s.getOutputStream();
            InputStream inStream = s.getInputStream();
            // Let op: volgorde is van belang!
            out = new ObjectOutputStream(outStream);
            ObjectInputStream in = new ObjectInputStream(inStream);
            this.naam = user;
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
    
    public void sendMessage(String bericht, String ontvanger)
    {
        try 
        {
            Message message = new Message(bericht, this.naam, ontvanger);
            out.writeObject(message);
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
    
    public void addClient(String name)
    {
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                if(clients.contains(name))
                {
                    observableClients.remove(name);
                }
                else
                {
                    observableClients.add(name);
                }                
            }
        });
    }
    
    public ObservableList getObservableClients()
    {
        return this.observableClients;
    }
}