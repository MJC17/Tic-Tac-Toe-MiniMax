import java.util.Random;

public class TTTMinimaxBot {

	private final char computerPlayer;
	private final char opponent;
	private final int predictionDepth;

	TTTMinimaxBot(char humanPlayer, char computerPlayer, int predictionDepth) {

		// init variables
		this.computerPlayer = computerPlayer;
		this.opponent = humanPlayer;
		this.predictionDepth = predictionDepth;

	}

	char[][] makeMove(char[][] board) {

		MinimaxMove[] possibleNextMoves = new MinimaxMove[9]; // array that holds the best possible moves
		int highestPredictionScore = -1;
		int nextMovesCount = 0;

		// looping through the possible positions
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (board[x][y] == ' ') { // checking if the position is free

					// Mini-Max algorithm making prediction of available position
					MinimaxMove currentPossibleMove = new MinimaxMove(computerPlayer, opponent);
					currentPossibleMove.movePrediction(board, x, y, predictionDepth);

					if (currentPossibleMove.getScore() > highestPredictionScore) { // check if the position is the new best position
						highestPredictionScore = currentPossibleMove.getScore();
						nextMovesCount = 1;

						possibleNextMoves = new MinimaxMove[8]; // resetting the array due to a new best position
						possibleNextMoves[nextMovesCount - 1] = currentPossibleMove;

					} else if (currentPossibleMove.getScore() == highestPredictionScore) {
						// check if the position is a equally as good as the best
						nextMovesCount += 1;
						possibleNextMoves[nextMovesCount - 1] = currentPossibleMove;

					}

				}
			}
		}

		return possibleNextMoves[new Random().nextInt(nextMovesCount)].getMove(); // picking a move from the best possible moves
	}
}
