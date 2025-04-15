package org.george.chess.util.window;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

import org.george.chess.movegenerator.MoveGenerator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import static org.george.chess.util.Constants.*;

//TODO: Figure out a better design for handling content
//ContentHandler doesn't allow for handlers to extend on top of each other
//IE: Adding click and drag to move pieces is too difficult to add
//Maybe my own design of the content handler is too granular?
//Figure out later
//Condense input handling to one method
//Figure out a better way to handle events
//Register events to a listener for each content handler
public class Window extends JPanel implements KeyListener, MouseListener {
    private JFrame frame;
    private Set<Integer> highlightedTiles;
    private List<ContentHandler> contentHandlers;
    private int tile = -1;
    private MoveGenerator moveGenerator;

    public Window() {
        this.frame = new JFrame();
        highlightedTiles = new HashSet<>();
        frame.add(this);
        frame.setSize(SQUARE_SIZE * 10, SQUARE_SIZE * 10);
        frame.setVisible(true);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        contentHandlers = new ArrayList<>();
        moveGenerator = new MoveGenerator();
    }

    public Window(ContentHandler... contentHandlers) {
        this();
        for(ContentHandler contentHandler : contentHandlers){
            this.contentHandlers.add(contentHandler);
        }
    }

    public void acceptContent(Object content, Class<? extends ContentHandler> clazz){
        boolean refresh = false;
        for(ContentHandler contentHandler : contentHandlers){
            if(contentHandler.getClass().equals(clazz)){
                refresh = refresh || contentHandler.acceptContent(content);
            }
        }
        if(refresh){
            repaint();
        }
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
        for(ContentHandler contentHandler : contentHandlers){
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
        boolean refresh = false;
        for(ContentHandler contentHandler : contentHandlers){
            refresh = refresh || contentHandler.handleKeyPressed(e);
        }
        if(refresh){
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        final Point point = mouseEvent.getPoint();
        final int x = point.x - HORIZONTAL_SHIFT, y = point.y - VERTICAL_SHIFT;
        final int tileNumber = 63 - (x / SQUARE_SIZE + 8 * (y / SQUARE_SIZE));
        final int button = mouseEvent.getButton();
        boolean refresh = false;
        if(button == MouseEvent.BUTTON1){
            if (highlightedTiles.contains(tileNumber)) {
                highlightedTiles.remove(tileNumber);
            } else {
                highlightedTiles.add(tileNumber);
            }
        }
        for(ContentHandler contentHandler : contentHandlers){
            contentHandler.handleMousePressed(mouseEvent);
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
