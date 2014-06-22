import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * 
 * @author Jozef
 * 
 */
public class Game {

	private class Pair {
		private int x, y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private int[][] gameArea;
	private int size, playerID, maxPlayers, gameId, onlinePlayers, rank, rows;
	private TicTacToe app;
	private Mode mode;
	private String playerName, gameName;
	private WaitingForPlayers wait;
	private OnlineGames onlineGames;
	private WaitingForStart waitingForStart;
	private int size_of_field;
	private int left;
	private int width;
	private int winner;
	private Move moveC;
	private AreaGet getArea;
	private String gamearea;
	private ArrayList<Pair> winnerRows = new ArrayList<>();

	/**
	 * Konstruktor triedy
	 * 
	 * @param app
	 *            applet
	 */
	public Game(TicTacToe app) {
		setApp(app);
		size = 10;
		setMode(Mode.LOGIN);
	}

	/**
	 * Poradie hraca v hre
	 * 
	 * @return vrati poradie hraca v hre
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Spusti ziskavanie hier, na ktore sa da pripojit zo servera
	 */
	public void getGames() {
		onlineGames = new OnlineGames(app);
		onlineGames.start();
	}

	/**
	 * Vitaz
	 * 
	 * @return poradie hraca, ktory vyhral
	 */
	public int getWinner() {
		return winner;
	}

	/**
	 * Nastavi vitaza
	 * 
	 * @param winner
	 *            vitaz
	 */
	public void setWinner(int winner) {
		this.winner = winner;
	}

	/**
	 * Zastavi ziskavanie hier, na ktore sa da pripojit
	 */
	public void stopGettingOnlineGames() {
		if (onlineGames != null) {
			onlineGames.interrupt();
			onlineGames = null;
		}
	}

	/**
	 * Inicializuje hru
	 */
	public void initialize() {
		size_of_field = Math
				.min((int) ((AppletConfig.height - AppletConfig.top - 2 * AppletConfig.left) / size),
						(int) ((AppletConfig.width - 2 * AppletConfig.left) / size));
		width = size_of_field * size;
		left = (AppletConfig.width - width) / 2;
		getArea = new AreaGet(this);
		winner = -1;
		getArea.start();
	}

	/**
	 * Kresli plochu hry
	 * 
	 * @param g
	 *            kde sa ma kreslit hra
	 */
	public void drawGameArea(Graphics g) {
		int bottom = AppletConfig.top + size_of_field * size;
		int right = AppletConfig.width - left;
		g.setColor(Color.white);
		g.fillRect(left, AppletConfig.top, width, width);
		Graphics2D g2d = (Graphics2D) g;
		BasicStroke original_stroke = (BasicStroke) g2d.getStroke();
		BasicStroke new_stroke = new BasicStroke(10, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL);
		// Mozno niekedy nabuduce
		/*
		 * if (winner > 0) { g.setColor(AppletConfig.colors[winner]); for (Pair
		 * pair : winnerRows) { g.fillRect(left + pair.x * size_of_field,
		 * AppletConfig.top + pair.y * size_of_field, size_of_field,
		 * size_of_field); } }
		 */
		g.setColor(Color.black);
		for (int i = 0; i <= size; i++) {
			g.drawLine(left + i * size_of_field, AppletConfig.top, left + i
					* size_of_field, bottom);
			g.drawLine(left, AppletConfig.top + i * size_of_field, right,
					AppletConfig.top + i * size_of_field);
			for (int j = 0; j < size; j++) {
				if (i < size && gameArea[i][j] != 0) {
					g2d.setStroke(new_stroke);
					g2d.setColor(AppletConfig.colors[gameArea[i][j]]);
					g2d.drawLine(left + i * size_of_field + 10,
							AppletConfig.top + j * size_of_field + 10, left
									+ (i + 1) * size_of_field - 10,
							AppletConfig.top + (j + 1) * size_of_field - 10);
					g2d.drawLine(left + (i + 1) * size_of_field - 10,
							AppletConfig.top + j * size_of_field + 10, left + i
									* size_of_field + 10, AppletConfig.top
									+ (j + 1) * size_of_field - 10);
					g2d.setStroke(original_stroke);
					g2d.setColor(Color.black);
				}
			}
		}
	}

	/**
	 * Metoda, ktora spracuje kliknutie
	 * 
	 * @param x
	 *            X suradnica mysi
	 * @param y
	 *            Y suradnica mysi
	 */
	public void click(int x, int y) {
		if (mode == Mode.IN_GAME && winner < 0) {
			y = ((y - AppletConfig.top) >= 0) ? (y - AppletConfig.top)
					/ size_of_field : -1;
			x = ((x - left) >= 0) ? (x - left) / size_of_field : -1;
			if (x >= 0 && x < size && y >= 0 && y < size && gameArea[x][y] == 0) {
				moveC = new Move(rank, gameId);
				if (moveC.can(codeArea())) {
					gameArea[x][y] = rank;
					gamearea = codeArea();
					if (checkWin(rank)) {
						getArea.interrupt();
						getArea = null;
						moveC.setWinner();
						winner = rank;
					}
					app.repaint();
					moveC.changeMove();
					moveC.updateArea(gamearea);
				}
			}
		}
	}

	private String codeArea() {
		String result = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result += gameArea[i][j] + " ";
			}
		}
		return result.trim();
	}

	/**
	 * Mod hry
	 * 
	 * @return vrati mod hry pre vykreslovanie
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * Nastavi mod hry
	 * 
	 * @param mode
	 *            novy mod
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/**
	 * Nastavi applet
	 * 
	 * @param app
	 *            nove
	 */
	public void setApp(TicTacToe app) {
		this.app = app;
	}

	/**
	 * Vrati hracove meno
	 * 
	 * @return hracove meno
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Nastavi hracove meno
	 * 
	 * @param playerName
	 *            nove meno
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Vrati hracove ID zo servera
	 * 
	 * @return hracove ID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * Nastavi hracove meno z DB
	 * 
	 * @param playerID
	 *            nove meno
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	/**
	 * Nastavi velkost plochy a inicializuje ju
	 * 
	 * @param size
	 *            nova velkost plochy
	 */
	public void setSize(int size) {
		this.size = size;
		gameArea = new int[size][size];
	}

	/**
	 * Vrato velkost plochy
	 * 
	 * @return velkost plochy
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Nastavi nazov hry
	 * 
	 * @param name
	 *            novy nazov
	 */
	public void setGameName(String name) {
		gameName = name;
	}

	/**
	 * Vrati nazov hry
	 * 
	 * @return nazov hry
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * Vrati max pocet hracov
	 * 
	 * @return max pocet hracov
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Nastavi max pocet hracov
	 * 
	 * @param maxPlayers
	 *            novy max pocet hracov
	 */
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	/**
	 * Spusti cakanie na dalsich hracov
	 */
	public void startWaiting() {
		wait = new WaitingForPlayers(this);
		wait.start();
	}

	/**
	 * Ukonci cakanie na ostatnych hracov
	 */
	public void stopWaiting() {
		if (wait != null) {
			wait.interrupt();
			wait = null;
		}
	}

	/**
	 * Nastavi vytvorenie hry
	 */
	public void createGame() {
		wait.setDelete(true);
	}

	/**
	 * Vrati ID hry
	 * 
	 * @return gameId
	 */
	public int getGameId() {
		return gameId;
	}

	/**
	 * @param gameId
	 *            nove ID hry
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	/**
	 * Vrati pocet pripojenych hracov
	 * 
	 * @return pocet pripojenych hracov
	 */
	public int getOnlinePlayers() {
		return onlinePlayers;
	}

	/**
	 * Nastavi pocet online hracov
	 * 
	 * @param onlinePlayers
	 *            novy pocet online hracov
	 */
	public void setOnlinePlayers(int onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}

	/**
	 * Preklesli applet
	 */
	public void repaint() {
		app.repaint();
	}

	/**
	 * Spusti cakanie na start hry
	 * 
	 * @param id
	 *            ID s hrou na cakanie
	 */
	public void connect(int id) {
		waitingForStart = new WaitingForStart(this, id);
		waitingForStart.start();
	}

	/**
	 * Odpoji od hry
	 */
	public void disconnect() {
		if (waitingForStart != null) {
			waitingForStart.interrupt();
			waitingForStart = null;
		}
	}

	/**
	 * Nstavi poradie hraca
	 * 
	 * @param rank
	 *            nove poradie
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Vrati plochu s hrou
	 * 
	 * @return plocha hry
	 */
	public String getGamearea() {
		return gamearea;
	}

	/**
	 * Nastavi plochu hry
	 * 
	 * @param gamearea
	 *            nova plocha hry
	 */
	public void setGamearea(String gamearea) {
		this.gamearea = gamearea;
		String[] tmp = gamearea.split(" ");
		int k = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				gameArea[i][j] = Integer.valueOf(tmp[k]);
				k++;
			}
		}
	}

	/**
	 * Vrati pocet na vyhratie
	 * 
	 * @return pocet na vyhratie
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Nastavi pocet policok na vyhru
	 * 
	 * @param rows
	 *            novy pocet na vyhratie
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	private boolean scan(int[] what, int dx, int dy) {
		winnerRows.clear();
		if (gameArea == null) {
			return false;
		}
		for (int i = 0; i < gameArea.length; i++) {
			if (gameArea[i] == null) {
				continue;
			}
			next: for (int j = 0; j < gameArea[i].length; j++) {
				for (int k = 0; k < what.length; k++) {
					int x = i + k * dx;
					int y = j + k * dy;
					if (x < 0 || x >= gameArea.length || gameArea[x] == null
							|| y < 0 || y >= gameArea[x].length
							|| gameArea[x][y] != what[k]) {
						continue next;
					}
					// winnerRows.add(new Pair(x, y));
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Skontroluje plochu na vyhru
	 * 
	 * @param rank
	 *            poradie hraca na kontrolu
	 * @return vyhrane alebo nie
	 */
	public boolean checkWin(int rank) {
		int[] what = new int[rows];
		for (int i = 0; i < rows; i++) {
			what[i] = rank;
		}
		return scan(what, 0, 1) || scan(what, 1, 0) || scan(what, 1, 1)
				|| scan(what, 1, -1);
	}
}
