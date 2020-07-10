import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class TicTacToe extends JPanel {

	char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
	char currentPlayer;
	char player1 = 'X';
	char player2 = 'O';
	int numMoves = 0;
	int width = 510;
	int height = 510;
	boolean isTwoPlayer = false;
	JButton resetBtn;
	JLabel lbl;
	TTTMinimaxBot bot;

	private TicTacToe() {

		resetBtn = new JButton("Reset Game");
		resetBtn.setPreferredSize(new Dimension(200, 40));
		//    resetBtn.tran
		resetBtn.setAction(
				new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						newGame();
					}
				});

		this.add(resetBtn, BorderLayout.CENTER);

		JFrame frame = new JFrame();
		frame.setSize(width + 30, height + 80);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		//    frame.add(resetBtn);
		frame.setTitle("TicTacToe Game");
		frame.setVisible(true);
		frame.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						super.mousePressed(e);
						playerMove(e.getLocationOnScreen());
					}
				});

		newGame();
	}

	public static void main(String[] args) {
		new TicTacToe();
	}

	private void newGame() {

		board = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
		numMoves = 0;

		if (isTwoPlayer) {

			if (new Random().nextBoolean()) {
				currentPlayer = player1;

			} else {
				currentPlayer = player2;
			}

		} else {
			bot = new TTTMinimaxBot(player1, player2, 4);
			currentPlayer = player1;
		}

		paint(getGraphics());
	}

	private void playerMove(Point clickedPoint) {

		int x = (clickedPoint.x - 15 - ((clickedPoint.x - 15) % (width / 3))) / (width / 3);
		int y = (clickedPoint.y - 55 - ((clickedPoint.y - 55) % (height / 3))) / (height / 3);

		try {

			if (board[x][y] == ' ' && !isWinner()) {
				numMoves += 1;
				board[x][y] = currentPlayer;
				this.paint(this.getGraphics());

				if (isWinner() && numMoves <= 9) {
					System.out.println(currentPlayer + " has won the game");

				} else if (numMoves == 9) {
					System.out.println("Tied game");

				} else {

					if (currentPlayer == player1 && isTwoPlayer) {
						currentPlayer = player2;

					} else if (currentPlayer == player2 && isTwoPlayer) {
						currentPlayer = player1;

					} else if (currentPlayer == player1 && !isTwoPlayer) {
						currentPlayer = player2;
						board = bot.makeMove(board);
						numMoves += 1;

						if (isWinner() && numMoves <= 9) {
							System.out.println(currentPlayer + " has won the game");

						} else if (numMoves == 9) {
							System.out.println("Tied game");

						} else {
							currentPlayer = player1;

						}
					}
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}

		paint(getGraphics());
	}

	private boolean isWinner() {

		for (int i = 0; i < 3; i++) {
			if ((((board[0][i] == board[1][i] && board[1][i] == board[2][i])) || (board[i][0] == board[i][1] && board[i][1] == board[i][2])) && board[i][i] != ' ') {
				return true;
			}
		}

		return ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2] == board[1][1] && board[1][1] == board[2][0])) && board[1][1] != ' ';
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

				if (board[x][y] == player1) {

					g.drawLine(
							x * (width / 3) + 40,
							y * (height / 3) + 40,
							x * (width / 3) - 40 + (width / 3),
							y * (height / 3) - 40 + (height / 3));
					g.drawLine(
							x * (width / 3) + 40,
							y * (height / 3) - 40 + (height / 3),
							x * (width / 3) - 40 + (width / 3),
							y * (height / 3) + 40);

				} else if (board[x][y] == player2) {

					g.drawOval(
							x * (width / 3) + 20,
							y * (height / 3) + 20,
							width / 3 - 40,
							height / 3 - 40);
				}
			}
		}
	}
}
