package view.Assets;

import javax.swing.*;
import java.awt.*;

public class FooterBar extends JPanel {
    public FooterBar() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("© Legendaria - Projet Java 2025", SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(label, BorderLayout.CENTER);
    }
}
