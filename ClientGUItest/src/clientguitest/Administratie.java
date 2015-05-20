/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientguitest;

import Audio.AudioHandler;
import Database.*;

/**
 *
 * @author Leo
 */
public class Administratie 
{
    private IDatabase database;
    private static Administratie admin = null;
    private AudioHandler handler;
    private ChatClient cc;
    
    private Administratie()
    {
        //database = new SQL();
        handler = new AudioHandler();
    }

    public void setOntvanger(String naam) 
    {
        this.cc.setOntvanger(naam);
    }  
    
    public ChatClient getCc() 
    {
        return cc;
    } 
    
    public String loginEmergencyService(String username, String password, int id)
    {
        return database.loginPerson(username, password);
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
        cc = new ChatClient(user);        
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
}