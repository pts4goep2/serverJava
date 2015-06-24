/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * Klasse om audio op te nemen en op te slaan in een byte[]
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
    private ExecutorService threadpool;
    private Future<byte[]> fut;
    private DataLine.Info info;
    private String path;
    private SimpleDateFormat sdfDate; 
    
    public AudioHandler()
    {
        //creerer een format voor de dataline om uit te lezen.
        audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100.0F, 16, 2, 4, 44100.0F, false);
        //maak correcte infor voor de dataline zodat deze weet hoe en wat het moet lezen. 
        info = new DataLine.Info(TargetDataLine.class, audioFormat);
        sdfDate = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss.SSS");;//dd/MM/yyyy
        threadpool = Executors.newFixedThreadPool(1);
        File file = new File("Opnames");
        if(!file.exists())
        {
            file.mkdir();
        }
    }
    /**
     * 
     * @return het pad van het bestand dat zojuist is opgenomen.
     */
    public String getPath() 
    {
        return path;
    }
    /**
     * 
     * @return de audiofile die is opgenomen als byte[]
     */
    public byte[] getAudiofile() 
    {
        return audiofile;
    }
    /**
     * Een methode die de systeemtijd ophaald.
     * @return een string met de systeem datum en tijd op ms nauwkeurig.
     */
    private String getSystemTimeAsString()
    {
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    /**
     * Methode die het opnemen van audio start. De microfoon wordt aangeroepen 
     * en de input wordt naar een bestand geschreven.
     */
    public void startRecording()
    {        
        initFile();
        recorder = new RecordingThread(targetLine, targetType, outputFile);
        fut = threadpool.submit(recorder);
    }
    /**
     * Stopt het opnemen van de audio en slaat het bestand in format van een
     * byte[] zodat dit kan worden meegegeven in een audiomessage.
     */
    public void stopRecording()
    {
        recorder.stopRecording();
        while(!fut.isDone())
        {
            // wacht tot de thread klaar is.
        }
        try 
        {
            this.audiofile = fut.get();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(AudioHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ExecutionException ex) 
        {
            Logger.getLogger(AudioHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Maakt een nieuw bestand aan waarin de opname kan worden gestopt en zorgt 
     * ervoor dat de microfoon wordt aangeroepen en als input kan worden gebruikt.
     */
    private void initFile()
    {
        targetLine = null;
        path = "Opnames/opname " + getSystemTimeAsString() + ".wav";
        outputFile = new File(path);
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
    }
}
