import java.util.List;

public class ChessGameDesign {

    ChessBoard chessBoard;
    Player[] player;
    Player currentPlayer;
    List<Move> movesList;
    GameStatus gameStatus;

    public boolean playerMove(CellPosition fromPosition, CellPosition toPosition, Piece piece);
    public boolean endGame();
    private void changeTurn();

}

class Player {

    Account account;
    Color color;
    Time timeLeft;

}

class Time {

    int mins;
    int secs;

}

enum Color {

    BLACK, WHITE;

}

class Account {

    String username;
    String password;

    String name;
    String email;
    String phone;
}

enum GameStatus {

    ACTIVE, PAUSED, FORTFEIGHT, BLACK_WIN, WHITE_WIN;
}

class ChessBoard {

    List<List<Cell>> board;

    public void resetBoard();
    public void updateBoard(Move move);
}

class Cell {

    Color color;
    Piece piece;
    CellPosition position;
}

class CellPosition {

    Char ch;
    int i;
}

class Move {

    Player turn;
    Piece piece;
    Piece killedPiece;
    CellPosition startPosition;
    CellPosition endPosition;

}

abstract class Piece {

    Color color;

    public boolean move(CellPosition fromPosition, CellPosition toPosition);
    public List<CellPosition> possibleMoves(CellPosition fromPosition);
    public boolean validate(CellPosition fromPosition, CellPosition toPosition);
}

class Knight extends Piece {

    public boolean move(CellPosition fromPosition, CellPosition toPosition);
    public List<CellPosition> possibleMoves(CellPosition fromPosition);
    public boolean validate(CellPosition fromPosition, CellPosition toPosition);

}

class Bishop extends Piece {

    public boolean move(CellPosition fromPosition, CellPosition toPosition);
    public List<CellPosition> possibleMoves(CellPosition fromPosition);
    public boolean validate(CellPosition fromPosition, CellPosition toPosition);

}

class rook extends Piece {

    public boolean move(CellPosition fromPosition, CellPosition toPosition);
    public List<CellPosition> possibleMoves(CellPosition fromPosition);
    public boolean validate(CellPosition fromPosition, CellPosition toPosition);

}

class King extends Piece {

    public boolean move(CellPosition fromPosition, CellPosition toPosition);
    public List<CellPosition> possibleMoves(CellPosition fromPosition);
    public boolean validate(CellPosition fromPosition, CellPosition toPosition);

}

class Queen extends Piece {

    public boolean move(CellPosition fromPosition, CellPosition toPosition);
    public List<CellPosition> possibleMoves(CellPosition fromPosition);
    public boolean validate(CellPosition fromPosition, CellPosition toPosition);

}

class Pawn extends Piece {

    public boolean move(CellPosition fromPosition, CellPosition toPosition);
    public List<CellPosition> possibleMoves(CellPosition fromPosition);
    public boolean validate(CellPosition fromPosition, CellPosition toPosition);

}
