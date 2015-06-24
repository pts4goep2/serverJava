/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientApp;

import Audio.AudioHandler;
import CommunicationClient.ComManager;
import CommunicationClient.LogManager;
import CommunicationClient.MessageListener;
import Protocol.Message;
import Protocol.MessageBuilder;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 * Singleton klasse die de communicatie regelt tussen de gui en de andere klasses.
 * @author Leo
 */
public class Administratie implements MessageListener
{
    private static Administratie admin = null;
    private AudioHandler handler;
    private ChatClient cc;
    private ComManager commanager;
    private MessageBuilder mBuilder;
    private int personid;
    private int persontypeid;
    private LogManager logmanager;
    private boolean configurator;
    
    private Administratie()
    {
        //database = new SQL();
        logmanager = LogManager.getInstance();
        handler = new AudioHandler();
        commanager = ComManager.getInstance();
        commanager.addListener(this);
        mBuilder = new MessageBuilder();
    }
    /**
     * Zet de ontvanger bij de chatclient om meet te communiceren.
     * @param naam 
     */
    public void setOntvanger(String naam) 
    {
        this.cc.setOntvanger(naam);
    }  
    /**
     * 
     * @return De chatclient van de administratie klasse.
     */
    public ChatClient getCc() 
    {
        return cc;
    } 
    /**
     * Logt een nieuwe emergencyunit in.
     * @param username gebruikersnaam van deze user.
     * @param password wachtwoord van de gebruiker.
     * @return true als de combinatie van de gebruikersnaam en wachtwoord overeen
     * komen. False als deze niet overeenkomt.
     */
    public boolean loginEmergencyService(String username, String password)
    {
        commanager.addMessage(mBuilder.buildLoginMessage(username, password));
        try 
        {
            Thread.sleep(500);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Administratie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(personid != 0)
        {
            return true;
        }
        
        return false;
    }
    /**
     * Maakt een Administratie object aan als deze nog niet bestaat.
     * @return Een instantie van de Administratie klasse.
     */
    public static Administratie getInstance()
    {
        if(admin == null)
        {
            admin = new Administratie();
        }
        return admin;
    }
    /**
     * zorgt ervoor dat het opnemen van audio begint.
     */
    public void startRecordingAudio()
    {
        handler.startRecording();
    }
    /**
     * zorgt ervoor dat het opnemen van audio eindigt.
     */
    public void stopRecordingAudio()
    {
        handler.stopRecording();
    }
    /**
     * Maakt een nieuwe chatclient die ervoor zorgt dat communicatie mogelijk is 
     * tussen de meldkamer en de hulpdiensten.
     * @param user 
     */
    public void setChatClient(String user)
    {
        cc = new ChatClient(user, persontypeid, personid);
    }
    /**
     * Zorgt ervoor dat er een bericht wordt verstuurd naar de huidige ontvanger.
     * @param bericht dat verstuurd moet worden naar de huidige ontvanger.
     */
    public void sendMessage(String bericht)
    {
        cc.sendMessage(bericht);
    }
    /**
     * Roept een methode aan in de chatclient klasse die ervoor zorgt dat er een 
     * audiomessage wordt verzonden.
     */
    public void sendAudioMessage()
    {
        cc.sendAudioMessage(handler.getAudiofile(), handler.getPath());
    }
    /**
     * Leest de response uit die wordt ontvangen vannuit het protocol en zorgt
     * ervoor dat de gebruikers naam en wachtwoord kan worden gecontroleerd.
     * @param message 
     */
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

                switch (keyname) 
                {
                    case "personid":
                        personid = parser.getInt();
                        logmanager.setPersonId(personid);
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
