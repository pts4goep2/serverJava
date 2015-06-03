/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientApp;

import Audio.AudioHandler;
import CommunicationClient.ComManager;
import CommunicationClient.MessageListener;
import Protocol.Message;
import Protocol.MessageBuilder;
import java.io.StringReader;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 *
 * @author Leo
 */
public class Administratie implements MessageListener
{
    private static Administratie admin = null;
    private AudioHandler handler;
    private ChatClient cc;
    private ComManager commanager;
    private MessageBuilder mBuilder;
    private boolean found;
    private int personid;
    private int persontypeid;
    private boolean configurator;
    
    private Administratie()
    {
        //database = new SQL();
        handler = new AudioHandler();
        commanager = ComManager.getInstance();
        commanager.addListener(this);
        mBuilder = new MessageBuilder();
    }

    public void setOntvanger(String naam) 
    {
        this.cc.setOntvanger(naam);
    }  
    
    public ChatClient getCc() 
    {
        return cc;
    } 
    
    public boolean loginEmergencyService(String username, String password)
    {
        commanager.addMessage(mBuilder.buildLoginMessage(username, password));
        MessageBuilder mb = new MessageBuilder();
        Message message = mb.buildLoginMessage(username, password);
        commanager.sendMessage(message);
        while(!found)
        {
            
        }
        if(personid != 0)
        {
            return true;
        }
        found = false;
        return false;
    }
    
    public static Administratie getInstance()
    {
        if(admin == null)
        {
            admin = new Administratie();
        }
        return admin;
    }
    
    public void startRecordingAudio()
    {
        handler.startRecording();
    }
    
    public void stopRecordingAudio()
    {
        handler.stopRecording();
    }
    
    public void setChatClient(String user)
    {
        cc = new ChatClient(user, 1);        
        cc.setOntvanger(user);
    }
    
    public void sendMessage(String bericht)
    {
        cc.sendMessage(bericht);
    }
    
    public void sendAudioMessage()
    {
        cc.sendAudioMessage(handler.getAudiofile(), handler.getPath());
    }
    
    private void readLoginResponse(Message message)
    {
        personid = 0;
        persontypeid = 0;
        configurator = false;

        StringReader reader = new StringReader(message.getText());
        JsonParser parser = Json.createParser(reader);
        Event event = parser.next();

        while (parser.hasNext()) {
            if (event.equals(Event.KEY_NAME)) {
                String keyname = parser.getString();
                event = parser.next();

                switch (keyname) {
                    case "personid":
                        personid = parser.getInt();
                        break;
                    case "persontypeid":
                        persontypeid = parser.getInt();
                        break;
                    case "personconfigurator":
                        if (parser.getString() == "YES") {
                            configurator = true;
                        } else {
                            configurator = false;
                        }
                        break;
                    default:
                        break;
                }
                event = parser.next();
            } else {
                event = parser.next();
            }
        }
        found = true;
        //hier de code met bovenstaande variabelen

    }

    @Override
    public void proces(Message message) 
    {
        switch (message.getType()) {
            case MessageBuilder.LoginReply:
                System.out.println(message.getText());
                readLoginResponse(message);
                break;

            default:
                break;
        }
    }
}
