package org.george.chess;

import static org.george.chess.util.Constants.*;

import org.george.chess.movegenerator.MoveGenerator;
import org.george.chess.util.Generator;
import org.george.chess.util.Logger;
import org.george.chess.util.BitBoard;
import org.george.chess.util.stockfish.StockfishRunner;
import org.george.chess.util.window.BitMaskContentHandler;
import org.george.chess.util.window.ChessGameContentHandler;
import org.george.chess.util.window.ContentHandler;
import org.george.chess.util.window.Window;
import org.george.chess.model.Position;
import org.george.chess.model.Move;

import java.util.List;
import java.util.ArrayList;

public class App {

        private static final Logger<App> logger = Logger.of(App.class);

        public static void main(String[] args) {
                final ChessGameContentHandler contentHandler = new ChessGameContentHandler();
                final Window<Move> window = new Window<Move>(contentHandler);
                StockfishRunner stockfish = StockfishRunner.get();
                stockfish.setThreads(10);
                List<String> moves = new ArrayList<>();
                while(true){
                        String move = stockfish.getBestMove(moves);
                        if(move.length() < 1){
                                break;
                        }
                        moves.add(move);
                        window.acceptContent(Move.parseUCIString(contentHandler.getPosition(), move));
                        System.out.println(move);
                }
                System.out.println("Game done: ");
                System.out.println(moves);
        }
}
