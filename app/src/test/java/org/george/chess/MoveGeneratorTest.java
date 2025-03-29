package org.george.chess;

import java.util.List;

import static org.george.chess.util.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.george.chess.movegenerator.MoveGenerator;
import org.george.chess.util.BitBoard;
import org.george.chess.util.BitBoard.BoardBuilder;
import org.junit.jupiter.api.Test;

public class MoveGeneratorTest {
	private MoveGenerator moveGenerator = new MoveGenerator();
	private BoardBuilder boardBuilder = new BoardBuilder();

	@Test
	void BottomRankRooks_AlternatingPawns_Test() {
		boardBuilder.clear()
				.with(WHITE, ROOK, FIRST_RANK)
				.with(WHITE, PAWN, BitBoard.write(List.of(23, 29, 35, 41)))
				.with(BLACK, PAWN, BitBoard.write(List.of(14, 20, 26, 32)));
		// assertEquals(moveGenerator.generateRookMoves(boardBuilder.get()), );

	}

}
