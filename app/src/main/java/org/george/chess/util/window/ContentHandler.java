package org.george.chess.util.window;

import java.awt.event.KeyEvent;
import java.awt.Graphics;

public interface ContentHandler<T> {

    //Returns whether or not to repaint
    public boolean acceptContent(T content);

    //Returns whether or not to repaint
    public boolean handleKeyPressed(KeyEvent e);

    public void draw(Graphics g);
}
