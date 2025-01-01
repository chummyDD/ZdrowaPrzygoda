import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import javax.swing.*;

public class Main extends JFrame {
    private int level = 1;
    private int score = 0;
    private int healthyFoodCollected = 0;

    private final int initialLives = 3;
    private int requiredFoodToCollect = 5;
    private int lives = initialLives;
    private JLabel scoreLabel, levelLabel, worldLabel, livesLabel;
    private GamePanel gamePanel;
    private Font customFont;
    private MainMenu mainMenu;
    private InfoPanel infoPanel;

    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem returnToMenuItem, returnToGameItem;

    public Main() {
        setTitle("Zdrowa Przygoda");
        setSize(1280, 724);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try (InputStream fontStream = getClass().getResourceAsStream("/MegamaxJonathanTooFont.ttf")) {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.BOLD, 18);
        }

        mainMenu = new MainMenu(this);
        add(mainMenu);

        infoPanel = new InfoPanel(customFont);

        scoreLabel = infoPanel.getScoreLabel();
        levelLabel = infoPanel.getLevelLabel();
        worldLabel = infoPanel.getWorldLabel();
        livesLabel = infoPanel.getLivesLabel();

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (gamePanel != null) {
                    gamePanel.movePlayer(e.getKeyCode());
                }
            }
        });

        initializeMenu();
    }

    private void initializeMenu() {
        menuBar = new JMenuBar();
        optionsMenu = new JMenu("Opcje");

        returnToMenuItem = new JMenuItem("Powrót do menu głównego");
        returnToMenuItem.addActionListener((ActionEvent e) -> returnToMainMenu());

        returnToGameItem = new JMenuItem("Powrót do gry");
        returnToGameItem.addActionListener((ActionEvent e) -> returnToGame());

        optionsMenu.add(returnToMenuItem);
        optionsMenu.add(returnToGameItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);
    }

    public void startGame() {
        getContentPane().removeAll();
        gamePanel = new GamePanel(this);
        add(gamePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.NORTH);

        score = 0;
        level = 1;
        lives = initialLives;

        scoreLabel.setText("Wynik: " + score);
        levelLabel.setText("Poziom: " + level);
        livesLabel.setText("Zycia: " + lives);

        setFocusable(true);
        requestFocusInWindow();
        revalidate();
        repaint();
    }

    public void nextLevel() {
        level++;
        healthyFoodCollected = 0;
        requiredFoodToCollect += 3;

        switch (level) {
            case 2 -> {
                worldLabel.setText("Environment: Dom");
                gamePanel.setBackground(new Color(255, 204, 153));
            }
            case 3 -> {
                worldLabel.setText("Environment: Ogrod");
                gamePanel.setBackground(new Color(204, 255, 204));
            }
            default -> {
                JOptionPane.showMessageDialog(this, "Gratulacje! Ukończyłeś/aś grę!");
                returnStraightToMainMenu();
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
        scoreLabel.setText("Wynik: " + score);
    }

    public void updateLives(int newLives) {
        lives = newLives;
        livesLabel.setText("Zycia: " + lives);
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

    public void returnToMainMenu() {
        int option = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz wrócić do menu głównego? Postęp w grze zostanie utracony.",
                "Powrót do menu głównego", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            getContentPane().removeAll();
            add(mainMenu);
            revalidate();
            repaint();
        }
    }

    public void returnStraightToMainMenu() {
        getContentPane().removeAll();
        add(mainMenu);
        revalidate();
        repaint();
    }

    public void returnToGame() {
    }

    public void showOptionsScreen() {
        getContentPane().removeAll();
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BorderLayout());
        JLabel optionsLabel = new JLabel("Opcje", SwingConstants.CENTER);
        optionsLabel.setFont(customFont.deriveFont(32f));
        optionsPanel.add(optionsLabel, BorderLayout.CENTER);

        JButton returnToMenuButton = new JButton("Powrot do menu glownego");
        returnToMenuButton.setFont(customFont.deriveFont(18f));
        returnToMenuButton.addActionListener(e -> returnStraightToMainMenu());
        optionsPanel.add(returnToMenuButton, BorderLayout.SOUTH);

        add(optionsPanel);
        revalidate();
        repaint();
    }

    public void showAuthorScreen() {
        getContentPane().removeAll();
        JPanel authorPanel = new JPanel();
        authorPanel.setLayout(new BorderLayout());

        JTextArea authorTextArea = new JTextArea("Jakub Kunio 189002");
        authorTextArea.setFont(customFont.deriveFont(32f));
        authorTextArea.setLineWrap(true);
        authorTextArea.setWrapStyleWord(true);
        authorTextArea.setEditable(false);
        authorTextArea.setOpaque(false); // Aby tekst miał przezroczyste tło

        JScrollPane scrollPane = new JScrollPane(authorTextArea);
        scrollPane.setBorder(null); // Usunięcie obramowania

        authorPanel.add(scrollPane, BorderLayout.CENTER);

        JButton returnToMenuButton = new JButton("Powrot do menu glownego");
        returnToMenuButton.setFont(customFont.deriveFont(18f));
        returnToMenuButton.addActionListener(e -> returnStraightToMainMenu());
        authorPanel.add(returnToMenuButton, BorderLayout.SOUTH);

        add(authorPanel);
        revalidate();
        repaint();
    }
}