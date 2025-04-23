package org.george.chess.util.verification;

import java.util.List;

import org.george.chess.model.Move;
import org.george.chess.model.Position;
import org.george.chess.service.MoveGenerator;

public class Perft {
    private final Position position;
    private int count = 0;
    private final int depth;
    private final MoveGenerator moveGenerator;
    public Perft(final int depth, final Position position){
        this.position = position;
        this.depth = depth;
        this.moveGenerator = new MoveGenerator();
    }

    public Perft(final int depth){
        this(depth, Position.START_POSITION);
    }

    public void run(){
        if(count != 0){
            return;
        }
        

    }

    private void search(final int depth, final int side){
        if(depth == 0){
            count++;
            return;
        }
        List<Move> moves = moveGenerator.generateMoves(position, side);
        for(final Move move : moves){
            position.apply(move);
            search(depth - 1, 1 - side);
            position.unapply(move);
        }

    }

    public int getCount(){
        return count;
    }

    

}
