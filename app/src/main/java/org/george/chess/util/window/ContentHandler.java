package org.george.chess.util.window;

import java.awt.event.KeyEvent;
import java.awt.Graphics;

public interface ContentHandler<T> {
    public void acceptContent(T content);

    public void acceptContent(T content, String label);

    public void handleKeyPressed(KeyEvent e);

    public void draw(Graphics g);
}
