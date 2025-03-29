package org.george.chess.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static org.george.chess.util.Constants.DARK_COLOR;
import static org.george.chess.util.Constants.LIGHT_COLOR;
import static org.george.chess.util.Constants.SQUARE_SIZE;
import static org.george.chess.util.Constants.LABEL_FONT;

public class Window extends JPanel implements KeyListener {
    private JFrame frame;

    public Window() {
        this.frame = new JFrame();
        frame.add(this);
        frame.setSize(SQUARE_SIZE * 10, SQUARE_SIZE * 10);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SQUARE_SIZE * 10, SQUARE_SIZE * 10);
        g.setFont(LABEL_FONT);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.setColor((i + j) % 2 == 0 ? DARK_COLOR : LIGHT_COLOR);
                int x = SQUARE_SIZE * i;
                int y = SQUARE_SIZE * j;
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
                int num = 63 - (i + j * 8);
                g.setColor(Color.BLACK);
                g.drawString("" + num, x, y + 90);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}
