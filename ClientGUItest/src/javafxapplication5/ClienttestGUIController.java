/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import clientguitest.Administratie;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML private Label lbRecordTimer;
    @FXML private Button btnRecord;
    
    private Administratie admin;
    private Timer timer;
    private boolean pressed;
    private int teller;
    private boolean audiomessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        admin = Administratie.getInstance();        
    }
    
    @FXML
    private void selectUnit(ActionEvent event)
    {
        String naam = (String) cbBeschikbareUnits.getSelectionModel().getSelectedItem();
        admin.setOntvanger(naam);
        output.getItems().add("[Algemeen]: je stuurt nu berichten naar: " + naam);
    }
    
    @FXML
    private void btnSend_Click(ActionEvent event) 
    {
       if(!audiomessage)
       {
           String message = input.getText();
           input.clear();
           admin.sendMessage(message);
       }
       else
       {
           input.clear();
           admin.sendAudioMessage();
           audiomessage = false;
       }
    }
    
    @FXML
    private void btnRecording_Click(ActionEvent event)
    {
        if(!pressed)
        {
            pressed = true;
            btnRecord.setText("Klik om te stoppen met opnemen");
            admin.startRecordingAudio();
            System.out.println("audio opnemen gestart");
            startTimer();
        }
        else if(pressed)
        {
            pressed = false;
            btnRecord.setText("Neem nieuw audiobericht op");
            admin.stopRecordingAudio();
            System.out.println("audio opnemen gestopt");
            timer.purge();
            timer.cancel();
            teller = 0;
            input.setText("druk op send om het audiobericht te versturen");
            lbRecordTimer.setText(String.valueOf(teller));
            audiomessage = true;
        }
    }
    
    private void startTimer()
    {
        timer = new Timer();
        TimerTask task = new TimerTask() 
        {
            @Override
            public void run() 
            {
                Platform.runLater(new Runnable() 
                {
                    @Override
                    public void run() 
                    {
                        lbRecordTimer.setText(String.valueOf(teller));
                        teller++;
                    }
                });
            }
        };
        timer.schedule(task, 0 , 1000);
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
        admin.setChatClient(user);
        admin.setOntvanger("Meldkamer");
        lbGebruikersnaam.setText(user);
        cbBeschikbareUnits.setItems(admin.getCc().getObservableClients());
        output.getItems().add("[Algemeen]: je coummuniceerd nu met de meldkamer");
        output.setItems(admin.getCc().getObservableMessages());
    }
}
