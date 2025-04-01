package org.george.chess.util;

import java.awt.Color;
import java.awt.Font;

public class Constants {

    // Graphical Constants
    public static final Color DARK_COLOR = Color.decode("#B58862");
    public static final Color LIGHT_COLOR = Color.decode("#F0D9B5");
    public static final int SQUARE_SIZE = 100;
    public static final Font LABEL_FONT = new Font("Times New Roman", Font.PLAIN, 60);
    public static final int HORIZONTAL_SHIFT = 1;
    public static final int VERTICAL_SHIFT = 29;

    // Standard constants
    // Sides
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    // Pieces
    public static final int PAWN = 0;
    public static final int KNIGHT = 1;
    public static final int BISHOP = 2;
    public static final int ROOK = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    public static final String[] PIECE_NAMES = {
        "PAWN", "KNIGHT", "BISHOP", "ROOK", "QUEEN", "KING"
    };

    // Board rows selectors
    public static final long A_FILE = 0x8080808080808080L;
    public static final long H_FILE = 0x0101010101010101L;
    public static final long FIRST_RANK = ~((long) (Math.pow(2, 63)) << 8);
    public static final long EIGHTH_RANK = ~((long) (Math.pow(2, 63)) >>> 7);

    // Move generation arrays
    // Knights
    public static final int[] KNIGHT_SHIFTS = { 17, 15, 10, 6, -6, -10, -15, -17 };
    public static final long[] KNIGHT_PRUNES = {
        ~A_FILE & ~(EIGHTH_RANK >>> 8),
        ~H_FILE & ~(EIGHTH_RANK >>> 8),
        ~EIGHTH_RANK & ~A_FILE & ~(A_FILE >>> 1),
        ~EIGHTH_RANK & ~H_FILE & ~(H_FILE << 1),
        ~FIRST_RANK & ~A_FILE & ~(A_FILE >>> 1),
        ~FIRST_RANK & ~H_FILE & ~(H_FILE << 1),
        ~A_FILE & ~(FIRST_RANK << 8),
        ~H_FILE & ~(FIRST_RANK << 8)
    };
    
    // Bishops
    public static final int[] BISHOP_SHIFTS = { 7, -7, 9, -9 };
    public static final long[] BISHOP_PRUNES = {
        ~EIGHTH_RANK & ~H_FILE,
        ~FIRST_RANK & ~A_FILE,
        ~EIGHTH_RANK & ~A_FILE,
        ~FIRST_RANK & ~H_FILE
    };
    
    // Rooks
    public static final int[] ROOK_SHIFTS = { 8, -8, 1, -1 };
    public static final long[] ROOK_PRUNES = {
        ~EIGHTH_RANK,
        ~FIRST_RANK,
        ~A_FILE,
        ~H_FILE
    };
    
    // Queens
    public static final int[] QUEEN_SHIFTS = { 8, -8, 1, -1, 7, -7, 9, -9 };
    public static final long[] QUEEN_PRUNES = {
        ~EIGHTH_RANK,
        ~FIRST_RANK,
        ~A_FILE,
        ~H_FILE,
        ~EIGHTH_RANK & ~H_FILE,
        ~FIRST_RANK & ~A_FILE,
        ~EIGHTH_RANK & ~A_FILE,
        ~FIRST_RANK & ~H_FILE
    };
    
    // Kings
    public static final int[] KING_SHIFTS = { 8, -8, 1, -1, 7, -7, 9, -9 };
    public static final long[] KING_PRUNES = {
        ~EIGHTH_RANK,
        ~FIRST_RANK,
        ~A_FILE,
        ~H_FILE,
        ~EIGHTH_RANK & ~H_FILE,
        ~FIRST_RANK & ~A_FILE,
        ~EIGHTH_RANK & ~A_FILE,
        ~FIRST_RANK & ~H_FILE
    };

    public static final int[][] SHIFTS = {
        new int[1],
        KNIGHT_SHIFTS,
        BISHOP_SHIFTS,
        ROOK_SHIFTS,
        QUEEN_SHIFTS,
        KING_SHIFTS
    };

    public static final long[][] PRUNES = {
        new long[1],
        KNIGHT_PRUNES,
        BISHOP_PRUNES,
        ROOK_PRUNES,
        QUEEN_PRUNES,
        KING_PRUNES
    };
}

