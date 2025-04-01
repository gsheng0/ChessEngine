package org.george.chess.util.window;

import java.util.ArrayList;
import java.util.List;

import org.george.chess.util.Constants;
import org.george.chess.util.Logger;

import java.awt.event.KeyEvent;
import java.awt.Graphics;

import static org.george.chess.util.Constants.SQUARE_SIZE;

import java.awt.Color;

public class BitMaskContentHandler implements ContentHandler<long[]> {
    private static final Logger logger = Logger.of(BitMaskContentHandler.class);
    private List<long[]> masks;
    private List<String> labels;
    private int index = 0;
    private int subIndex = 0;

    public BitMaskContentHandler() {
        masks = new ArrayList<>();
        labels = new ArrayList<>();
    }

    @Override
    public void acceptContent(final long[] content) {
        masks.add(content);
        labels.add("");
    }

    @Override
    public void acceptContent(final long[] content, String label) {
        masks.add(content);
        labels.add(label);
    }

    @Override
    public void handleKeyPressed(final KeyEvent e) {
        if (e.getKeyChar() == 'l' && ++subIndex >= masks.get(index).length) {
            subIndex = 0;
            index = Math.min(masks.size() - 1, index + 1);
        } else if (e.getKeyChar() == 'h' && --subIndex < 0) {
            subIndex = index == 0 ? 0 : masks.get(--index).length;
        } else if (e.getKeyChar() == 'L') {
            index = Math.min(masks.size() - 1, index + 1);
            subIndex = 0;
        } else if (e.getKeyChar() == 'H') {
            index = Math.max(0, index - 1);
            subIndex = 0;
        }
    }

    @Override
    public void draw(final Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(Constants.LABEL_FONT);
        g.drawString(labels.get(index), 50, 890);

        g.setColor(Color.RED);
        long current = 1l;
        long currentMask = masks.get(index)[subIndex];
        StringBuilder builder = new StringBuilder();
        builder.append(labels.get(index));
        builder.append(": ");
        builder.append(subIndex);
        logger.logBitBoard(builder.toString(), currentMask);

        for (int i = 0; i < 64; i++) {
            int x = 7 - i % 8, y = 7 - i / 8;
            if ((current & currentMask) != 0) {
                g.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
            current = current << 1;
        }
        g.setColor(Color.BLUE);
        int x = 7 - subIndex % 8, y = 7 - subIndex / 8;
        g.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

}
