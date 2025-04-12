package org.george.chess.util.window;

import java.util.List;

import org.george.chess.util.BitBoard;
import org.george.chess.model.Move;
import org.george.chess.model.Position;
import org.george.chess.util.Logger;
import static org.george.chess.util.Constants.*;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.KeyEvent;

public class UCIMoveContentHandler implements ContentHandler<String>{
    private int index;
    private List<Position> positions;
    private static final Logger<UCIMoveContentHandler> logger = Logger.of(UCIMoveContentHandler.class);

    public UCIMoveContentHandler(final List<String> moves){
        this();
        for(String move : moves){
            this.acceptContent(move);
        }
    }

    public UCIMoveContentHandler(){
        positions = new ArrayList<>(List.of(Position.START_POSITION));
    }

    @Override
    public boolean acceptContent(final String uciMove){
        Position copy = positions.getLast().copy();
        Move move = Move.parseUCIString(copy, uciMove);
        if(index == positions.size() - 1){
            index++;
        }
        copy.apply(move);
        positions.add(copy);
        return true;
    }

    @Override
    public boolean handleKeyPressed(final KeyEvent e){
        if(e.getKeyChar() == 'l'){
            index = Math.min(positions.size() - 1, index + 1);
        } else if(e.getKeyChar() == 'h'){
            index = Math.max(0, index - 1);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void draw(final Graphics g){
        BitBoard.drawBitBoard(g, positions.get(index), null);
    }

}
