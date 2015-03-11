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
public class Clientontvangen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        try 
        {
            Socket s = new Socket("localhost", 8189);
            OutputStream outStream = s.getOutputStream();
            InputStream inStream = s.getInputStream();

            // Let op: volgorde is van belang!
            ObjectOutputStream out = new ObjectOutputStream(outStream);
            ObjectInputStream in = new ObjectInputStream(inStream);
            String message = (String) in.readObject();
            System.out.println(message);
        } 
        catch (IOException | ClassNotFoundException ex) 
        {
            Logger.getLogger(Clientontvangen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
