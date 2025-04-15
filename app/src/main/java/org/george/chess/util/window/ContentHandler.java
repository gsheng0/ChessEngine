package org.george.chess.util.window;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.Graphics;

public interface ContentHandler<T> {

    //Returns whether or not to repaint
    public boolean acceptContent(T content);

    //Returns whether or not to repaint
    public boolean handleKeyPressed(KeyEvent e);

    public boolean handleMousePressed(MouseEvent e);

    public void draw(Graphics g);

}
