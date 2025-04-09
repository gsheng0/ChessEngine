package org.george.chess.util.window;

import java.util.Set;
import java.util.HashSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import static org.george.chess.util.Constants.*;

public class Window<T> extends JPanel implements KeyListener, MouseListener {
    private JFrame frame;
    private Set<Integer> highlightedTiles;
    private ContentHandler<T> contentHandler;

    public Window() {
        this.frame = new JFrame();
        highlightedTiles = new HashSet<>();
        frame.add(this);
        frame.setSize(SQUARE_SIZE * 10, SQUARE_SIZE * 10);
        frame.setVisible(true);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
    }

    public Window(ContentHandler<T> contentHandler) {
        this();
        this.contentHandler = contentHandler;
    }

    public void acceptContent(T content){
        contentHandler.acceptContent(content);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SQUARE_SIZE * 10, SQUARE_SIZE * 10);
        g.setFont(LABEL_FONT);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.setColor((i + j) % 2 == 0 ? DARK_COLOR : LIGHT_COLOR);
                int x = SQUARE_SIZE * i;
                int y = SQUARE_SIZE * j;
                int num = 63 - (i + j * 8);
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
                g.setColor(Color.BLACK);
                //g.drawString("" + num, x, y + 90);
                if (highlightedTiles.contains(num)) {
                    g2d.setStroke(new BasicStroke(5.0f));
                    g2d.setColor(Color.BLUE);
                    g2d.drawOval(x + 5, y + 5, SQUARE_SIZE - 10, SQUARE_SIZE - 10);
                }
            }
        }
        if (contentHandler != null) {
            contentHandler.draw(g);
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
        System.out.println("Key Pressed: " + e.getKeyChar());
        contentHandler.handleKeyPressed(e);
        repaint();
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
