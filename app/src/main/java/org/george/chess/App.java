package org.george.chess;

import static org.george.chess.util.Constants.*;

import org.george.chess.service.MoveGenerator;
import org.george.chess.util.Generator;
import org.george.chess.util.Logger;
import org.george.chess.util.BitBoard;
import org.george.chess.util.stockfish.StockfishRunner;
import org.george.chess.util.verification.Perft;
import org.george.chess.util.window.BitMaskContentHandler;
import org.george.chess.util.window.ContentHandler;
import org.george.chess.util.window.UCIMoveContentHandler;
import org.george.chess.util.window.Window;
import org.george.chess.model.Position;
import org.george.chess.model.Move;

import java.util.List;
import java.util.ArrayList;

//TODO: Allow user to setup position manually
public class App {

        private static final Logger<App> logger = Logger.of(App.class);
        private static final List<String> EVANS_GAMBIT = List.of("e2e4", "e7e5", "g1f3", "b8c6", "f1c4", "f8c5", "b2b4");
        private static final List<String> OPEN_SICILIAN_OPPOSITE_SIDE_CASTLE = List.of();
        private static final List<String> FRIED_LIVER = List.of("e2e4", "e7e5", "g1f3", "b8c6", "f1c4", "g8f6", "f3g5", "d7d5", "e4d5", "f6d5", "g5f7");
        private static final List<String> KING_SIDE_FRENCH_DEFENSE = List.of("e2e4", "d7d6", "d2d4", "e7e5");
        private static final List<String> FRENCH_DEFENSE = List.of("e2e4", "e7e6", "d2d4", "d7d5", "e4e5");
        private static final List<String> EXTRA_MOVES_FOR_WHITE = List.of("d2d4", "b8c6", "c2c4", "c6b8");
        private static final List<String> REVERSE_STAFFORD = List.of("e2e4", "e7e5", "g1f3", "g8f6", "f1c4", "f6e4", "b1c3", "e4c3");
        private static final List<String> MY_LINE = List.of("e2e4", "e7e5", "g1f3", "g8f6", "b1c3", "f8c5", "f1c4", "f6g4", "e1g1", "d7d6", "h2h3", "h7h5", "d2d3", "c7c6", "c1g5", "f7f6", "g5h4");
        private static final List<String> LOLLI_GAMBIT = List.of("e2e4", "e7e5", "g1f3", "b8c6", "f1c4", "g8f6", "d2d4");
        private static final List<String> DOUBLE_BONG_CLOUD = List.of("e2e3", "e7e6", "e1e2", "e8e7");
        private static final List<String> EN_PASSANT_TEST = List.of("e2e4", "d7d5", "e4e5", "f7f5");

        public static void main(String[] args) {
                // final List<String> moves = new ArrayList<>(EN_PASSANT_TEST);
                // final UCIMoveContentHandler contentHandler = new UCIMoveContentHandler(moves);
                // final Window window = new Window(contentHandler);
                // final StockfishRunner stockfish = StockfishRunner.get();
                // stockfish.setThreads(4);
                // while(true){
                //         String move = stockfish.getBestMove(moves);
                //         if(move.length() < 1){
                //                 break;
                //         }
                //         moves.add(move);
                //         window.acceptContent(move, UCIMoveContentHandler.class);
                // }
                // System.out.println("Game done: ");
                // System.out.println(moves);
                final Position position = Position.START_POSITION.copy();
                //position.apply(Move.parseUCIString(position, "e2e4"));
                final Perft perft = new Perft(position);

                logger.log("" + perft.run(3));
                
        }
}
