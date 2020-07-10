public class MinimaxMove {

	private final char[][] currentMove = new char[3][3];
	char player;
	char opponent;
	int predictionDepth;
	private int predictionScore = 0;

	MinimaxMove(char player, char opponent, int predictionDepth) {

		this.player = player;
		this.opponent = opponent;
		this.predictionDepth = predictionDepth;
		this.predictionScore = predictionDepth;

	}

	void MovePerdiction(char[][] currentBoard, int x, int y) {

		for (int i = 0; i < 3; i++) {
			System.arraycopy(currentBoard[i], 0, currentMove[i], 0, 3);
		}

		currentMove[x][y] = player;

		if (!isWinner(currentMove) && predictionScore != 0) {


		}
	}

	void playerTurn() {
	}


	void opponentTurn() {

	}


	private boolean isWinner(char[][] board) {

		for (int i = 0; i < 3; i++) {
			if ((((board[0][i] == board[1][i] && board[1][i] == board[2][i])) || (board[i][0] == board[i][1] && board[i][1] == board[i][2])) && board[i][i] != ' ') {
				return true;
			}
		}

		return ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2] == board[1][1] && board[1][1] == board[2][0])) && board[1][1] != ' ';
	}

	public int getScore() {
		return predictionScore;
	}

	public char[][] getCurrentMove() {
		return currentMove;
	}
}
