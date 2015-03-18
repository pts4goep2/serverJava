/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communicatieserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Leo
 */
public class CommunicatieServer {

    /**
     * @param args the command line arguments
     */
    private Map<String, Client> clients;
    private serverThread st;
    
    public static void main(String[] args) 
    {
       CommunicatieServer server = new CommunicatieServer();
       Scanner input = new Scanner(System.in);
       System.out.println("Stuur een bericht: ");
       while(true)
       {
           String bericht = input.nextLine();
           server.sendMessage(bericht, "Hallo");
       }
    }
    
    public CommunicatieServer() 
    {
        st = new serverThread();
        Thread t = new Thread(st);
        t.start();
    }
    
    public void sendMessage(String Message, String naam)
    {
        System.out.println("ik stuur een bericht");
        clients = (Map<String, Client>) st.getClients();
        clients.get(naam).sendMessage(Message);
    }
}
