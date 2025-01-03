import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Klasa reprezentująca menu główne gry.
 */
public class MainMenu extends JPanel {
    private Image backgroundImage;
    private Image logoImage;
    private Image scaledLogoImage;
    private int backgroundX = 0;
    private Timer animationTimer;
    private Clip menuMusicClip;
    private Main mainApp;
    private JLabel characterPreview;

    /**
     * Konstruktor klasy MainMenu.
     *
     * @param mainApp główna aplikacja gry
     */
    public MainMenu(Main mainApp) {
        this.mainApp = mainApp;
        setLayout(null);
        setBackground(Color.BLACK);

        backgroundImage = new ImageIcon(getClass().getResource("/background.png")).getImage();
        logoImage = new ImageIcon(getClass().getResource("/logo.png")).getImage();
        scaledLogoImage = getScaledImage(logoImage, 500, 500);
        startBackgroundAnimation();

        // Panel przycisków po lewej stronie
        JButton startButton = createButton("Start", 50, 200);
        JButton optionsButton = createButton("Opcje", 50, 280);
        JButton authorButton = createButton("Autor", 50, 360);
        JButton exitButton = createButton("Wyjście", 50, 440);

        startButton.addActionListener(e -> mainApp.startGame());

        optionsButton.addActionListener(e -> mainApp.showOptionsScreen());

        authorButton.addActionListener(e -> mainApp.showAuthorScreen());

        exitButton.addActionListener(e -> System.exit(0));

        add(startButton);
        add(optionsButton);
        add(authorButton);
        add(exitButton);

        // Panel podglądu postaci po prawej stronie
        JPanel characterPanel = new JPanel();
        characterPanel.setLayout(new BorderLayout());
        characterPanel.setBounds(330, 200, 220, 300);  // Można dostosować wymiary i położenie
        characterPanel.setBackground(Color.LIGHT_GRAY);  // Ustawienie tła na jasno szary dla kontrastu

        characterPreview = new JLabel();
        updateCharacterPreview();
        characterPreview.setHorizontalAlignment(SwingConstants.CENTER);

        JButton changeAppearanceButton = new JButton("Zmień wygląd");
        changeAppearanceButton.setFont(new Font("Arial", Font.BOLD, 30));
        changeAppearanceButton.setForeground(Color.WHITE);
        changeAppearanceButton.setBackground(Color.GRAY);
        changeAppearanceButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        changeAppearanceButton.setFocusPainted(false);
        changeAppearanceButton.addActionListener(e -> mainApp.showCharacterSelectionMenu());

        characterPanel.add(characterPreview, BorderLayout.CENTER);
        characterPanel.add(changeAppearanceButton, BorderLayout.SOUTH);

        add(characterPanel);
    }

    /**
     * Tworzy przycisk z określonym tekstem, pozycją i stylem.
     *
     * @param text tekst na przycisku
     * @param x pozycja X przycisku
     * @param y pozycja Y przycisku
     * @return utworzony przycisk
     */
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        button.setFocusPainted(false);
        button.setBounds(x, y, 200, 50);  // Ustawienie rozmiaru i pozycji przycisków
        return button;
    }

    /**
     * Skaluje obraz do określonych rozmiarów.
     *
     * @param srcImg źródłowy obraz
     * @param width szerokość docelowa
     * @param height wysokość docelowa
     * @return przeskalowany obraz
     */
    private Image getScaledImage(Image srcImg, int width, int height) {
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();

        return resizedImg;
    }

    /**
     * Rozpoczyna animację tła.
     */
    private void startBackgroundAnimation() {
        animationTimer = new Timer(30, e -> {
            backgroundX -= 2;
            if (backgroundX <= -backgroundImage.getWidth(null)) {
                backgroundX = 0;
            }
            repaint();
        });
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, backgroundX, 0, null);
            g.drawImage(backgroundImage, backgroundX + backgroundImage.getWidth(null), 0, null);
        }

        if (scaledLogoImage != null) {
            g.drawImage(scaledLogoImage, (getWidth()) / 2, 100, null);
        }
    }

    /**
     * Aktualizuje podgląd wybranej postaci w menu.
     */
    public void updateCharacterPreview() {
        characterPreview.setIcon(new ImageIcon(getClass().getResource("/" + mainApp.getSelectedCharacter())));
    }
}
