package org.george.chess.util;

import java.util.Set;
import java.util.HashSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import static org.george.chess.util.Constants.*;

public class Window extends JPanel implements KeyListener, MouseListener {
    private JFrame frame;
    private Set<Integer> highlightedTiles;

    public Window() {
        this.frame = new JFrame();
        highlightedTiles = new HashSet<>();
        frame.add(this);
        frame.setSize(SQUARE_SIZE * 10, SQUARE_SIZE * 10);
        frame.setVisible(true);
        frame.addMouseListener(this);
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
                int num = 63 - (i + j * 8);
                if (highlightedTiles.contains(num)) {
                    g.setColor(Color.RED);
                }
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
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

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point point = mouseEvent.getPoint();
        int x = point.x - HORIZONTAL_SHIFT, y = point.y - VERTICAL_SHIFT;
        int tileNumber = 63 - (x / SQUARE_SIZE + 8 * (y / SQUARE_SIZE));
        if (highlightedTiles.contains(tileNumber)) {
            highlightedTiles.remove(tileNumber);
        } else {
            highlightedTiles.add(tileNumber);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

}
