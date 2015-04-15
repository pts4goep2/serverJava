/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import clientguitest.chatClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author pieter
 */
public class ClienttestGUIController implements Initializable 
{
    @FXML private TextArea input;
    @FXML private ListView output;
    @FXML private ComboBox cbBeschikbareUnits;
    @FXML private Label lbGebruikersnaam;
    private String naam;
    
    private chatClient cc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }
    
    @FXML
    private void selectUnit(ActionEvent event)
    {
        this.naam = (String) cbBeschikbareUnits.getSelectionModel().getSelectedItem();
        output.getItems().add("[Algemeen]: je stuurt nu berichten naar: " + this.naam);
    }
    
    @FXML
    private void btnSend_Click(ActionEvent event) 
    {
       String message = input.getText();
       input.clear();
       output.getItems().add(message);
       cc.sendMessage(message, naam);
    }
    
    public void addItemListView(String item)
    {
        Platform.runLater(new Runnable() {

            @Override
            public void run() 
            {
                output.getItems().add(item);            
            }
        });
    }
    
    public void setUser(String user)
    {
        cc = new chatClient(this, user);
        lbGebruikersnaam.setText(user);
        cbBeschikbareUnits.setItems(cc.getObservableClients());
        this.naam = "Meldkamer";
        output.getItems().add("[Algemeen]: je coummuniceerd nu met de meldkamer");
    }
}
