package toolbox;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    public static void playSound(String filename) {
        new Thread(() -> {
            try {
                URL soundURL = SoundPlayer.class.getResource("/sounds/" + filename);
                if (soundURL == null) {
                    System.err.println("Son non trouvé : " + filename);
                    return;
                }

                AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start(); // joue le son
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start(); // non bloquant grâce au thread
    }
}

