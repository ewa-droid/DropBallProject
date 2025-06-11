package sound;

import javafx.scene.media.AudioClip;

public class SoundManager // Klasa do dźwięku naciśniecia guzika (wykorzystana w wielu miejscach)
{
    private static AudioClip buttonSound;

    static 
    {
        try 
        {
            buttonSound = new AudioClip(SoundManager.class.getResource("/buttonclicked.wav").toExternalForm());
            buttonSound.setVolume(0.4);
        } 
        
        catch (Exception e) 
        {
            System.err.println("Nie można załadować dźwięku przycisku: " + e.getMessage());
        }
    }

    public static void playButtonSound() 
    {
        if (buttonSound != null) 
        {
            buttonSound.play();
        }
    }
}
