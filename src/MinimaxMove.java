import java.util.Arrays;

public class MinimaxMove {

	private final char[][] currentMove = new char[3][3];
	char player;
	char opponent;
	char currentPlayer;
	int predictionDepth;
	private int predictionScore = 0;

	MinimaxMove(char player, char opponent, int predictionDepth) {

		this.player = player;
		this.opponent = opponent;
		this.predictionDepth = predictionDepth;
		this.predictionScore = predictionDepth;

	}

	void MovePerdiction(char[][] currentBoard, int x, int y) {

		System.out.println("new prediction move");
		moveTesting(currentBoard, x, y, player);

		if (isWinner(currentMove)) {

			predictionScore += 1;

		} else if (predictionScore != 0) {

			opponentTurn(currentBoard);

		}
	}

	void moveTesting(char[][] currentBoard, int x, int y, char currentPlayer) {

		for (int i = 0; i < 3; i++) {
			System.arraycopy(currentBoard[i], 0, currentMove[i], 0, 3);
		}

		currentMove[x][y] = currentPlayer;

	}

	void playerTurn(char[][] testingBoard) {
		predictionScore -= 1;

	}


	void opponentTurn(char[][] testingBoard) {


		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (testingBoard[x][y] == ' ') {


					MinimaxMove currentTestingMove = new MinimaxMove('O', 'X', this.predictionDepth);
					currentTestingMove.moveTesting(testingBoard, x, y, 'O');
					System.out.println(Arrays.deepToString(currentTestingMove.getCurrentMove()));


					if (isWinner(currentTestingMove.getCurrentMove())) {

						predictionScore = 0;

					} else if (predictionScore == 0) {
//						playerTurn(currentTestingMove.getCurrentMove());
//						System.out.print(currentTestingMove.getScore());
//						System.out.println(Arrays.deepToString(currentTestingMove.getCurrentMove()));

					}


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

	public char[][] getCurrentMove() {
		return currentMove;
	}
}
