import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {
    private int playerX = 300, playerY = 300;
    private int[] foodX, foodY, currentFoodType;
    private Random random = new Random();
    private Image playerImage;
    private Image backgroundImage;
    private Main main;
    private boolean foodInitialized = false;
    private int healthyFoodCount = 0;  // Licznik zdrowego jedzenia

    public GamePanel(Main main) {
        this.main = main;
        foodX = new int[5];
        foodY = new int[5];
        currentFoodType = new int[5];

        playerImage = new ImageIcon(getClass().getResource("/potrac.png")).getImage();
        updateBackgroundForLevel(main.getLevel());

        // Dodanie listenera do inicjalizacji jedzenia po ustaleniu wymiarów panelu
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (!foodInitialized) {
                    spawnFood(); // Rozmieść jedzenie na początku
                    foodInitialized = true;
                }
            }
        });
    }

    public void updateBackgroundForLevel(int level) {
        switch (level) {
            case 1 -> backgroundImage = new ImageIcon(getClass().getResource("/Tlo1.png")).getImage();
            default -> backgroundImage = null;
        }

        // Opóźnione rozmieszczenie jedzenia
        SwingUtilities.invokeLater(() -> {
            if (foodInitialized) {
                spawnFood(); // Rozmieść jedzenie
            }
        });

        repaint();
    }

    public void movePlayer(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> playerY = Math.max(playerY - 10, 0);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> playerY = Math.min(playerY + 10, getHeight() - 30);
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> playerX = Math.max(playerX - 10, 0);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> playerX = Math.min(playerX + 10, getWidth() - 30);
        }
        checkFoodCollision();
        repaint();
    }

    private void spawnFood() {
        int width = getWidth();
        int height = getHeight();
        int minDistance = 100;

        // Jeśli wymiary są za małe, nie rozmieszczamy jedzenia
        if (width <= minDistance * 2 || height <= minDistance * 2) {
            System.err.println("Zbyt mala odleglosc by zespawnowac tu jedzenie.");
            return;
        }
        int foodCount;  // Liczba jedzenia na poziomie
        int unhealthyFoodCount; // Liczba niezdrowego jedzenia

        // Określamy liczbę jedzenia w zależności od poziomu
        switch (main.getLevel()) {
            case 1:
                foodCount = 25;
                break;
            case 2:
                foodCount = 30;
                break;
            case 3:
                foodCount = 40;
                break;
            default:
                foodCount = 25; // Domyślna wartość
        }

        // Niezdrowe jedzenie stanowi maksymalnie 20% całkowitej liczby jedzenia
        unhealthyFoodCount = foodCount / 5;  // 20% z foodCount

        healthyFoodCount = 0;  // Resetowanie licznika zdrowego jedzenia

        // Ustalamy tablicę na tyle dużą, aby pomieścić wszystkie posiłki
        foodX = new int[foodCount];
        foodY = new int[foodCount];
        currentFoodType = new int[foodCount]; // 0 - zdrowe, 1 - niezdrowe

        // Rozstawiamy jedzenie
        for (int i = 0; i < foodCount; i++) {
            foodX[i] = random.nextInt(width - minDistance * 2) + minDistance;
            foodY[i] = random.nextInt(height - minDistance * 2) + minDistance;

            // Upewniamy się, że niezdrowe jedzenie nie przekracza 20% całkowitej liczby jedzenia
            if (i < unhealthyFoodCount) {
                currentFoodType[i] = 1; // Niezdrowe jedzenie
            } else {
                currentFoodType[i] = 0; // Zdrowe jedzenie
                healthyFoodCount++;  // Zwiększamy licznik zdrowego jedzenia
            }
        }
    }


    private void checkFoodCollision() {
        for (int i = 0; i < foodX.length; i++) {
            if (Math.abs(playerX - foodX[i]) < 30 && Math.abs(playerY - foodY[i]) < 30) {
                if (currentFoodType[i] == 0) {
                    main.updateScore(main.getScore() + 10);
                    healthyFoodCount--; // Zebrano zdrowe jedzenie
                } else {
                    main.updateLives(main.getLives() - 1);
                }

                if (main.getLives() <= 0) {
                    JOptionPane.showMessageDialog(this, "Gra zakończona! Przegrałeś/aś.");
                    System.exit(0);
                }

                // Usuń zebrane jedzenie z planszy
                foodX[i] = -100;  // Umieść poza planszą
                foodY[i] = -100;

                // Sprawdź, czy wystarczająca liczba zdrowego jedzenia została zebrana
                if (healthyFoodCount <= 0) {
                    proceedToNextLevel();  // Przejdź do kolejnego poziomu
                }
            }
        }
    }

    private void proceedToNextLevel() {
        main.nextLevel();
        JOptionPane.showMessageDialog(this, "Gratulacje! Przechodzisz do kolejnego poziomu.");
        spawnFood();  // Rozmieść jedzenie na nowym poziomie
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        g.drawImage(playerImage, playerX, playerY, 60, 100, this);

        for (int i = 0; i < foodX.length; i++) {
            if (currentFoodType[i] == 0 && foodX[i] != -100 && foodY[i] != -100) {
                g.drawImage(new ImageIcon(getClass().getResource("/Food" + (i % 15 + 1) + ".png")).getImage(), foodX[i], foodY[i], 40, 40, this);
            } else if (foodX[i] != -100 && foodY[i] != -100) {
                g.drawImage(new ImageIcon(getClass().getResource("/JunkFood" + (i % 4 + 1) + ".png")).getImage(), foodX[i], foodY[i], 40, 40, this);
            }
        }

        drawLives(g);
    }

    private void gameOver() {
        int option = JOptionPane.showOptionDialog(this, "Gra zakończona! Chcesz zagrać ponownie?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Tak", "Nie"}, "Tak");

        if (option == JOptionPane.YES_OPTION) {
            main.startGame();  // Rozpocznij nową grę
        } else {
            System.exit(0);  // Zamknij grę
        }
    }


    private void drawLives(Graphics g) {
        Image liveHeart = new ImageIcon(getClass().getResource("/Live.png")).getImage();
        Image noLiveHeart = new ImageIcon(getClass().getResource("/NoLive.png")).getImage();

        for (int i = 0; i < 3; i++) {
            if (i < main.getLives()) {
                g.drawImage(liveHeart, 10 + (i * 50), 35, 40, 40, this);
            } else {
                g.drawImage(noLiveHeart, 10 + (i * 50), 35, 40, 40, this);
            }
        }
    }
}
