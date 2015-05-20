/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientguitest;

import chat.AudioMessage;
import chat.Message;
import java.io.File;
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
public class ChatClient 
{
    private ObjectOutputStream out;
    private Thread t;
    private String naam;
    private ArrayList<String> clients;
    private ObservableList<String> observableClients;
    private ArrayList<Message> messages;
    private ObservableList<Message> observableMessages;
    private String ontvanger;
    
    public ChatClient(String user)
    {
        OutputStream outStream = null;
        try 
        {
            clients = new ArrayList<String>();
            observableClients = observableList(clients);
            messages = new ArrayList<Message>();
            observableMessages = observableList(messages);
            Socket s = new Socket("localhost", 8189);
            outStream = s.getOutputStream();
            InputStream inStream = s.getInputStream();
            // Let op: volgorde is van belang!
            out = new ObjectOutputStream(outStream);
            ObjectInputStream in = new ObjectInputStream(inStream);
            this.naam = user;
            out.writeObject(naam);
            ClientThread ct = new ClientThread(in,this);
            t = new Thread(ct);
            t.start();
            File file = new File("Opnames");
            if(!file.exists())
            {
                file.mkdir();
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public ObservableList<Message> getObservableMessages() 
    {
        return observableMessages;
    }
    
    public void setOntvanger(String ontvanger) 
    {
        this.ontvanger = ontvanger;
    }
    
    public void sendMessage(String bericht)
    {
        try 
        {
            Message message = new Message(bericht, this.naam, ontvanger);
            out.writeObject(message);
            out.flush();
            addMessage(message);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendAudioMessage(byte[] audiofile, String path)
    {
        try
        {
            AudioMessage audiomessage = new AudioMessage("Audiobericht ontvangen van: " + this.naam, this.naam, ontvanger, audiofile);
            audiomessage.setAudiopath(path);
            out.writeObject(audiomessage);
            out.flush();
            addMessage(audiomessage);
            
        }
        catch(IOException ex)
        {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
    
    public void addMessage(Message message)
    {
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                observableMessages.add(message);
            }
        });
        
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