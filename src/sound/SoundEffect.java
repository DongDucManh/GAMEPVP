package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffect {
    private Clip clip;

    // Constructor nhận đường dẫn file âm thanh
    public SoundEffect() {
        try {
            // Tạo đối tượng File từ đường dẫn
            File soundFile = new File("res/sound/dinhdan.wav");

            // Lấy luồng dữ liệu âm thanh từ file
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            // Lấy đối tượng Clip và mở luồng dữ liệu âm thanh
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để phát âm thanh từ đầu
    public void play() {
        if (clip != null) {
            // Đảm bảo rằng âm thanh phát lại từ đầu mỗi lần gọi play()
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    // Phương thức để dừng phát âm thanh
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // (Tùy chọn) Phương thức để lặp lại âm thanh liên tục
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void main(String[] args) {
        SoundEffect soundEffect = new SoundEffect();
        soundEffect.loop();
    }
}
