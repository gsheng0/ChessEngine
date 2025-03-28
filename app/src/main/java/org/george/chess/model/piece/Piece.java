package org.george.chess.model.piece;

import java.util.List;

public class Piece {
    private boolean repeat;
    private boolean phase;
    private List<Integer> moveShifts;
    private List<Integer> attackShifts;
    private int side;

    public Piece(int side, boolean repeat, List<Integer> moveShifts, List<Integer> attackShifts) {
        this.side = side;
        this.repeat = repeat;
        this.moveShifts = moveShifts;
        this.attackShifts = attackShifts;
        this.phase = false;
    }

    public Piece(int side, boolean repeat, List<Integer> moveShifts) {
        this.side = side;
        this.repeat = repeat;
        this.moveShifts = moveShifts;
        this.attackShifts = moveShifts;
        this.phase = false;
    }

    public Piece(int side, boolean repeat, boolean phase, List<Integer> moveShifts) {
        this.side = side;
        this.repeat = repeat;
        this.moveShifts = moveShifts;
        this.attackShifts = moveShifts;
        this.phase = phase;
    }

    public int getSide() {
        return side;
    }

    public boolean doesRepeat() {
        return repeat;
    }

    public boolean canPhase() {
        return phase;
    }

    public List<Integer> getMoveShifts() {
        return moveShifts;
    }

    public List<Integer> getAttackShifts() {
        return attackShifts;
    }
}
