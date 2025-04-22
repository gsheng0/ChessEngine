package org.george.chess;

import java.util.List;

import static org.george.chess.util.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.george.chess.util.BitBoard;
import org.george.chess.util.BitBoard.BoardBuilder;
import org.junit.jupiter.api.Test;

public class MoveGeneratorTest {
	private BoardBuilder boardBuilder = new BoardBuilder();
/*
	@Test
	void BottomRankRooks_AlternatingPawns_Test() {
		boardBuilder.clear()
				.with(WHITE, ROOK, FIRST_RANK)
				.with(WHITE, PAWN, BitBoard.write(List.of(23, 29, 35, 41)))
				.with(BLACK, PAWN, BitBoard.write(List.of(14, 20, 26, 32)));
		final long[][] board = boardBuilder.get();
		final long[] all = calculateAllArray(board);
		final long expected = BitBoard.consecutiveOnes(2) << 32 |
				BitBoard.consecutiveOnes(4) << 24 |
				BitBoard.consecutiveOnes(6) << 16 |
				FIRST_RANK << 8;
		// assertEquals(expected, moveGenerator.generateRookMoves(board, all, WHITE));
	}

	@Test
	void TopRankRooks_AlternatingPawns_Test() {

	}

	private long[] calculateAllArray(long[][] pieces) {
		long[] all = new long[2];
		for (int i = 0; i < pieces.length; i++) {
			all[WHITE] |= pieces[WHITE][i];
			all[BLACK] |= pieces[BLACK][i];
		}
		return all;
	}
*/
}
