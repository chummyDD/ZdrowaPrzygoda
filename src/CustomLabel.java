import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {
    public CustomLabel(String text, Font customFont) {
        super(text);
        setFont(customFont);
        setOpaque(true);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
    }
}