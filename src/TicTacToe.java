import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TicTacToe extends JPanel {

	char[][] board;
	char currentPlayer;
	char player1 = 'X';
	char player2 = 'O';
	int numMoves = 0;
	int width = 510;
	int height = 510;
	String gameStatus;
	JButton resetBtn;
	TTTMinimaxBot bot;

	private TicTacToe() {

		setLayout(new GridLayout(2, 1));

		JFrame frame = new JFrame();
		frame.setSize(width + 30, height + 90);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setTitle("TicTacToe Game with Minimax and Blocking Algroithm");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						super.mousePressed(e);
						playerMove(e.getPoint());
					}
				});

		resetBtn = new JButton();
		resetBtn.setBounds(290, 530, 235, 40);
		resetBtn.setAction(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		resetBtn.setText("Reset Game");
		resetBtn.setFont(new Font("Helvetica", Font.BOLD, 20));
		resetBtn.setBorderPainted(true);
		resetBtn.setFocusPainted(false);
		add(resetBtn);

		newGame();
	}

	public static void main(String[] args) {
		new TicTacToe(); // starting the game
	}


	private void newGame() {

		// init the new game (resetting the board/game)
		board = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
		gameStatus = null;
		numMoves = 0;

		bot = new TTTMinimaxBot(player1, player2, 2);
		currentPlayer = player1;

		paint(getGraphics());
	}

	private void playerMove(Point clickedPoint) {

		int x = (clickedPoint.x - 15 - ((clickedPoint.x - 15) % (width / 3))) / (width / 3);
		int y = (clickedPoint.y - 55 - ((clickedPoint.y - 55) % (height / 3))) / (height / 3);

		try {

			if (board[x][y] == ' ' && gameStatus == null) {
				currentPlayer = player1;
				numMoves += 1;
				board[x][y] = currentPlayer;
				checkGameStatus();

				if (gameStatus == null) {
					board = bot.makeMove(board);
					currentPlayer = player2;
					numMoves += 1;
					checkGameStatus();
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}

		paint(getGraphics());
	}

	void checkGameStatus() {

		if (isWinner()) {
			gameStatus = currentPlayer + " is the winner";

		} else if (numMoves == 9) {
			gameStatus = "tied Game";
		} else {
			gameStatus = null;
		}
	}


	private boolean isWinner() {
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

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.translate(15, 10);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));

		for (int i = 0; i < 4; i++) {
			g.drawLine((height / 3) * i, 0, (height / 3) * i, height);
			g.drawLine(0, (width / 3) * i, width, (width / 3) * i);
		}

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				g2.setStroke(new BasicStroke(10));

				try {
					if (board[x][y] == player1) {

						g2.drawLine(
								x * (width / 3) + 40,
								y * (height / 3) + 40,
								x * (width / 3) - 40 + (width / 3),
								y * (height / 3) - 40 + (height / 3));
						g2.drawLine(
								x * (width / 3) + 40,
								y * (height / 3) - 40 + (height / 3),
								x * (width / 3) - 40 + (width / 3),
								y * (height / 3) + 40);

					} else if (board[x][y] == player2) {

						g2.drawOval(
								x * (width / 3) + 20,
								y * (height / 3) + 20,
								width / 3 - 40,
								height / 3 - 40);
					}
				} catch (NullPointerException ignored) {
					return;
				}
			}
		}

		try {
			g.setFont(new Font("Helvetica", Font.BOLD, 20));
			g.drawString(gameStatus.toUpperCase(), 10, 545);
		} catch (NullPointerException ignored) {
			return;
		}
	}
}
