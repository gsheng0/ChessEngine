package org.george.chess;

import static org.george.chess.util.Constants.*;

import org.george.chess.movegenerator.MoveGenerator;
import org.george.chess.util.Generator;
import org.george.chess.util.Logger;
import org.george.chess.util.Window;

public class App {

    private static final Logger<App> logger = Logger.of(App.class);

    public static void main(String[] args) {
        final Window window = new Window();
        long[][] pieces = new long[2][KING + 1];
        long[] masks = Generator.generateAttackMasks(QUEEN);
        for (int i = 0; i < 64; i++) {
            logger.logBitBoard("" + i, masks[i]);
        }
        pieces[WHITE][ROOK] = (long) (Math.pow(2, 8) - 1);

        pieces[BLACK][PAWN] = Long.parseLong(
                "00000000" +
                        "00000000" +
                        "00000000" +
                        "00000010" +
                        "00001000" +
                        "00100000" +
                        "10000000" +
                        "00000000",
                2);

        pieces[WHITE][PAWN] = Long.parseLong(
                "00000000" +
                        "00000000" +
                        "00000001" +
                        "00000100" +
                        "00010000" +
                        "01000000" +
                        "00000000" +
                        "00000000",
                2);

        MoveGenerator generator = new MoveGenerator();
        generator.generateMoves(pieces, WHITE);
    }
}
