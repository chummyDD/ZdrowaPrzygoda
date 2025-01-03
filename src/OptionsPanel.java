import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {
    private JSlider menuVolumeSlider;
    private JSlider gameVolumeSlider;
    private Clip menuMusicClip;
    private Clip gameMusicClip;
    private Main mainApp;

    public OptionsPanel(Clip menuMusicClip, Clip gameMusicClip, Main mainApp, Font customFont) {
        this.menuMusicClip = menuMusicClip;
        this.gameMusicClip = gameMusicClip;
        this.mainApp = mainApp;

        setLayout(new GridLayout(5, 1));
        setBackground(Color.decode("#24338a")); // Tło panelu na #24338a

        JLabel menuVolumeLabel = new JLabel("Głośność menu:");
        menuVolumeLabel.setForeground(Color.WHITE); // Jasny kolor tekstu

        menuVolumeSlider = createTransparentSlider();

        JLabel gameVolumeLabel = new JLabel("Głośność gry:");
        gameVolumeLabel.setForeground(Color.WHITE); // Jasny kolor tekstu

        gameVolumeSlider = createTransparentSlider();

        JButton saveButton = new JButton("Zapisz");
        saveButton.setFont(customFont.deriveFont(18f));
        saveButton.addActionListener(e -> saveSettings());

        JButton returnButton = new JButton("Powrot do menu glownego");
        returnButton.setFont(customFont.deriveFont(18f));
        returnButton.addActionListener(e -> mainApp.returnStraightToMainMenu());

        add(menuVolumeLabel);
        add(menuVolumeSlider);
        add(gameVolumeLabel);
        add(gameVolumeSlider);
        add(saveButton);
        add(returnButton);
    }

    private JSlider createTransparentSlider() {
        JSlider slider = new JSlider(0, 100, 50);
        slider.setOpaque(false); // Przezroczystość suwaka
        slider.addChangeListener(e -> adjustVolume(menuMusicClip, slider.getValue()));
        return slider;
    }

    private void adjustVolume(Clip clip, int volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
            float gain = (range * volume / 100) + volumeControl.getMinimum();
            volumeControl.setValue(gain);
        }
    }

    private void saveSettings() {
        JOptionPane.showMessageDialog(this, "Ustawienia zostały zapisane!");
    }

}
