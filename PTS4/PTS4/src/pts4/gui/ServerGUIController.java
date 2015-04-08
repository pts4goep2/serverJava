/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts4.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pts4.chatserver.Client;
import pts4.chatserver.Server;
import pts4.chatserver.Server;
import pts4.gui.*;


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
        server.sendMessage(bericht, communicator);
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
        server.addControllerToClient(this, communicator);
    }
}
