/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;

import Audio.AudioHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 *
 * @author Leo
 */
public class RecordingThread implements Runnable
{
    private TargetDataLine line;
    private AudioFileFormat.Type targetType;
    private AudioInputStream audioInputStream;
    private File outputFile;
    private AudioHandler handler;
    
    public RecordingThread(TargetDataLine line, AudioFileFormat.Type targetType, File file, AudioHandler handler)
    {
        this.line = line;
        this.targetType = targetType;
        this.outputFile = file;
        this.audioInputStream = new AudioInputStream(line);
        this.handler = handler;
    }
    
    @Override
    public void run() 
    {
        try 
        {
           // schrijf de opgenomen audio naar de meegegeven file.
            line.start();
            System.out.println("ik begin met schrijven");
            AudioSystem.write(audioInputStream, targetType, outputFile);
            System.out.println("ik stop met schrijven");
            System.out.println("Naar de byte[]");
            byte[] audiofile = Files.readAllBytes(outputFile.toPath());
            System.out.println("En daar voorbij");
            handler.setAudiofile(audiofile);                        
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(RecordingThread.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void stopRecording()
    {
        line.stop();
        line.close();
    }
}
