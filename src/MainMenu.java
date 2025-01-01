import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainMenu extends JPanel {

    private Image backgroundImage;
    private Image logoImage;
    private Image scaledLogoImage;
    private int backgroundX = 0;
    private Timer animationTimer;

    public MainMenu(Main mainApp) {
        setLayout(null);  // Używamy null layout, aby dostosować ręcznie pozycje przycisków
        setBackground(Color.BLACK);

        // Wczytanie obrazów
        backgroundImage = new ImageIcon(getClass().getResource("/background.png")).getImage();
        logoImage = new ImageIcon(getClass().getResource("/logo.png")).getImage();
        scaledLogoImage = getScaledImage(logoImage, 500, 500); // Skalowanie obrazu logo
        startBackgroundAnimation();

        // Tworzenie przycisków
        JButton startButton = createButton("Start", 200, 200);
        JButton optionsButton = createButton("Opcje", 200, 280);
        JButton authorButton = createButton("Autor", 200, 360);
        JButton exitButton = createButton("Wyjście", 200, 440);

        // Akcja po kliknięciu "Start"
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainApp.startGame();  // Rozpoczynamy grę
            }
        });

        add(startButton); // Dodanie przycisku do panelu
        add(optionsButton); // Dodanie przycisku do panelu
        add(authorButton); // Dodanie przycisku do panelu
        add(exitButton); // Dodanie przycisku do panelu
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setForeground(Color.WHITE); // Kolor tekstu
        button.setBackground(Color.GRAY);  // Szary środek
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Czarne obramowanie
        button.setFocusPainted(false); // Wyłączenie efektu podświetlenia po kliknięciu
        button.setBounds(x, y, 200, 50); // Pozycja i rozmiar
        return button;
    }

    private Image getScaledImage(Image srcImg, int width, int height){
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();

        return resizedImg;
    }

    private void startBackgroundAnimation() {
        animationTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundX -= 2; // Przesuwanie obrazu w poziomie
                if (backgroundX <= -backgroundImage.getWidth(null)) {
                    backgroundX = 0; // Reset pozycji tła
                }
                repaint(); // Odświeżenie widoku
            }
        });
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rysowanie animowanego tła
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, backgroundX, 0, null);
            g.drawImage(backgroundImage, backgroundX + backgroundImage.getWidth(null), 0, null);
        }

        // Rysowanie logo o rozmiarze 200x200 nad przyciskami
        if (scaledLogoImage != null) {
            g.drawImage(scaledLogoImage, (getWidth() - 200) / 2, 100, null);
        }
    }
}