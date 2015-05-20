/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author Leo
 */
public class AudioMessage extends Message
{
    private byte[] audiofile;
    
    public AudioMessage(String bericht, String afzender, String ontvanger, byte[] audiofile) 
    {
        super(bericht, afzender, ontvanger);
        this.audiofile = audiofile;
    }

    public byte[] getAudiofile() 
    {
        return audiofile;
    }
}
