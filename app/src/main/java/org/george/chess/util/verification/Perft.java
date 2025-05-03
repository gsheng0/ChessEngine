package org.george.chess.util.verification;

import java.util.List;

import org.george.chess.model.Move;
import org.george.chess.model.Position;
import org.george.chess.service.MoveGenerator;
import org.george.chess.util.Logger;

import static org.george.chess.util.Constants.*;

public class Perft {
    private final Position position;
    private int count = 0;
    private final MoveGenerator moveGenerator;
    private static final Logger logger = Logger.of(Perft.class);

    public Perft(final Position position){
        this.position = position;
        this.moveGenerator = new MoveGenerator();
    }

    public Perft(){
        this(Position.START_POSITION);
    }

    public int run(final int depth){
        count = 0;
        search(depth, WHITE);
        return count;
    }

    private void search(final int depth, final int side){
        if(depth == 0){
            count++;
            return;
        }
        List<Move> moves = moveGenerator.generateMoves(position, side);
        for(final Move move : moves){
                logger.log(move.toString());
            position.apply(move);
            search(depth - 1, 1 - side);
            position.unapply(move);
        }

    }
}
