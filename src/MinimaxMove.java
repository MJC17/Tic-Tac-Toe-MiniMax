import java.util.Arrays;

public class MinimaxMove {

	private final char[][] currentMove = new char[3][3];

	char computerPlayer;
	char opponent;
	int predictionDepth;
	private int predictionScore = 0;

	MinimaxMove(char computerPlayer, char humanPlayer, int predictionDepth) {

		this.computerPlayer = computerPlayer;
		this.opponent = humanPlayer;
		this.predictionDepth = predictionDepth;
		this.predictionScore = predictionDepth;


	}

	void MovePerdiction(char[][] currentPossibleMove, int x, int y) {

		System.out.println("new prediction move");
		this.moveTesting(currentPossibleMove, x, y, computerPlayer);

		if (isWinner(currentMove)) {

			predictionScore += 1;

		} else if (predictionScore != 0) {

//			System.out.println(Arrays.deepToString(currentMove));
			System.out.println("\nlevel 2");
			opponentTurn(currentMove);

		}
	}

	void moveTesting(char[][] currentBoard, int x, int y, char currentPlayer) {

		for (int i = 0; i < 3; i++) {
			System.arraycopy(currentBoard[i], 0, this.currentMove[i], 0, 3);
		}

		this.currentMove[x][y] = currentPlayer;

	}

	void playerTurn(char[][] testingBoard) {
//		predictionScore -= 1;

		System.out.println("level 3");

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (testingBoard[x][y] == ' ') {


//					MinimaxMove currentTestingMove = new MinimaxMove('X', 'O', this.predictionDepth);
//					currentTestingMove.moveTesting(testingBoard, x, y, 'x');
//
//
//					if (isWinner(currentTestingMove.getCurrentMove())) {
//
//						predictionScore += 1;
//
//					} else if (predictionScore == 0) {
////						playerTurn(currentTestingMove.getCurrentMove());
//
////						System.out.println(Arrays.deepToString(currentTestingMove.getCurrentMove()));
//
//					}


				}
			}
		}


	}


	void opponentTurn(char[][] testingBoard) {

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (testingBoard[x][y] == ' ') {


					MinimaxMove currentTestingMove = new MinimaxMove(opponent, computerPlayer, this.predictionDepth);
					currentTestingMove.moveTesting(testingBoard, x, y, opponent);
					System.out.println(Arrays.deepToString(currentTestingMove.getMove()));


//					if (isWinner(currentTestingMove.getCurrentMove())) {
//
////						predictionScore = 0;
//
//					} else if (predictionScore >= 0) {
////						playerTurn(currentTestingMove.getCurrentMove());
//
////						System.out.println(Arrays.deepToString(currentTestingMove.getCurrentMove()));
//
//					}


				}
			}
		}

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

	public char[][] getMove() {
		return currentMove;
	}
}
