/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Leo
 */
public class AudioHandler 
{
    private RecordingThread recorder;   
    private File outputFile;
    private TargetDataLine targetLine;
    private AudioFileFormat.Type targetType;
    private Thread opnameThread;
    private AudioFormat audioFormat;
    private byte[] audiofile;
    DataLine.Info info;
    
    public AudioHandler()
    {
        //creerer een format voor de dataline om uit te lezen.
        audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100.0F, 16, 2, 4, 44100.0F, false);
        //maak correcte infor voor de dataline zodat deze weet hoe en wat het moet lezen. 
        info = new DataLine.Info(TargetDataLine.class, audioFormat);
        outputFile = new File("opname.wav");
    }

    public void setAudiofile(byte[] audiofile) 
    {
        this.audiofile = audiofile;
    }

    public byte[] getAudiofile() 
    {
        return audiofile;
    }
    
    public void startRecording()
    {
        outputFile = new File("opname.wav");
        initFile();
        recorder = new RecordingThread(targetLine, targetType, outputFile, this);
        opnameThread = new Thread(recorder);        
        opnameThread.start();
    }
    
    public void stopRecording()
    {
        recorder.stopRecording();
    }
    
    private void initFile()
    {
        targetLine = null;
        try 
        {
            //open de audiolijn zodat er kan worden opgenomen.
            targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open(audioFormat);
        } 
        catch (LineUnavailableException ex) 
        {
            Logger.getLogger(AudioHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* Again for simplicity, we've hardcoded the audio filetype, too.*/
        targetType = AudioFileFormat.Type.WAVE;
//        try
//        {
//            if(outputFile.exists())
//            {
//                outputFile.delete();
//                
//                outputFile.createNewFile();
//            }
//            else
//            {
//                outputFile.createNewFile();
//            }
//        }
//        catch(IOException ex)
//        {
//            Logger.getLogger(AudioHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
