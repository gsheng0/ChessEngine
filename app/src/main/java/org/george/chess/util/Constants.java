package org.george.chess.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import java.io.File;

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
    public static final long[] PAWN_START_RANKS = { FIRST_RANK << 8, EIGHTH_RANK >>> 8};
    public static final long[] EN_PASSANT_RANK = { EIGHTH_RANK >>> 24, FIRST_RANK << 24 };
    public static final long[] KING_SIDE_CASTLE_SELECTORS = { 
        6l,
        6l << (8 * 7)
    };
    public static final long[] QUEEN_SIDE_CASTLE_SELECTORS = {
        1l << 6 | 1l << 5 | 1l << 4,
        1l << 62 | 1l << 61 | 1l << 60
    };

    public static final long EDGES = A_FILE | H_FILE | FIRST_RANK | EIGHTH_RANK;

    // Move generation arrays
    // Pawn
    public static final int[] PAWN_SHIFTS = { 8, -8 };
    public static final long[] PAWN_PRUNES = { ~EIGHTH_RANK, ~FIRST_RANK };
    public static final long[] STARTING_RANK = { FIRST_RANK << 8, EIGHTH_RANK >>> 8 };
    public static final int[][] PAWN_ATTACKS = {
        { 7, 9 },
        { -9, -7 },
    };
    public static final long[][] PAWN_ATTACK_PRUNES = {
        { ~EIGHTH_RANK & ~H_FILE, ~EIGHTH_RANK & ~A_FILE },
        { ~FIRST_RANK & ~H_FILE, ~FIRST_RANK & ~A_FILE },
    };

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

    public static final int FILE = 0;
    public static final int RANK = 1;

    public static final int LEFT_MOUSE_BUTTON = MouseEvent.BUTTON1;
    public static final int RIGHT_MOUSE_BUTTON = MouseEvent.BUTTON3;

    //Piece Images
    public static Image[][] PIECE_IMAGES = new Image[2][KING + 1];

    static {
        // final String IMAGE_PATH_PREFIX = "C:\\Users\\George\\proj\\chess\\app\\src\\main\\resources\\";
        final String IMAGE_PATH_PREFIX = "/Users/georgesheng/proj/ChessEngine/app/src/main/resources/";
        PIECE_IMAGES[WHITE][PAWN] = getImage(IMAGE_PATH_PREFIX + "white_pawn.png");
        PIECE_IMAGES[WHITE][KNIGHT] = getImage(IMAGE_PATH_PREFIX + "white_knight.png");
        PIECE_IMAGES[WHITE][BISHOP] = getImage(IMAGE_PATH_PREFIX + "white_bishop.png");
        PIECE_IMAGES[WHITE][ROOK] = getImage(IMAGE_PATH_PREFIX + "white_rook.png");
        PIECE_IMAGES[WHITE][QUEEN] = getImage(IMAGE_PATH_PREFIX + "white_queen.png");
        PIECE_IMAGES[WHITE][KING] = getImage(IMAGE_PATH_PREFIX + "white_king.png");

        PIECE_IMAGES[BLACK][PAWN] = getImage(IMAGE_PATH_PREFIX + "black_pawn.png");
        PIECE_IMAGES[BLACK][KNIGHT] = getImage(IMAGE_PATH_PREFIX + "black_knight.png");
        PIECE_IMAGES[BLACK][BISHOP] = getImage(IMAGE_PATH_PREFIX + "black_bishop.png");
        PIECE_IMAGES[BLACK][ROOK] = getImage(IMAGE_PATH_PREFIX + "black_rook.png");
        PIECE_IMAGES[BLACK][QUEEN] = getImage(IMAGE_PATH_PREFIX + "black_queen.png");
        PIECE_IMAGES[BLACK][KING] = getImage(IMAGE_PATH_PREFIX + "black_king.png");
        System.out.println("Done loading piece images");
    }

    private static Image getImage(String path){
        File file = new File(path);
        try{
            return ImageIO.read(file);
        } catch(Exception e){
            throw new RuntimeException("Error retrieving image with path: " + path);
        } 

    }
}

