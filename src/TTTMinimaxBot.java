import java.util.Random;

public class TTTMinimaxBot {

	char player;
	char opponent;
	int predictionDepth;

	TTTMinimaxBot(char player, char opponent, int predictionDepth) {

		this.player = player;
		this.opponent = opponent;
		this.predictionDepth = predictionDepth;
	}

	char[][] makeMove(char[][] board) {

		MinimaxMove[] possibleMoves = new MinimaxMove[8];
		int highestPredictionScore = 0;
		int movesCount = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				if (board[x][y] == ' ') {

					MinimaxMove currentPossibleMove = new MinimaxMove(player, opponent, this.predictionDepth);
					currentPossibleMove.MovePerdiction(board, x, y);

					if (currentPossibleMove.getScore() > highestPredictionScore) {
						highestPredictionScore = currentPossibleMove.getScore();
						movesCount = 1;
						possibleMoves = new MinimaxMove[8];
						possibleMoves[movesCount - 1] = currentPossibleMove;

					} else if (currentPossibleMove.getScore() == highestPredictionScore) {
						movesCount += 1;
						possibleMoves[movesCount - 1] = currentPossibleMove;

					}

				}
			}
		}

		return possibleMoves[new Random().nextInt(movesCount)].getCurrentMove();
	}
}
