import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * Główna klasa aplikacji gry "Zdrowa Przygoda".
 * Odpowiada za inicjalizację gry, menu głównego, paneli informacji i opcji.
 */
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
    private OptionsPanel optionsPanel;

    private Clip menuMusicClip;
    private Clip gameMusicClip;

    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem returnToMenuItem, returnToGameItem;
    private String selectedCharacter = "Postac1.png"; // Default character

    /**
     * Konstruktor klasy Main.
     * Inicjalizuje główne okno gry, ładuje niestandardową czcionkę i muzykę oraz ustawia menu.
     */
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

        loadMusic();

        mainMenu = new MainMenu(this);
        add(mainMenu);

        infoPanel = new InfoPanel(customFont);

        scoreLabel = infoPanel.getScoreLabel();
        levelLabel = infoPanel.getLevelLabel();
        worldLabel = infoPanel.getWorldLabel();
        livesLabel = infoPanel.getLivesLabel();

        optionsPanel = new OptionsPanel(menuMusicClip, gameMusicClip, this, customFont);

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

    /**
     * Inicjalizuje menu główne.
     */
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

    /**
     * Ładuje muzykę dla menu głównego i gry.
     */
    private void loadMusic() {
        try {
            AudioInputStream menuAudioStream = AudioSystem.getAudioInputStream(getClass().getResource("/menu_music.wav"));
            menuMusicClip = AudioSystem.getClip();
            menuMusicClip.open(menuAudioStream);

            AudioInputStream gameAudioStream = AudioSystem.getAudioInputStream(getClass().getResource("/game_music.wav"));
            gameMusicClip = AudioSystem.getClip();
            gameMusicClip.open(gameAudioStream);

            FloatControl gameVolumeControl = (FloatControl) gameMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gameVolumeControl.setValue(-7.5f);

            menuMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            menuMusicClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rozpoczyna grę, ustawia początkowe wartości i wyświetla panel gry.
     */
    public void startGame() {
        if (menuMusicClip != null && menuMusicClip.isRunning()) {
            menuMusicClip.stop();
        }
        if (gameMusicClip != null && !gameMusicClip.isRunning()) {
            gameMusicClip.setFramePosition(0);
            gameMusicClip.start();
        }

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

    /**
     * Przechodzi do następnego poziomu gry.
     */
    public void nextLevel() {
        level++;
        healthyFoodCollected = 0;
        requiredFoodToCollect += 3;

        switch (level) {
            case 2 -> {
                worldLabel.setText("Swiat: Dom");
                gamePanel.setBackground(new Color(255, 204, 153));
            }
            case 3 -> {
                worldLabel.setText("Swiat: Ogrod");
                gamePanel.setBackground(new Color(204, 255, 204));
            }
            default -> {
                JOptionPane.showMessageDialog(this, "Gratulacje! Ukończyłeś/aś grę!");
                returnStraightToMainMenu();
            }
        }
        levelLabel.setText("Poziom: " + level);
        gamePanel.updateBackgroundForLevel(level);
    }

    /**
     * Główna metoda aplikacji.
     *
     * @param args argumenty wiersza poleceń (nie używane)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }

    /**
     * Aktualizuje wynik gracza.
     *
     * @param newScore nowy wynik do ustawienia
     */
    public void updateScore(int newScore) {
        score = newScore;
        scoreLabel.setText("Wynik: " + score);
    }

    /**
     * Aktualizuje liczbę żyć gracza.
     *
     * @param newLives nowa liczba żyć do ustawienia
     */
    public void updateLives(int newLives) {
        lives = newLives;
        livesLabel.setText("Zycia: " + lives);
    }

    /**
     * Zwraca aktualny wynik gracza.
     *
     * @return aktualny wynik
     */
    public int getScore() {
        return score;
    }

    /**
     * Zwraca aktualną liczbę żyć gracza.
     *
     * @return aktualna liczba żyć
     */
    public int getLives() {
        return lives;
    }

    /**
     * Zwraca aktualny poziom gry.
     *
     * @return aktualny poziom
     */
    public int getLevel() {
        return level;
    }

    /**
     * Powraca do menu głównego, resetując postęp w grze.
     */
    public void returnToMainMenu() {
        int option = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz wrócić do menu głównego? Postęp w grze zostanie utracony.",
                "Powrót do menu głównego", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (gameMusicClip != null && gameMusicClip.isRunning()) {
                gameMusicClip.stop();
            }
            if (menuMusicClip != null && !menuMusicClip.isRunning()) {
                menuMusicClip.start();
            }

            getContentPane().removeAll();
            add(mainMenu);
            revalidate();
            repaint();
        }
    }

    /**
     * Powraca bezpośrednio do menu głównego, resetując postęp w grze.
     */
    public void returnStraightToMainMenu() {
        if (gameMusicClip != null && gameMusicClip.isRunning()) {
            gameMusicClip.stop();
        }
        if (menuMusicClip != null && !menuMusicClip.isRunning()) {
            menuMusicClip.start();
        }

        getContentPane().removeAll();
        add(mainMenu);
        revalidate();
        repaint();
    }

    /**
     * Powraca do gry z menu opcji.
     */
    public void returnToGame() {
    }

    /**
     * Wyświetla panel opcji.
     */
    public void showOptionsScreen() {
        getContentPane().removeAll();
        add(optionsPanel);
        revalidate();
        repaint();
    }

    /**
     * Wyświetla informacje o autorze gry.
     */
    public void showAuthorScreen() {
        getContentPane().removeAll();
        JPanel authorPanel = new JPanel();
        authorPanel.setLayout(new BorderLayout());
        authorPanel.setBackground(Color.decode("#24338a"));

        JTextArea authorTextArea = new JTextArea("Jakub Kunio 189002, Elektronika i Telekomunikacja, E2A\n" +
                "Źródła użytych materiałów:\n" +
                "-Muzyka menu głównego: Pixabay/Onetent\n" +
                "-Muzyka z gry: Pixabay/SoundUniverseStudio\n" +
                "-Tło z menu głównego: Pixabay/Lesiakower\n" +
                "-Część obrazków jedzenia: Pixabay & itch.io\n");
        authorTextArea.setLineWrap(true);
        authorTextArea.setWrapStyleWord(true);
        authorTextArea.setEditable(false);
        authorTextArea.setOpaque(true);
        authorTextArea.setBackground(Color.decode("#24338a"));
        authorTextArea.setForeground(Color.WHITE);

        authorTextArea.setFont(new Font("SansSerif", Font.BOLD, 22));

        JScrollPane scrollPane = new JScrollPane(authorTextArea);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        authorPanel.add(scrollPane, BorderLayout.CENTER);

        JButton returnToMenuButton = new JButton("Powrot do menu glownego");
        returnToMenuButton.setFont(customFont.deriveFont(18f));
        returnToMenuButton.addActionListener(e -> returnStraightToMainMenu());
        authorPanel.add(returnToMenuButton, BorderLayout.SOUTH);

        add(authorPanel);
        revalidate();
        repaint();
    }

    /**
     * Zwraca aktualnie wybraną postać.
     *
     * @return wybrana postać
     */
    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    /**
     * Wyświetla menu wyboru postaci.
     */
    public void showCharacterSelectionMenu() {
        getContentPane().removeAll();
        add(new CharacterSelectionPanel(this, customFont));
        revalidate();
        repaint();
    }

    /**
     * Ustawia wybraną postać.
     *
     * @param character nazwa pliku z wybraną postacią
     */
    public void setSelectedCharacter(String character) {
        this.selectedCharacter = character;
        mainMenu.updateCharacterPreview(); // Aktualizacja podglądu w menu głównym
    }
}
