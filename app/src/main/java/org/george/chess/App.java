package org.george.chess;

import static org.george.chess.util.Constants.*;

import org.george.chess.movegenerator.MoveGenerator;
import org.george.chess.util.Generator;
import org.george.chess.util.Logger;
import org.george.chess.util.BitBoard;
import org.george.chess.util.stockfish.StockfishRunner;
import org.george.chess.util.window.BitMaskContentHandler;
import org.george.chess.util.window.ContentHandler;
import org.george.chess.util.window.UCIMoveContentHandler;
import org.george.chess.util.window.Window;
import org.george.chess.model.Position;
import org.george.chess.model.Move;

import java.util.List;
import java.util.ArrayList;

public class App {

        private static final Logger<App> logger = Logger.of(App.class);
        private static final List<String> EVANS_GAMBIT = List.of("e2e4", "e7e5", "g1f3", "b8c6", "f1c4", "f8c5", "b2b4");
        private static final List<String> OPEN_SICILIAN_OPPOSITE_SIDE_CASTLE = List.of();
        private static final List<String> FRIED_LIVER = List.of("e2e4", "e7e5", "g1f3", "b8c6", "f1c4", "g8f6", "f3g5", "d7d5", "e4d5", "f6d5", "g5f7");

        public static void main(String[] args) {
                final List<String> moves = new ArrayList<>(FRIED_LIVER);
                final UCIMoveContentHandler contentHandler = new UCIMoveContentHandler(moves);
                final Window<String> window = new Window<String>(contentHandler);
                final StockfishRunner stockfish = StockfishRunner.get();
                stockfish.setThreads(9);
                while(true){
                        String move = stockfish.getBestMove(moves);
                        if(move.length() < 1){
                                break;
                        }
                        moves.add(move);
                        window.acceptContent(move);
                        System.out.println(move);
                }
                System.out.println("Game done: ");
                System.out.println(moves);
                
        }
}
