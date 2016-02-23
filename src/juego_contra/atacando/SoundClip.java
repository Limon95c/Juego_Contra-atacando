package juego_contra.atacando;

/**
 * SoundClip
 *
 * Modela la definición de todos los objetos de tipo
 * <code>SoundClip</code>
 *
 * @author Jorge Gonzalez Borboa - Jorge Limón Cabrera
 * @version 1.0
 * @date dd/Mes/2016
 */
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.net.URL;
                                                        
public class SoundClip { 
                                                       
    private AudioInputStream sample;
    private Clip clip; 
    private boolean looping = false;
    private int repeat = 0;
    private String filename = "";
    
    public SoundClip() {                                                        
        try {                                                        
            clip = AudioSystem.getClip();
        }catch (LineUnavailableException e) {                                    
            System.out.println("Error en " + e.toString());
        }
    }
    
    public SoundClip(String filename) {                                                        
        this();
        load(filename);
    }
    
    public void setLooping(boolean looping) {
        this.looping = looping; 
    }           
                                            
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }  
                                                      
    public void setFilename(String filename) {
        this.filename = filename; 
    }
    
    public Clip getClip() {                                                        
        return clip; 
    }
                                                   
    public boolean getLooping() {                                                        
        return looping;
    }
                                                      
    public int getRepeat() {                                                        
        return repeat; 
    }
                                                     
    public String getFilename() {                                                        
        return filename;
    }
                                                    
    private URL getURL(String filename) {                                                      
        URL url = null;
        try {                                                     
            url = this.getClass().getResource(filename);
        }catch (Exception e) {                                                  
            System.out.println("Error en " + e.toString());
        }
        return url;
    }
    
    public boolean isLoaded() {                                                        
        return (boolean)(sample != null);
    }
    
    public boolean load(String audiofile) {                                                        
        try {
            setFilename(audiofile);
            sample = AudioSystem.getAudioInputStream(getURL(filename)); 
            clip.open(sample); 
            return true;
        } catch (IOException e) {                                                    
            System.out.println("Error en " + e.toString());
            return false;
        }catch (UnsupportedAudioFileException e) {                                                       
            System.out.println("Error en " + e.toString());
            return false;
        }catch (LineUnavailableException e) {                                                      
            System.out.println("Error en " + e.toString());
            return false;
        }
    }
    
    public void play() {                                                        
        if (!isLoaded()) 
            return;                                                        
        clip.setFramePosition(0);                                             
        if (looping) 
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        else 
            clip.loop(repeat);
    }
    public void stop() {                                                        
        clip.stop();
    }
}