package org.george.chess.util.window;

import java.util.List;

import org.george.chess.util.BitBoard;
import org.george.chess.model.Move;
import org.george.chess.model.Position;
import org.george.chess.service.MoveGenerator;
import org.george.chess.util.Logger;
import static org.george.chess.util.Constants.*;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public class UCIMoveContentHandler implements ContentHandler<String>{
    private int index;
    private List<Position> positions;
    private static final Logger<UCIMoveContentHandler> logger = Logger.of(UCIMoveContentHandler.class);
    private int tile = -1;
    private MoveGenerator moveGenerator;

    public UCIMoveContentHandler(final List<String> moves){
        this();
        moveGenerator = new MoveGenerator();
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
    public boolean handleMousePressed(final MouseEvent e){
        final Point point = e.getPoint();
        final int x = point.x - HORIZONTAL_SHIFT, y = point.y - VERTICAL_SHIFT;
        tile = 63 - (x / SQUARE_SIZE + 8 * (y / SQUARE_SIZE));
        
        return true;
    }

    @Override
    public void draw(final Graphics g){
        if(tile != -1){
            long[] all = new long[2];
            long[][] pieces= positions.get(index).getPieces();
            for(int s = WHITE; s <= BLACK; s++){
                for(int p = PAWN; p <= KING; p++){
                    all[s] |= pieces[s][p];
                }
            }
            List<Move> moves = moveGenerator.generateMovesForTile(positions.get(index), tile, all);
            g.setColor(Color.RED);
            for(Move move : moves){
                int to = (int)move.to();
                int x = SQUARE_SIZE * (7 - to % 8);
                int y = SQUARE_SIZE * (7 - to / 8);
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        BitBoard.drawBitBoard(g, positions.get(index), null);
    }

}
