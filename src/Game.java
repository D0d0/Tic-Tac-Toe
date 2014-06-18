import java.awt.Color;
import java.awt.Graphics;

public class Game {
	private int[][] gameArea;
	private int size, playerID, maxPlayers, gameId, onlinePlayers;
	private TicTacToe app;
	private Mode mode;
	private String playerName, gameName;
	private WaitingForPlayers wait;
	private OnlineGames onlineGames;
	private WaitingForStart waitingForStart;
	private int size_of_field;
	private int left;
	private int width;

	public Game(TicTacToe app) {
		setApp(app);
		size = 10;
		setMode(Mode.LOGIN);
	}

	public void getGames() {
		onlineGames = new OnlineGames(app);
		onlineGames.start();
	}

	public void stopGettingOnlineGames() {
		onlineGames.interrupt();
		onlineGames = null;
	}

	public void initialize() {
		size_of_field = Math
				.min((int) ((AppletConfig.height - AppletConfig.top - 2 * AppletConfig.left) / size),
						(int) ((AppletConfig.width - 2 * AppletConfig.left) / size));
		width = size_of_field * size;
		left = (AppletConfig.width - width) / 2;
	}

	public void drawGameArea(Graphics g) {
		int bottom = AppletConfig.top + size_of_field * size;
		int right = AppletConfig.width - left;
		g.setColor(Color.white);
		g.fillRect(left, AppletConfig.top, width, width);
		g.setColor(Color.black);
		for (int i = 0; i <= size; i++) {
			g.drawLine(left + i * size_of_field, AppletConfig.top, left + i
					* size_of_field, bottom);
			g.drawLine(left, AppletConfig.top + i * size_of_field, right,
					AppletConfig.top + i * size_of_field);
			for (int j = 0; j < size; j++) {
				if (i < size && gameArea[i][j] != 0) {
					g.fillRect(left + i * size_of_field, AppletConfig.top + j
							* size_of_field, size_of_field, size_of_field);
				}
			}
		}
	}

	public void click(int x, int y) {
		if (mode == Mode.IN_GAME) {
			y = ((y - AppletConfig.top) >= 0) ? (y - AppletConfig.top)
					/ size_of_field : -1;
			x = ((x - left) >= 0) ? (x - left) / size_of_field : -1;
			if (x >= 0 && x < size && y >= 0 && y < size) {
				// TODO: V ploche s indexami
				System.out.println(x + " " + y);
				gameArea[x][y] = 1;
				app.repaint();
			}
		}
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public TicTacToe getApp() {
		return app;
	}

	public void setApp(TicTacToe app) {
		this.app = app;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public void setSize(int size) {
		this.size = size;
		gameArea = new int[size][size];
	}

	public int getSize() {
		return this.size;
	}

	public void setGameName(String name) {
		gameName = name;
	}

	public String getGameName() {
		return gameName;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void startWaiting() {
		wait = new WaitingForPlayers(this);
		wait.start();
	}

	public void stopWaiting() {
		wait.interrupt();
		wait = null;
	}

	public void createGame() {
		wait.setDelete(true);
	}

	/**
	 * @return the gameId
	 */
	public int getGameId() {
		return gameId;
	}

	/**
	 * @param gameId
	 *            the gameId to set
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getOnlinePlayers() {
		return onlinePlayers;
	}

	public void setOnlinePlayers(int onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}

	public void repaint() {
		app.repaint();
	}

	public void connect(int id) {
		waitingForStart = new WaitingForStart(this, id);
		waitingForStart.start();
	}

	public void disconnect() {
		waitingForStart.interrupt();
		waitingForStart = null;
	}

}