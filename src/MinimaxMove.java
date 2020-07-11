public class MinimaxMove {

	private final char[][] currentMove = new char[3][3];

	char computerPlayer;
	char opponent;
	private int predictionScore = 0;

	MinimaxMove(char computerPlayer, char humanPlayer) {

		this.computerPlayer = computerPlayer;
		this.opponent = humanPlayer;
		this.predictionScore = 0;

	}


	void movePrediction(char[][] currentPossibleMove, int x, int y, int predictionDepth) {

		moveTesting(currentPossibleMove, x, y, this.computerPlayer);
//		System.out.println(Arrays.deepToString(currentMove));
//		System.out.println("level 1");

		if (isWinner(this.currentMove) && predictionDepth >= 0) {
			this.predictionScore = 1;

		} else if (predictionDepth == 0) {
			this.predictionScore = 0;

		} else {
			this.predictionScore = opponentTurn(this.currentMove, predictionDepth - 1);
		}
	}

	void moveTesting(char[][] currentBoard, int x, int y, char currentPlayer) {

		for (int i = 0; i < 3; i++) {
			System.arraycopy(currentBoard[i], 0, this.currentMove[i], 0, 3);
		}
		this.currentMove[x][y] = currentPlayer;

	}

	int playerTurn(char[][] testingBoard, int predictionDepth) {

		int miniimizScore;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (testingBoard[x][y] == ' ') {


				}
			}
		}

		return 1;

	}


	int opponentTurn(char[][] testingBoard, int predictionDepth) {

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (testingBoard[x][y] == ' ') {

//					System.out.println("level 2");
				}
			}
		}

		return 0;

	}


	private boolean isWinner(char[][] board) {

		for (int i = 0; i < 3; i++) {
			if ((((board[0][i] == board[1][i] && board[1][i] == board[2][i])) || (board[i][0] == board[i][1] && board[i][1] == board[i][2])) && board[i][i] != ' ') {
				return true;

			}
		}

//		for (int i = 0; i < 3; i++) {
//			if ((board[0][0] == board[1][1] && board[1][1] == board[2][2])  && board[1][1] != ' ') {
//
//			}
//		}

		return ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
		        (board[0][2] == board[1][1] && board[1][1] == board[2][0])) && board[1][1] != ' ';
	}

	public int getScore() {
		return predictionScore;
	}

	public char[][] getMove() {
		return currentMove;
	}
}
