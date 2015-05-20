/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pts4.chatserver;

import chat.AudioMessage;
import chat.Message;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;



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
    private ArrayList<Message> messages;
    private ObservableList<Message> observableMessages;
    
    public Client(Socket incoming, Server server) throws IOException, ClassNotFoundException
    {
        OutputStream outStream = incoming.getOutputStream();
        InputStream inStream = incoming.getInputStream();
        
        in = new ObjectInputStream(inStream);
        out = new ObjectOutputStream(outStream);
        this.naam = (String) in.readObject();
        File dir = new File("Opnames\\" + naam);
        if(!dir.exists())
        {
            dir.mkdir();
        }
        System.out.println(naam);
        this.server = server;
        messages = new ArrayList<Message>();
        observableMessages = observableList(messages);
    }
    
    public void sendMessage(Message message)
    {
        try 
        {
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
    
    public void addMessageToObservable(Message message)
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
    
    public String getNaam()
    {
        return this.naam;
    }
    
    public ObservableList<Message> getMessages()
    {
        return this.observableMessages;
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
                    
                    if(message instanceof AudioMessage)
                    {
                        AudioMessage audiomessage = (AudioMessage) message;
                        Thread t = new Thread(new WriteFileThread(audiomessage, this));
                        t.start();
                    }
                    else
                    {
                        addMessageToObservable(message);
                    }
                }
                else
                {
                    server.sendMessage(message);
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