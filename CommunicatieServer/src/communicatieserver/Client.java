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
public class Client
{
    private Socket incoming;
    private ObjectInputStream  in;
    private ObjectOutputStream out;
    private String naam;
    public Client(Socket incoming) throws IOException, ClassNotFoundException
    {
        this.incoming = incoming;
        OutputStream outStream = incoming.getOutputStream();
        InputStream inStream = incoming.getInputStream();

        in = new ObjectInputStream(inStream);
        out = new ObjectOutputStream(outStream);
        this.naam = (String) in.readObject();
        System.out.println(naam);
    }
    
    public void sendMessage(String message)
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
}
