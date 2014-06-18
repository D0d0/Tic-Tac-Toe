import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TicTacToe extends JApplet {

	private static final long serialVersionUID = 1097471433365253670L;
	private Translate trans = new TranslateSlovak();
	private Image logo;
	private JButton loginButton = new MyButton(trans.login());
	private JButton registerButton = new MyButton(trans.register());
	private JButton startGameButton = new MyButton(trans.startGame());
	private JButton createGame = new MyButton(trans.createGame());
	private JButton connectToGame = new MyButton(trans.connectToGame());
	private JButton showAllPlayers = new MyButton(trans.showAllPlayers());
	private JButton back = new MyButton(trans.backToMenu());
	private JButton startGame = new MyButton(trans.startGame1());
	private JTextField user = new JTextField();
	private JTextField size = new JTextField();
	private JTextField max = new JTextField();
	private JTextField gameName = new JTextField();
	private JPasswordField password = new JPasswordField();
	private JTextArea players = new JTextArea(5, 10);
	private JScrollPane playersScrool = new JScrollPane(players);
	private JTextArea allGames = new JTextArea(5, 10);
	private JScrollPane allGameScroll = new JScrollPane(allGames);
	private JPanel view = new JPanel();
	private JScrollPane gamesScroll = new JScrollPane();
	private FontRenderContext frc = new FontRenderContext(
			new AffineTransform(), true, true);
	private MediaTracker tracker;
	private Game game = new Game(this);
	private BufferedImage buffer;
	private int onlinePlayersS;
	private int maxPlayersS;
	private int getNameS;
	private int passwordS;
	private int userS;

	@Override
	public void init() {
		tracker = new MediaTracker(this);
		logo = getImage(this.getCodeBase(), trans.logo());
		tracker.addImage(logo, 0);
		try {
			tracker.waitForAll();
			repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer = new BufferedImage(AppletConfig.width, AppletConfig.height,
				BufferedImage.TYPE_INT_ARGB);
		setFont(new Font("TimesRoman", Font.BOLD, 18));
		user.setHorizontalAlignment(JLabel.CENTER);
		password.setHorizontalAlignment(JLabel.CENTER);
		size.setHorizontalAlignment(JLabel.CENTER);
		max.setHorizontalAlignment(JLabel.CENTER);
		gameName.setHorizontalAlignment(JLabel.CENTER);
		user.setBorder(null);
		password.setBorder(null);
		size.setBorder(null);
		max.setBorder(null);
		gameName.setBorder(null);
		setBackground(AppletConfig.bgColor);
		loginButton.setBounds(AppletConfig.loginButtonX, AppletConfig.buttonsY,
				AppletConfig.buttonsWidth, AppletConfig.buttonsHeight);
		registerButton.setBounds(AppletConfig.registerButtonX,
				AppletConfig.buttonsY, AppletConfig.buttonsWidth,
				AppletConfig.buttonsHeight);
		user.setBounds(AppletConfig.textFieldX, AppletConfig.textFieldUserY,
				AppletConfig.textFieldWidth, AppletConfig.textFieldHeight);
		password.setBounds(AppletConfig.textFieldX,
				AppletConfig.textFieldPasswordY, AppletConfig.textFieldWidth,
				AppletConfig.textFieldHeight);
		max.setText("4");
		max.setBounds(AppletConfig.textFieldX, AppletConfig.textFieldUserY,
				AppletConfig.textFieldWidth, AppletConfig.textFieldHeight);
		size.setText("10");
		size.setBounds(AppletConfig.textFieldX,
				AppletConfig.textFieldPasswordY, AppletConfig.textFieldWidth,
				AppletConfig.textFieldHeight);
		gameName.setBounds(AppletConfig.textFieldX, AppletConfig.gameNameY,
				AppletConfig.textFieldWidth, AppletConfig.textFieldHeight);
		startGameButton.setBounds(AppletConfig.menuButtonsX,
				AppletConfig.startGameButtonY, AppletConfig.menuButtonsWidth,
				AppletConfig.buttonsHeight);
		createGame.setBounds(AppletConfig.menuButtonsX,
				AppletConfig.createGameY, AppletConfig.menuButtonsWidth,
				AppletConfig.buttonsHeight);
		connectToGame.setBounds(AppletConfig.menuButtonsX,
				AppletConfig.connectToGameButtonY,
				AppletConfig.menuButtonsWidth, AppletConfig.buttonsHeight);
		showAllPlayers.setBounds(AppletConfig.menuButtonsX,
				AppletConfig.showAllPlayersButtonY,
				AppletConfig.menuButtonsWidth, AppletConfig.buttonsHeight);
		playersScrool.setBounds(AppletConfig.playersScrollX,
				AppletConfig.playersScrollY, AppletConfig.playersScrollWidth,
				AppletConfig.playersScrollHeight);
		playersScrool.setBorder(null);
		players.setMargin(new Insets(10, 10, 10, 10));
		players.setBackground(AppletConfig.bgColor);
		allGameScroll.setBounds(AppletConfig.playersScrollX,
				AppletConfig.playersScrollY, AppletConfig.playersScrollWidth,
				AppletConfig.playersScrollHeight);

		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		view.setBounds(AppletConfig.playersScrollX,
				AppletConfig.playersScrollY, AppletConfig.playersScrollWidth,
				AppletConfig.playersScrollHeight);
		gamesScroll = new JScrollPane(view);
		gamesScroll.setBounds(AppletConfig.playersScrollX,
				AppletConfig.playersScrollY, AppletConfig.playersScrollWidth,
				AppletConfig.playersScrollHeight);
		view.setBackground(AppletConfig.bgColor);
		gamesScroll.setBackground(AppletConfig.bgColor);
		gamesScroll.setBorder(null);

		allGames.setMargin(new Insets(10, 10, 10, 10));
		allGames.setBackground(AppletConfig.bgColor);
		back.setBounds(AppletConfig.backX, AppletConfig.backY,
				AppletConfig.backWidth, AppletConfig.buttonsHeight);
		startGame.setBounds(AppletConfig.menuButtonsX,
				AppletConfig.createGameY, AppletConfig.menuButtonsWidth,
				AppletConfig.buttonsHeight);
		setBackground(AppletConfig.bgColor);
		setLayout(null);
		add(loginButton);
		add(registerButton);
		add(startGameButton);
		add(connectToGame);
		add(showAllPlayers);
		add(user);
		add(password);
		add(playersScrool);
		add(gamesScroll);
		add(createGame);
		add(allGameScroll);
		add(back);
		add(max);
		add(size);
		add(gameName);
		add(startGame);
		setSize(AppletConfig.width, AppletConfig.height);
		setVisible(true);
		loginButton.addActionListener(new ButtonActionLoginL());
		registerButton.addActionListener(new ButtonActionRegisterL());
		startGameButton.addActionListener(new StartGameButtonL());
		showAllPlayers.addActionListener(new ShowAllPlayersL());
		back.addActionListener(new BackToMenuL());
		createGame.addActionListener(new CreateGameL());
		connectToGame.addActionListener(new ConnectGameL());
		startGame.addActionListener(new StartGameToGameL());
		addMouseListener(new MouseL());
		// repaint();
		calculateXs();
	}

	/**
	 * 
	 */
	public void empyView() {
		view.removeAll();
		view.repaint();
	}

	public void addButton(String s, int id, int max, int size) {
		JButton butt = new MyChooseGameButton(s, id, game, this, max, size);
		butt.setAlignmentX(Component.CENTER_ALIGNMENT);
		view.add(butt);
	}

	public void refreshGames() {
		validate();
		invalidate();
	}

	class MouseL extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getButton() == MouseEvent.BUTTON1) {
				game.click(arg0.getX(), arg0.getY());
			}
		}
	}

	class BackToMenuL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				if (game.getMode() == Mode.WAITING_FOR_OTHERS) {
					game.stopWaiting();
					return;
				}
				if (game.getMode() == Mode.CONNECT_TO_GAME) {
					game.stopGettingOnlineGames();
				}
				if (game.getMode() == Mode.WAITING_FOR_START) {
					game.disconnect();
				}
			} catch (Exception e) {
			} finally {
				game.setMode(Mode.MENU);
				repaint();
			}

		}
	}

	class ShowAllPlayersL implements ActionListener {
		private String padRight(String s, int n) {
			return String.format("%1$-" + n + "s", s);
		}

		private String padLeft(String s, int n) {
			return String.format("%1$" + n + "s", s);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Request rqt = new Request();
			rqt.setTypeRequest(TypeRequest.GETPLAYERS);
			rqt.start();
			synchronized (rqt) {
				try {
					rqt.wait();
					Object obj1 = (new JSONParser()).parse(rqt
							.getResultString());
					JSONObject jsonObject = (JSONObject) obj1;
					JSONArray array = null;
					players.setText(null);
					for (Object o : new TreeSet<>(jsonObject.keySet())) {
						array = (JSONArray) jsonObject.get(o);
						String name = (String) o;
						String win = Long.toString((Long) array.get(0));
						String lost = Long.toString((Long) array.get(1));
						String output = (players.getText().equals("") ? ""
								: "\n")
								+ padRight(name, 15)
								+ padRight(win, 1)
								+ padLeft(lost, 3);
						players.append(output);
					}
					game.setMode(Mode.ALL_PLAYERS);
					players.setEditable(false);
					players.setHighlighter(null);
					repaint();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	class ButtonActionLoginL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Request rqt = new Request();
			rqt.setTypeRequest(TypeRequest.LOGIN);
			rqt.setname(user.getText());
			rqt.setPassword(String.valueOf(password.getPassword()));
			rqt.start();
			synchronized (rqt) {
				try {
					rqt.wait();
					Object obj1 = (new JSONParser()).parse(rqt
							.getResultString());
					JSONObject jsonObject = (JSONObject) obj1;
					if ((boolean) jsonObject.get("result")) {
						game.setMode(Mode.MENU);
						game.setPlayerName(user.getText());
						game.setPlayerID(((Long) jsonObject.get("id"))
								.intValue());
						repaint();
					} else {
						throw new Exception();
					}
					// TODO:tuto chujovinu errory
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class ButtonActionRegisterL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Request rqt = new Request();
			rqt.setTypeRequest(TypeRequest.REGISTER);
			rqt.setname(user.getText());
			rqt.setPassword(String.valueOf(password.getPassword()));
			rqt.start();
			synchronized (rqt) {
				try {
					rqt.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class StartGameButtonL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			game.setMode(Mode.CREATE_GAME);
			repaint();
		}

	}

	class CreateGameL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean error = false;
			max.setBackground(Color.white);
			size.setBackground(Color.white);
			gameName.setBackground(Color.white);
			try {
				String m = max.getText();
				int maxInt = Integer.parseInt(m);
				game.setMaxPlayers(maxInt);
				if (maxInt > 6 || maxInt < 2) {
					throw new Exception();
				}
			} catch (Exception ex) {
				error = true;
				max.setBackground(Color.red);
			}
			try {
				String s = size.getText();
				int sizeInt = Integer.parseInt(s);
				game.setSize(sizeInt);
				if (sizeInt < 3 || sizeInt > 10) {
					throw new Exception();
				}
			} catch (Exception ex) {
				error = true;
				size.setBackground(Color.red);
			}
			try {
				String name = gameName.getText();
				game.setGameName(name);
				if (name.length() < 4 || name.length() > 10) {
					throw new Exception();
				}
			} catch (Exception ex) {
				error = true;
				gameName.setBackground(Color.red);
			}
			if (!error) {
				game.setMode(Mode.WAITING_FOR_OTHERS);
				game.setOnlinePlayers(1);
				game.startWaiting();
			}
			repaint();
		}
	}

	class ConnectGameL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.setMode(Mode.CONNECT_TO_GAME);
			game.getGames();
			repaint();
		}
	}

	class StartGameToGameL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			game.setMode(Mode.IN_GAME);
			game.createGame();
			game.stopWaiting();
			// TODO: Kliknutie na spustenie hry
			game.initialize();
			repaint();
		}

	}

	@Override
	public void destroy() {
		if (game.getMode() == Mode.WAITING_FOR_OTHERS) {
			game.stopWaiting();
		}
		if (game.getMode() == Mode.CONNECT_TO_GAME) {
			game.stopGettingOnlineGames();
		}
		if (game.getMode() == Mode.WAITING_FOR_START) {
			game.disconnect();
		}
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		getContentPane().removeAll();
		buffer.getGraphics().dispose();
		Graphics bg = buffer.getGraphics();
		bg.setFont(new Font("TimesRoman", Font.BOLD, 18));
		bg.setColor(AppletConfig.bgColor);
		bg.clearRect(0, 0, AppletConfig.width, AppletConfig.height);
		bg.fillRect(0, 0, AppletConfig.width, AppletConfig.height);
		bg.drawImage(logo, AppletConfig.left, AppletConfig.logoTop, this);
		calculateXs();
		switch (game.getMode()) {
		case LOGIN:
			add(loginButton);
			add(registerButton);
			add(user);
			add(password);
			loginButton.repaint();
			registerButton.repaint();
			user.repaint();
			password.repaint();
			bg.setColor(Color.black);
			bg.drawString(trans.user(), userS, AppletConfig.labelUserY);
			bg.drawString(trans.password(), passwordS,
					AppletConfig.labelPaswordY);
			break;
		case MENU:
			add(startGameButton);
			add(connectToGame);
			add(showAllPlayers);
			startGameButton.repaint();
			connectToGame.repaint();
			showAllPlayers.repaint();
			bg.setColor(Color.black);
			bg.drawString(trans.loggedUser() + game.getPlayerName(), 30,
					AppletConfig.height - 20);
			break;
		case ALL_PLAYERS:
			add(playersScrool);
			add(back);
			playersScrool.getVerticalScrollBar().setValue(0);
			back.repaint();
			playersScrool.repaint();
			playersScrool.doLayout();
			bg.setColor(Color.black);
			bg.drawString(trans.loggedUser() + game.getPlayerName(), 30,
					AppletConfig.height - 20);
			break;
		case CREATE_GAME:
			// TODO: ked si vytvaram hru
			add(createGame);
			add(back);
			add(max);
			add(size);
			add(gameName);
			// gameName.setText("");
			// TODO: dokoncit toto
			size.repaint();
			max.repaint();
			back.repaint();
			createGame.repaint();
			gameName.repaint();
			bg.setColor(Color.black);
			bg.drawString(trans.loggedUser() + game.getPlayerName(), 30,
					AppletConfig.height - 20);
			bg.drawString(trans.gameName(), getNameS, AppletConfig.sizeY);
			bg.drawString(trans.maxPlayers(), maxPlayersS, AppletConfig.maxY);
			break;
		case CONNECT_TO_GAME:
			// TODO: ked som klikol na pripojenie
			add(back);
			add(gamesScroll);
			gamesScroll.repaint();
			back.repaint();
			bg.setColor(Color.black);
			bg.drawString(trans.loggedUser() + game.getPlayerName(), 30,
					AppletConfig.height - 20);
			break;
		case WAITING_FOR_START:
		case WAITING_FOR_OTHERS:
			// TODO: ked som klikol na vytvorenieh hry
			add(back);
			if (game.getMode() == Mode.WAITING_FOR_OTHERS) {
				add(startGame);
				startGame.repaint();
			}
			back.repaint();
			bg.setColor(Color.black);
			bg.drawString(trans.loggedUser() + game.getPlayerName(), 30,
					AppletConfig.height - 20);
			bg.drawString(trans.numberOfConnected() + game.getOnlinePlayers(),
					onlinePlayersS, AppletConfig.maxY);
			break;
		case IN_GAME:
			game.drawGameArea(bg);
			bg.setColor(Color.black);
			bg.drawString(trans.loggedUser() + game.getPlayerName(), 30,
					AppletConfig.height - 20);
			break;
		default:
			break;
		}
		g.drawImage(buffer, 0, 0, this);
	}

	private void calculateXs() {
		onlinePlayersS = (AppletConfig.width - (int) (getFont()
				.getStringBounds(
						trans.numberOfConnected() + game.getOnlinePlayers(),
						frc).getWidth())) >> 1;
		maxPlayersS = (AppletConfig.width - (int) (getFont().getStringBounds(
				trans.maxPlayers(), frc).getWidth())) >> 1;
		getNameS = (AppletConfig.width - (int) (getFont().getStringBounds(
				trans.gameName(), frc).getWidth())) >> 1;
		passwordS = (AppletConfig.width - (int) (getFont().getStringBounds(
				trans.password(), frc).getWidth())) >> 1;
		userS = (AppletConfig.width - (int) (getFont().getStringBounds(
				trans.user(), frc).getWidth())) >> 1;
	}

}