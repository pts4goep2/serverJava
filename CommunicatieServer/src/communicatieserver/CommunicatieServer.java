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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Leo
 */
public class CommunicatieServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
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
            Thread ct = new Thread(new ClientThread(incoming));
            ct.start();
        }
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }
      System.out.println("server is klaar");
    }
}
