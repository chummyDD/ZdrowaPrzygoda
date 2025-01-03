import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
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
    private int healthyFoodCount = 0;

    private String foodDescription = "";
    private Timer descriptionTimer;
    private Map<String, String> foodDescriptions;
    private Map<String, String> junkFoodDescriptions;

    public GamePanel(Main main) {
        this.main = main;
        foodX = new int[5];
        foodY = new int[5];
        currentFoodType = new int[5];

        playerImage = new ImageIcon(getClass().getResource("/" + main.getSelectedCharacter())).getImage();
        updateBackgroundForLevel(main.getLevel());

        // Initialize food and junk food descriptions
        initializeFoodDescriptions();
        initializeJunkFoodDescriptions();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (!foodInitialized) {
                    spawnFood();
                    foodInitialized = true;
                }
            }
        });
    }

    private void initializeFoodDescriptions() {
        foodDescriptions = new HashMap<>();
        foodDescriptions.put("Food1.png", "Banany są bogate w potas, witaminę B6 i błonnik, co wspiera serce, mięśnie i trawienie, czyniąc je zdrową i energetyczną przekąską.");
        foodDescriptions.put("Food2.png", "Borówki są pełne przeciwutleniaczy, witaminy C i błonnika, wspierając odporność, zdrowie serca i układ trawienny, a przy tym są niskokaloryczne.");
        foodDescriptions.put("Food3.png", "Czereśnie są bogate w antyoksydanty, witaminę C i melatoninę, co wspiera odporność, zdrowy sen i redukcję stanów zapalnych.");
        foodDescriptions.put("Food4.png", "Czerwone winogrona zawierają resweratrol, witaminę C i błonnik, wspierając serce, odporność i zdrowe trawienie.");
        foodDescriptions.put("Food5.png", "Białe winogrona dostarczają witamin C i K oraz antyoksydantów, wspomagając odporność, zdrowe kości i ogólną witalność.");
        foodDescriptions.put("Food6.png", "Limonki są pełne witaminy C i związków alkalizujących, wzmacniając odporność i wspierając detoksykację organizmu.");
        foodDescriptions.put("Food7.png", "Pomarańcze są źródłem witaminy C, błonnika i przeciwutleniaczy, wspierając odporność, zdrowie skóry i trawienie.");
        foodDescriptions.put("Food8.png", "Mandarynki dostarczają witaminy C, flawonoidów i błonnika, wspomagając odporność, zdrową skórę i metabolizm.");
        foodDescriptions.put("Food9.png", "Jabłka są bogate w błonnik, witaminę C i przeciwutleniacze, wspierając zdrowie serca, trawienie i kontrolę poziomu cukru.");
        foodDescriptions.put("Food10.png", "Arbuz zawiera dużo wody, likopen i witaminę A, nawadniając organizm, wspierając zdrowie skóry i ochronę przed stresem oksydacyjnym.");
        foodDescriptions.put("Food11.png", "Papryka jest pełna witaminy C, witaminy A i błonnika, wspierając odporność, zdrową skórę i metabolizm.");
        foodDescriptions.put("Food12.png", "Marchewki są bogate w beta-karoten, witaminę A i błonnik, wspierając zdrowy wzrok, skórę i trawienie.");
        foodDescriptions.put("Food13.png", "Bakłażany zawierają błonnik, antyoksydanty i niską ilość kalorii, wspierając zdrowie serca i kontrolę wagi.");
        foodDescriptions.put("Food14.png", "Ziemniaki dostarczają potasu, witaminy C i skrobi, wspierając zdrowie mięśni, odporność i dostarczając energii.");
        foodDescriptions.put("Food15.png", "Pomidory są bogate w likopen, witaminę C i potas, wspierając zdrowie serca, skóry i odporność.");
        foodDescriptions.put("Food16.png", "Woda nie zawiera kalorii, a dostarcza niezbędnego nawodnienia, wspierając funkcjonowanie organizmu, detoksykację i zdrowy metabolizm.");
        foodDescriptions.put("Food17.png", "Cytryny mogą być kwaśne i drażniące dla szkliwa zębów, ale warto je jeść, bo są bogate w witaminę C i wspierają odporność oraz trawienie.");
        foodDescriptions.put("Food18.png", "Kiwi zawiera kwas szczawiowy, który może sprzyjać kamieniom nerkowym u niektórych osób, ale warto je jeść, bo dostarcza witaminę C, błonnik i przeciwutleniacze.");
        foodDescriptions.put("Food19.png", "Melon ma wysoki indeks glikemiczny, ale warto go jeść, bo jest niskokaloryczny i bogaty w witaminy oraz wodę, wspomagając nawodnienie.");
        foodDescriptions.put("Food20.png", "Gruszki mogą zawierać fruktozę, która w nadmiarze może obciążać układ trawienny, ale warto je jeść, bo dostarczają błonnika i witamin.");
        foodDescriptions.put("Food21.png", "Truskawki mogą zawierać pozostałości pestycydów, ale warto je jeść, bo są bogate w witaminę C, antyoksydanty i mało kaloryczne.");
        foodDescriptions.put("Food22.png", "Ogórek ma mało składników odżywczych w porównaniu do innych warzyw, ale warto go jeść, bo doskonale nawadnia organizm.");
        foodDescriptions.put("Food23.png", "Śliwki mogą być ciężkostrawne dla niektórych, ale warto je jeść, bo wspomagają trawienie dzięki zawartości błonnika.");
        foodDescriptions.put("Food24.png", "Smoczy owoc ma niską zawartość kalorii i cukrów, ale warto go jeść, bo dostarcza antyoksydantów i witaminę C.");
        foodDescriptions.put("Food25.png", "Figi zawierają dużo naturalnych cukrów, ale warto je jeść, bo są bogate w błonnik i wspierają zdrowie jelit.");
 }

    private void initializeJunkFoodDescriptions() {
        junkFoodDescriptions = new HashMap<>();
        junkFoodDescriptions.put("JunkFood1.png", "Hamburgery często zawierają dużo nasyconych tłuszczów, soli i przetworzonych dodatków, co może prowadzić do problemów z sercem i wagą.");
        junkFoodDescriptions.put("JunkFood2.png", "Frytki są bogate w tłuszcze trans, sól i kalorie, co sprzyja otyłości i zwiększa ryzyko chorób serca.");
        junkFoodDescriptions.put("JunkFood3.png", "Pepsi zawiera dużo cukru i sztucznych dodatków, co może powodować próchnicę, nadwagę i skoki poziomu cukru we krwi.");
        junkFoodDescriptions.put("JunkFood4.png", "Sprite jest pełen cukru i pustych kalorii, co zwiększa ryzyko otyłości i problemów metabolicznych.");
        junkFoodDescriptions.put("JunkFood5.png", "Chipsy zawierają dużo soli, tłuszczów trans i sztucznych dodatków, co zwiększa ryzyko otyłości i chorób serca.");
        junkFoodDescriptions.put("JunkFood6.png", "Hotdogi są pełne tłuszczów nasyconych, soli i konserwantów, co może obciążać serce i sprzyjać chorobom układu krążenia.");
        junkFoodDescriptions.put("JunkFood7.png", "Żelki zawierają dużo cukru i sztucznych barwników, co może prowadzić do próchnicy i skoków cukru we krwi.");
        junkFoodDescriptions.put("JunkFood8.png", "Pączki są bogate w cukry, tłuszcze trans i puste kalorie, sprzyjając przybieraniu na wadze i problemom z cukrzycą.");
        junkFoodDescriptions.put("JunkFood9.png", "Ser może zawierać dużo tłuszczów nasyconych i soli, co przy nadmiernym spożyciu obciąża serce i nerki.");
        junkFoodDescriptions.put("JunkFood10.png", "Lody zawierają dużo cukru, tłuszczu i kalorii, co może prowadzić do otyłości i skoków poziomu cukru we krwi.");
        junkFoodDescriptions.put("JunkFood11.png", "Białe pieczywo ma mało błonnika i składników odżywczych, co powoduje szybkie skoki cukru i brak uczucia sytości.");
        junkFoodDescriptions.put("JunkFood12.png", "Ciastka są pełne cukru, tłuszczów trans i pustych kalorii, co sprzyja przybieraniu na wadze i chorobom serca.");
        junkFoodDescriptions.put("JunkFood13.png", "Czekolada, zwłaszcza mleczna, zawiera dużo cukru i tłuszczu, co może prowadzić do otyłości i problemów z zębami.");
        junkFoodDescriptions.put("JunkFood14.png", "Red Bull zawiera dużo cukru i kofeiny, co może prowadzić do skoków poziomu energii, a potem jej spadków, obciążając serce i układ nerwowy.");
    }

    public void updateBackgroundForLevel(int level) {
        switch (level) {
            case 1 -> backgroundImage = new ImageIcon(getClass().getResource("/Tlo1.png")).getImage();
            case 2 -> backgroundImage = new ImageIcon(getClass().getResource("/Tlo2.png")).getImage();
            case 3 -> backgroundImage = new ImageIcon(getClass().getResource("/Tlo3.png")).getImage();
            default -> backgroundImage = null;
        }

        SwingUtilities.invokeLater(() -> {
            if (foodInitialized) {
                spawnFood();
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

        if (width <= minDistance * 2 || height <= minDistance * 2) {
            System.err.println("Zbyt mała odległość by zespawnować tu jedzenie.");
            return;
        }
        int foodCount;
        int unhealthyFoodCount;

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
                foodCount = 25;
        }

        unhealthyFoodCount = foodCount / 5;

        healthyFoodCount = 0;

        foodX = new int[foodCount];
        foodY = new int[foodCount];
        currentFoodType = new int[foodCount];

        for (int i = 0; i < foodCount; i++) {
            foodX[i] = random.nextInt(width - minDistance * 2) + minDistance;
            foodY[i] = random.nextInt(height - minDistance * 2) + minDistance;

            if (i < unhealthyFoodCount) {
                currentFoodType[i] = 1;
            } else {
                currentFoodType[i] = 0;
                healthyFoodCount++;
            }
        }
    }
    private void checkFoodCollision() {
        for (int i = 0; i < foodX.length; i++) {
            if (Math.abs(playerX - foodX[i]) < 30 && Math.abs(playerY - foodY[i]) < 30) {
                if (currentFoodType[i] == 0) {
                    main.updateScore(main.getScore() + 10);
                    healthyFoodCount--;
                    showFoodDescription("Food" + (i % 25 + 1) + ".png", true);
                } else {
                    main.updateLives(main.getLives() - 1);
                    showFoodDescription("JunkFood" + (i % 14 + 1) + ".png", false);
                }

                if (main.getLives() <= 0) {
                    gameOver();
                    return;
                }

                foodX[i] = -100;
                foodY[i] = -100;

                if (healthyFoodCount <= 0) {
                    proceedToNextLevel();
                }
            }
        }
    }

    private void showFoodDescription(String foodImage, boolean isHealthy) {
        foodDescription = isHealthy ? foodDescriptions.get(foodImage) : junkFoodDescriptions.get(foodImage);
        if (foodDescription != null) {
            if (descriptionTimer != null) {
                descriptionTimer.stop();
            }
            descriptionTimer = new Timer(10000, (ActionEvent e) -> foodDescription = "");
            descriptionTimer.setRepeats(false);
            descriptionTimer.start();
        }
    }

    private void proceedToNextLevel() {
        main.nextLevel();
        if (main.getLevel()<4) {
            JOptionPane.showMessageDialog(this, "Gratulacje! Przechodzisz do kolejnego poziomu.");
            spawnFood();
            repaint();
        }
    }

    private void gameOver() {
        int option = JOptionPane.showOptionDialog(this, "Gra zakończona! Chcesz zagrać ponownie?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Tak", "Nie"}, "Tak");

        if (option == JOptionPane.YES_OPTION) {
            main.startGame();
        } else {
            main.returnStraightToMainMenu();
        }
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
                g.drawImage(new ImageIcon(getClass().getResource("/Food" + (i % 25 + 1) + ".png")).getImage(), foodX[i], foodY[i], 40, 40, this);
            } else if (foodX[i] != -100 && foodY[i] != -100) {
                g.drawImage(new ImageIcon(getClass().getResource("/JunkFood" + (i % 14 + 1) + ".png")).getImage(), foodX[i], foodY[i], 40, 40, this);
            }
        }

        drawLives(g);
        drawOutlinedString(g, foodDescription, 10, 30);
    }

    private void drawOutlinedString(Graphics g, String text, int x, int y) {
        if (text != null && !text.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Draw black outline
            g2.setColor(Color.BLACK);
            g2.drawString(text, x - 1, y - 1);
            g2.drawString(text, x - 1, y + 1);
            g2.drawString(text, x + 1, y - 1);
            g2.drawString(text, x + 1, y + 1);

            // Draw white text
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
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
