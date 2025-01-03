import javax.swing.*;
import java.awt.*;

/**
 * Klasa reprezentująca panel informacyjny wyświetlający wynik, poziom, świat i liczby żyć gracza.
 */
public class InfoPanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel worldLabel;
    private JLabel livesLabel;

    /**
     * Konstruktor klasy InfoPanel.
     *
     * @param customFont niestandardowa czcionka używana w panelu
     */
    public InfoPanel(Font customFont) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); // Marginesy 20px między etykietami, 10px między wierszami
        setBackground(new Color(255, 255, 255));

        scoreLabel = new JLabel("Wynik: 0");
        scoreLabel.setFont(customFont);
        scoreLabel.setForeground(Color.BLACK);
        add(scoreLabel);

        levelLabel = new JLabel("Poziom: 1");
        levelLabel.setFont(customFont);
        levelLabel.setForeground(Color.BLACK);
        add(levelLabel);

        worldLabel = new JLabel("Swiat: Boisko");
        worldLabel.setFont(customFont);
        worldLabel.setForeground(Color.BLACK);
        add(worldLabel);

        livesLabel = new JLabel("Zycia: 3");
        livesLabel.setFont(customFont);
        livesLabel.setForeground(Color.BLACK);
        add(livesLabel);
    }

    /**
     * Zwraca etykietę z wynikiem gracza.
     *
     * @return etykieta wyniku
     */
    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    /**
     * Zwraca etykietę z poziomem gry.
     *
     * @return etykieta poziomu
     */
    public JLabel getLevelLabel() {
        return levelLabel;
    }

    /**
     * Zwraca etykietę z aktualnym światem.
     *
     * @return etykieta świata
     */
    public JLabel getWorldLabel() {
        return worldLabel;
    }

    /**
     * Zwraca etykietę z liczbą żyć gracza.
     *
     * @return etykieta żyć
     */
    public JLabel getLivesLabel() {
        return livesLabel;
    }
}
