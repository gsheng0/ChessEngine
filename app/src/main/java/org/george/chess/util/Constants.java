package org.george.chess.util;

import java.awt.Color;
import java.awt.Font;

public class Constants {
    // Graphical constants
    public static final Color DARK_COLOR = Color.decode("#b58862");
    public static final Color LIGHT_COLOR = Color.decode("#f0d9b5");
    public static final int SQUARE_SIZE = 100;
    public static final Font LABEL_FONT = new Font("Times New Roman", Font.PLAIN, 60);

    // Standard constants
    // Sides
    public static final int BLACK = 0;
    public static final int WHITE = 1;

    // Pieces
    public static final int PAWN = 0;
    public static final int KNIGHT = 1;
    public static final int BISHOP = 2;
    public static final int ROOK = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;

    // Board rows
    public static final long A_FILE = 0x8080808080808080L;
    public static final long H_FILE = 0x0101010101010101L;
    public static final long FIRST_RANK = (long) (Math.pow(2, 63)) << 8;
    public static final long EIGHTH_RANK = (long) (Math.pow(2, 63)) >> 8;

    // Move generation arrays
    // pawns
    public static final int[] WHITE_PAWN_SHIFTS = new int[] { 8 };
    public static final int[] BLACK_PAWN_SHIFTS = new int[] { 8 };
    // TODO: ADD PRUNING AND ATTACK ARRAYS

    // bishops
    public static final int[] BISHOP_SHIFTS = new int[] { 9, -9, 7 - 7 };

    // rooks
    public static final int[] ROOK_SHIFTS = new int[] { 8, -8, 1, -1 };
    public static final long[] ROOK_SHIFT_PRUNING = new long[] {
            EIGHTH_RANK,
            FIRST_RANK,
            H_FILE,
            A_FILE
    };
}
