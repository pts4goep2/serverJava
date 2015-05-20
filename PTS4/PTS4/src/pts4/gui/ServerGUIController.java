/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts4.gui;

import chat.Message;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import pts4.chatserver.Server;


/**
 * FXML Controller class
 *
 * @author pieter
 */
public class ServerGUIController extends AnchorPane implements Initializable {

    /**
     * Initializes the controller class.
     * 
     */
    @FXML TextArea input;
    @FXML ListView OutPut;
    @FXML ComboBox present;
    private Server server;
    private String communicator="";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    @FXML
    public void btnChat_Click()
    {
        String bericht = input.getText();
        input.clear();
        server.sendMessage(new Message(bericht, "Meldkamer", communicator));
        this.AddItemListview(bericht);
    }
    
    public void AddItemListview(String item)
    {
        Platform.runLater(new Runnable() {

            @Override
            public void run() 
            {
                OutPut.getItems().add(item);            
            }
        });
    }
    
    public void setServer(Server s, String communicator)
    {
        this.server = s;
        this.communicator = communicator;
        this.OutPut.setItems(server.getClient(communicator).getMessages());
    }
}
