import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;

/**
 * Klasa reprezentująca panel opcji, pozwalający graczom dostosować głośność muzyki.
 */
public class OptionsPanel extends JPanel {
    private JSlider menuVolumeSlider;
    private JSlider gameVolumeSlider;
    private Clip menuMusicClip;
    private Clip gameMusicClip;
    private Main mainApp;

    /**
     * Konstruktor klasy OptionsPanel.
     *
     * @param menuMusicClip klip muzyczny menu
     * @param gameMusicClip klip muzyczny gry
     * @param mainApp główna aplikacja gry
     * @param customFont niestandardowa czcionka używana w panelu
     */
    public OptionsPanel(Clip menuMusicClip, Clip gameMusicClip, Main mainApp, Font customFont) {
        this.menuMusicClip = menuMusicClip;
        this.gameMusicClip = gameMusicClip;
        this.mainApp = mainApp;

        setLayout(new GridLayout(5, 1));
        setBackground(Color.decode("#24338a")); // Tło panelu na #24338a

        JLabel menuVolumeLabel = new JLabel("Głośność menu:");
        menuVolumeLabel.setForeground(Color.WHITE); // Jasny kolor tekstu

        menuVolumeSlider = createTransparentSlider();
        menuVolumeSlider.addChangeListener(e -> adjustVolume(menuMusicClip, menuVolumeSlider.getValue()));

        JLabel gameVolumeLabel = new JLabel("Głośność gry:");
        gameVolumeLabel.setForeground(Color.WHITE); // Jasny kolor tekstu

        gameVolumeSlider = createTransparentSlider();
        gameVolumeSlider.addChangeListener(e -> adjustVolume(gameMusicClip, gameVolumeSlider.getValue()));

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

    /**
     * Tworzy przezroczysty suwak do regulacji głośności.
     *
     * @return przezroczysty suwak
     */
    private JSlider createTransparentSlider() {
        JSlider slider = new JSlider(0, 100, 50);
        slider.setOpaque(false); // Przezroczystość suwaka
        return slider;
    }

    /**
     * Dostosowuje głośność dla podanego klipu muzycznego.
     *
     * @param clip klip muzyczny do dostosowania głośności
     * @param volume nowa wartość głośności
     */
    private void adjustVolume(Clip clip, int volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
            float gain = (range * volume / 100) + volumeControl.getMinimum();
            volumeControl.setValue(gain);
        }
    }

    /**
     * Zapisuje ustawienia opcji.
     */
    private void saveSettings() {
        JOptionPane.showMessageDialog(this, "Ustawienia zostały zapisane!");
    }
}
