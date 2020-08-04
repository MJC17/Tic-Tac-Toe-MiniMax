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

		// set the panel layout (this is only for positioning the reset/new game button)
		setLayout(new GridLayout(1, 1));

		// setting up the window for the game
		JFrame frame = new JFrame();
		frame.setSize(width + 30, height + 90);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this); // adding the panel to the window
		frame.setTitle("TicTacToe Game with Minimax and Blocking Algorithm");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.addMouseListener( // registering the mouse clicks in the window
				new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						super.mousePressed(e);
						playerMove(e.getPoint());
					}
				});

		// setting up the restart/new game button
		resetBtn = new JButton();
		resetBtn.setBounds(290, 530, 235, 40);
		resetBtn.setAction(new AbstractAction() { // the button click action
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		resetBtn.setText("Reset Game");
		resetBtn.setFont(new Font("Helvetica", Font.BOLD, 20));
		resetBtn.setBorderPainted(true);
		resetBtn.setFocusPainted(false);
		add(resetBtn); //adding the reset button to the panel

		newGame(); // init the games variables
	}

	public static void main(String[] args) {
		new TicTacToe(); // starting the game
	}


	private void newGame() {

		// init the new game (resetting the board/game)
		board = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
		gameStatus = null;
		numMoves = 0;

		bot = new TTTMinimaxBot(player1, player2, 2); // init the computer player
		currentPlayer = player1; // setting player 1 to the first player

		paint(getGraphics()); // painting the board to the window
	}

	private void playerMove(Point clickedPoint) {

		// calculating the position on the board that was clicked
		int x = (clickedPoint.x - 15 - ((clickedPoint.x - 15) % (width / 3))) / (width / 3);
		int y = (clickedPoint.y - 55 - ((clickedPoint.y - 55) % (height / 3))) / (height / 3);

		try {


			if (board[x][y] == ' ' && gameStatus == null) { // checking if the player can play
				currentPlayer = player1;
				board[x][y] = currentPlayer; // setting human players move
				numMoves += 1;

				checkGameStatus(); // checking the status of the current game

				if (gameStatus == null) { // checking if the computer player can play
					currentPlayer = player2;
					board = bot.makeMove(board); // computer player making its move
					numMoves += 1;

					checkGameStatus();// checking the status of the game
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}

		paint(getGraphics()); // painting the board to the window
	}

	void checkGameStatus() {

		// findinngthe status of the game

		if (isWinner()) {
			gameStatus = currentPlayer + " is the winner";

		} else if (numMoves == 9) {
			gameStatus = "tied Game";
		} else {
			gameStatus = null; // no important status
		}
	}


	private boolean isWinner() {

		// looping for colum and row,checking for winner
		for (int i = 0; i < 3; i++) {
			if ((((board[0][i] == board[1][i] && board[1][i] == board[2][i])) || (board[i][0] == board[i][1] && board[i][1] == board[i][2])) && board[i][i] != ' ') {
				return true;
			}
		}

		// check for winner in diagonal rows
		if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) && board[1][1] != ' ') {
			return true;
		} else if ((board[0][2] == board[1][1] && board[1][1] == board[2][0]) && board[1][1] != ' ') {
			return true;
		}

		return false; // if theres no winner
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.translate(15, 10);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5)); // setting the thickness of the lines

		// drawing the board lines in window
		for (int i = 0; i < 4; i++) {
			g.drawLine((height / 3) * i, 0, (height / 3) * i, height);
			g.drawLine(0, (width / 3) * i, width, (width / 3) * i);
		}

		// looping through the board positions
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				g2.setStroke(new BasicStroke(10)); // setting like thickness of the symbols

				try {
					if (board[x][y] == 'X') { // drawing the "X" symbol

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
					} else if (board[x][y] == 'O') { // drawing the "O" symbol

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
			// displaying the game status to the window
			g.setFont(new Font("Helvetica", Font.BOLD, 20));
			g.drawString(gameStatus.toUpperCase(), 10, 545);
		} catch (NullPointerException ignored) {
			return;
		}
	}
}
