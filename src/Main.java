import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import javax.swing.*;

public class Main extends JFrame {
    private int level = 1;
    private int score = 0;
    private int healthyFoodCollected = 0;
    private int requiredFoodToCollect = 5;
    private int lives = 3;
    private JLabel scoreLabel, levelLabel, worldLabel, livesLabel;
    private GamePanel gamePanel;
    private Font customFont;
    private MainMenu mainMenu;  // Panel Menu Głównego
    private InfoPanel infoPanel;  // InfoPanel

    public Main() {
        setTitle("Zdrowa Przygoda");
        setSize(1280, 724);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicjalizacja czcionki
        try (InputStream fontStream = getClass().getResourceAsStream("/MegamaxJonathanTooFont.ttf")) {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.BOLD, 18);
        }

        // Inicjujemy menu główne
        mainMenu = new MainMenu(this);
        add(mainMenu);

        // Inicjalizujemy InfoPanel, ale nie dodajemy go teraz
        infoPanel = new InfoPanel(customFont);

        // Inicjalizacja JLabel
        scoreLabel = infoPanel.getScoreLabel();
        levelLabel = infoPanel.getLevelLabel();
        worldLabel = infoPanel.getWorldLabel();
        livesLabel = infoPanel.getLivesLabel();

        // Fokus na JFrame
        setFocusable(true);

        // Umożliwiamy grze przechwytywanie zdarzeń klawiatury
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (gamePanel != null) {
                    gamePanel.movePlayer(e.getKeyCode());
                }
            }
        });
    }

    // Funkcja do rozpoczęcia gry
    public void startGame() {
        remove(mainMenu);  // Usuwamy menu główne
        gamePanel = new GamePanel(this);  // Tworzymy nowy panel gry
        add(gamePanel, BorderLayout.CENTER);

        // Dodajemy InfoPanel
        add(infoPanel, BorderLayout.NORTH);

        setFocusable(true);
        requestFocusInWindow();  // Zmieniamy fokus na panel gry
        revalidate();
        repaint();
    }

    // Funkcja przechodzenia do kolejnego poziomu
    public void nextLevel() {
        level++;
        healthyFoodCollected = 0;
        requiredFoodToCollect += 3;

        switch (level) {
            case 2 -> {
                worldLabel.setText("Environment: Kitchen");
                gamePanel.setBackground(new Color(255, 204, 153));
            }
            case 3 -> {
                worldLabel.setText("Environment: Garden");
                gamePanel.setBackground(new Color(204, 255, 204));
            }
            default -> {
                JOptionPane.showMessageDialog(this, "Gratulacje! Ukończyłeś/aś grę!");
                System.exit(0);
            }
        }
        levelLabel.setText("Level: " + level);
        gamePanel.updateBackgroundForLevel(level);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }

    public void updateScore(int newScore) {
        score = newScore;
        scoreLabel.setText("Wynik: " + score);  // Teraz scoreLabel nie jest null
    }

    public void updateLives(int newLives) {
        lives = newLives;
        livesLabel.setText("  Zycia: " + lives);
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }
}