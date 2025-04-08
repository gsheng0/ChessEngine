package org.george.chess;

import static org.george.chess.util.Constants.*;

import org.george.chess.movegenerator.MoveGenerator;
import org.george.chess.util.Generator;
import org.george.chess.util.Logger;
import org.george.chess.util.window.BitMaskContentHandler;
import org.george.chess.util.window.ContentHandler;
import org.george.chess.util.window.Window;

public class App {

        private static final Logger<App> logger = Logger.of(App.class);

        public static void main(String[] args) {
                for(int i = 1; i <= 20; i++){
                        System.out.println(Integer.toBinaryString(i) + " " + Integer.toBinaryString(i & -i));
                }
                final ContentHandler<long[]> contentHandler = new BitMaskContentHandler();
                for (int i = PAWN; i <= KING; i++) {
                        contentHandler.acceptContent(Generator.generateAttackMasks(i), PIECE_NAMES[i]);
                }
                final Window window = new Window(contentHandler);
        }
}
