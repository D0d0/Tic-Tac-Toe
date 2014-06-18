public class TranslateSlovak extends Translate {
	private final String logo = "logo.png";
	private final String loggedUser = "Prihl·sen˝ uûÌvateæ: ";
	private final String user = "Meno";
	private final String password = "Heslo";
	private final String login = "Prihl·s";
	private final String register = "Registruj";
	private final String startGame = "Vytvoriù hru";
	private final String createGame = "Vytvor hru";
	private final String connectToGame = "Pripojiù sa k hre";
	private final String showAllPlayers = "Zobraz vöetk˝ch hr·Ëov";
	private final String backToMenu = "Sp‰ù do menu";
	private final String gameName = "N·zov hry";
	private final String maxPlayers = "Maxim·lny poËet hr·Ëov";
	private final String numberOfConnected = "PoËet prihl·sen˝ch hr·Ëov: ";
	private final String startGame1 = "Spusti hru";

	@Override
	public String logo() {
		return logo;
	}

	@Override
	String loggedUser() {
		return loggedUser;
	}

	@Override
	String user() {
		return user;
	}

	@Override
	String password() {
		return password;
	}

	@Override
	String login() {
		return login;
	}

	@Override
	String register() {
		return register;
	}

	@Override
	String startGame() {
		return startGame;
	}

	@Override
	String createGame() {
		return createGame;
	}

	@Override
	String connectToGame() {
		return connectToGame;
	}

	@Override
	String showAllPlayers() {
		return showAllPlayers;
	}

	@Override
	String backToMenu() {
		return backToMenu;
	}

	@Override
	String gameName() {
		return gameName;
	}

	@Override
	String maxPlayers() {
		return maxPlayers;
	}

	@Override
	String numberOfConnected() {
		return numberOfConnected;
	}

	@Override
	String startGame1() {
		return startGame1;
	}
}
