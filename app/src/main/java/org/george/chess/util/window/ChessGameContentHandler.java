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

//TODO: Add support for going backwards
public class ChessGameContentHandler implements ContentHandler<Move>{
    private List<Move> moves;
    private int index;
    private Position position;
    private static final Logger<ChessGameContentHandler> logger = Logger.of(ChessGameContentHandler.class);
    public ChessGameContentHandler(){
        BitBoard.BoardBuilder builder = new BitBoard.BoardBuilder();
        builder.withAdditionally(WHITE, PAWN, FIRST_RANK << 8);
        builder.withAdditionally(WHITE, KING, 1l << 3);
        builder.withAdditionally(WHITE, QUEEN, 1l << 4);
        builder.withAdditionally(WHITE, KNIGHT, 1l << 1);
        builder.withAdditionally(WHITE, KNIGHT, 1l << 6);
        builder.withAdditionally(WHITE, BISHOP, 1l << 5);
        builder.withAdditionally(WHITE, BISHOP, 1l << 2);
        builder.withAdditionally(WHITE, ROOK, 1l << 7);
        builder.withAdditionally(WHITE, ROOK, 1l);

        builder.withAdditionally(BLACK, PAWN, EIGHTH_RANK >>> 8);
        builder.withAdditionally(BLACK, KING, 1l << 59);
        builder.withAdditionally(BLACK, QUEEN, 1l << 60);
        builder.withAdditionally(BLACK, KNIGHT, 1l << 57);
        builder.withAdditionally(BLACK, KNIGHT, 1l << 62);
        builder.withAdditionally(BLACK, BISHOP, 1l << 61);
        builder.withAdditionally(BLACK, BISHOP, 1l << 58);
        builder.withAdditionally(BLACK, ROOK, 1l << 63);
        builder.withAdditionally(BLACK, ROOK, 1l << 56);
        position = new Position(builder.get(), WHITE, -1, new boolean[2][2]);
        logger.logBitBoard("", builder.get()[WHITE][PAWN]);
        moves = new ArrayList<>();
    }

    @Override
    public void acceptContent(final Move move){
        moves.add(move);
        position.apply(move);
    }

    @Override
    public void acceptContent(final Move move, final String label){
        acceptContent(move);
    }

    @Override
    public void handleKeyPressed(final KeyEvent e){
    
    }

    @Override
    public void draw(final Graphics g){
        BitBoard.drawBitBoard(g, position, null);
    }

    public Position getPosition(){
        return position;
    }

}
