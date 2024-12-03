import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel worldLabel;
    private JLabel livesLabel;

    public InfoPanel(Font customFont) {
        // Ustawienie Layoutu, np. FlowLayout do rozmieszczenia etykiet w poziomie
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); // Marginesy 20px między etykietami, 10px między wierszami
        setBackground(new Color(255, 255, 255));

        // Tworzymy etykiety
        scoreLabel = new JLabel("Wynik: 0");
        scoreLabel.setFont(customFont);
        scoreLabel.setForeground(Color.BLACK);
        add(scoreLabel);  // Dodajemy do panelu

        levelLabel = new JLabel("Poziom: 1");
        levelLabel.setFont(customFont);
        levelLabel.setForeground(Color.BLACK);
        add(levelLabel);  // Dodajemy do panelu

        worldLabel = new JLabel("Swiat: Dom");
        worldLabel.setFont(customFont);
        worldLabel.setForeground(Color.BLACK);
        add(worldLabel);  // Dodajemy do panelu

        livesLabel = new JLabel("Zycia: 3");
        livesLabel.setFont(customFont);
        livesLabel.setForeground(Color.BLACK);
        add(livesLabel);  // Dodajemy do panelu
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public JLabel getLevelLabel() {
        return levelLabel;
    }

    public JLabel getWorldLabel() {
        return worldLabel;
    }

    public JLabel getLivesLabel() {
        return livesLabel;
    }
}