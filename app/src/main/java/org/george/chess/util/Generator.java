package org.george.chess.util;

import static org.george.chess.util.Constants.*;

import org.george.chess.movegenerator.MoveGenerator;

public class Generator {

    private static final MoveGenerator moveGenerator = new MoveGenerator();

    public static long[] generateRookAttackMasks() {
        long[] masks = new long[64];
        long[][] board = new long[2][KING + 1];

        for (int i = 0; i < masks.length; i++) {
            long current = 1l << i;
            board[WHITE][ROOK] = current;
            masks[i] = moveGenerator.generateMoves(board, WHITE)[WHITE][ROOK];
        }
        return masks;
    }

    public static void main(String[] args) {}
}
