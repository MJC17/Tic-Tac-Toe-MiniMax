public class MinimaxMove {

	private final char[][] currentMove = new char[3][3];
	char computerPlayer;
	char opponent;
	private int predictionScore = 0;

	MinimaxMove(char computerPlayer, char humanPlayer) {

		// init variables
		this.computerPlayer = computerPlayer;
		this.opponent = humanPlayer;
		this.predictionScore = 0;
	}


	MinimaxMove() { } // init the class

	void movePrediction(char[][] currentPossibleMove, int x, int y, int predictionDepth) {
		// making perdiction of the move based on the current board

		moveTesting(currentPossibleMove, x, y, this.computerPlayer);

		if (predictionDepth > 0) {
			if (isWinner(this.currentMove)) {
				this.predictionScore = 3;
			} else if (canBlock(this.currentMove, x, y)) {
				this.predictionScore = 2;
			} else {
				this.predictionScore = opponentTurn(this.currentMove, predictionDepth - 1);
			}
		}

//		System.out.println(Arrays.deepToString(currentMove) + this.predictionScore);
	}

	void moveTesting(char[][] currentBoard, int x, int y, char currentPlayer) {

		for (int i = 0; i < 3; i++) {
			System.arraycopy(currentBoard[i], 0, this.currentMove[i], 0, 3);
		}

		this.currentMove[x][y] = currentPlayer;
	}

	int playerTurn(char[][] testingBoard, int predictionDepth) {

		int highestScore = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (testingBoard[x][y] == ' ') {

					int currentScore;

					MinimaxMove currentTestingMove = new MinimaxMove();
					currentTestingMove.moveTesting(testingBoard, x, y, computerPlayer);

					if (isWinner(currentTestingMove.getMove()) && predictionDepth >= 0) {
						currentScore = 1;
					} else {
						currentScore = opponentTurn(currentTestingMove.getMove(), predictionDepth - 1);
					}

					highestScore = Math.max(currentScore, highestScore);
				}
			}
		}

		return highestScore;
	}


	int opponentTurn(char[][] testingBoard, int predictionDepth) {

		int lowestScore = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (testingBoard[x][y] == ' ') {

					int currentScore = 0;

					MinimaxMove currentTestingMove = new MinimaxMove();
					currentTestingMove.moveTesting(testingBoard, x, y, opponent);

					if (isWinner(currentTestingMove.getMove())) {
						currentScore = - 1;
					} else if (predictionDepth >= 0) {
						currentScore = playerTurn(currentTestingMove.getMove(), predictionDepth - 1);
					}

					lowestScore = Math.max(currentScore, lowestScore);
				}
			}
		}

		return lowestScore;
	}


	private boolean isWinner(char[][] board) {

		for (int i = 0; i < 3; i++) {
			if ((((board[0][i] == board[1][i] && board[1][i] == board[2][i])) || (board[i][0] == board[i][1] && board[i][1] == board[i][2])) && board[i][i] != ' ') {
				return true;
			}
		}

		if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) && board[1][1] != ' ') {
			return true;
		} else if ((board[0][2] == board[1][1] && board[1][1] == board[2][0]) && board[1][1] != ' ') {
			return true;
		}

		return false;
	}

	private boolean canBlock(char[][] board, int moveX, int moveY) {

		int xCount = 0;
		int yCount = 0;
		for (int i = 0; i < 3; i++) {

			if (board[moveX][i] == opponent) {
				xCount += 1;
			} else if (board[moveX][i] == computerPlayer) {
				yCount += 1;
			}

			if (xCount == 2 && yCount == 1) {
				return true;
			}
		}

		xCount = 0;
		yCount = 0;
		for (int i = 0; i < 3; i++) {

			if (board[i][moveY] == opponent) {
				xCount += 1;
			} else if (board[i][moveY] == computerPlayer) {
				yCount += 1;
			}

			if (xCount == 2 && yCount == 1) {
				return true;
			}
		}

		if (moveX == moveY) {

			xCount = 0;
			yCount = 0;

			for (int i = 0; i < 3; i++) {

				if (board[i][i] == opponent) {
					xCount += 1;
				} else if (board[i][i] == computerPlayer) {
					yCount += 1;
				}

				if (xCount == 2 && yCount == 1) {
					return true;
				}
			}
		}
		if ((moveX == 0 && moveY == 2) || (moveX == 2 && moveY == 0) || (moveX == 1 && moveY == 1)) {


			xCount = 0;
			yCount = 0;

			for (int i = 0; i < 3; i++) {

				if (board[i][2 - i] == opponent) {
					xCount += 1;
				} else if (board[i][2 - i] == computerPlayer) {
					yCount += 1;
				}

				if (xCount == 2 && yCount == 1) {
					return true;
				}
			}
		}

		return false;
	}


	public int getScore() { // get the moves prediction score
		return predictionScore;
	}

	public char[][] getMove() { // getting the move that was scored
		return currentMove;
	}
}
