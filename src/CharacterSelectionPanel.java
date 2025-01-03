import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterSelectionPanel extends JPanel {
    private JLabel characterPreview;
    private String[] characterImages = {"Postac1.png", "Postac2.png", "Postac3.png", "Postac4.png", "Postac5.png", "Postac6.png", "Postac7.png", "Postac8.png","Postac9.png", "Postac10.png", "Postac11.png", "Postac12.png", "Postac13.png", "Postac14.png", "Postac15.png", "Postac16.png"};
    private int currentIndex = 0;
    private Main mainApp;

    public CharacterSelectionPanel(Main mainApp, Font customFont) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#24338a"));

        characterPreview = new JLabel();
        updateCharacterPreview();
        add(characterPreview, BorderLayout.CENTER);

        JButton nextButton = new JButton("Nastepna");
        nextButton.setFont(customFont.deriveFont(18f));
        nextButton.addActionListener(e -> nextCharacter());
        add(nextButton, BorderLayout.EAST);

        JButton prevButton = new JButton("Poprzednia");
        prevButton.setFont(customFont.deriveFont(18f));
        prevButton.addActionListener(e -> previousCharacter());
        add(prevButton, BorderLayout.WEST);

        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.setBackground(Color.decode("#24338a"));

        JButton saveButton = new JButton("Zapisz");
        saveButton.setFont(customFont.deriveFont(18f));
        saveButton.addActionListener(e -> saveCharacterSelection());
        southPanel.add(saveButton);

        JButton backButton = new JButton("Powrot");
        backButton.setFont(customFont.deriveFont(18f));
        backButton.addActionListener(e -> mainApp.returnStraightToMainMenu());
        southPanel.add(backButton);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void updateCharacterPreview() {
        characterPreview.setIcon(new ImageIcon(getClass().getResource("/" + characterImages[currentIndex])));
        characterPreview.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void nextCharacter() {
        currentIndex = (currentIndex + 1) % characterImages.length;
        updateCharacterPreview();
    }

    private void previousCharacter() {
        currentIndex = (currentIndex - 1 + characterImages.length) % characterImages.length;
        updateCharacterPreview();
    }

    private void saveCharacterSelection() {
        mainApp.setSelectedCharacter(characterImages[currentIndex]);
        JOptionPane.showMessageDialog(this, "Wygląd postaci został zapisany!");
    }
}
