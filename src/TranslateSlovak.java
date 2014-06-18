public class TranslateSlovak extends Translate {
	private final String logo = "logo.png";
	private final String loggedUser = "Prihl�sen� u��vate�: ";
	private final String user = "Meno";
	private final String password = "Heslo";
	private final String login = "Prihl�s";
	private final String register = "Registruj";
	private final String startGame = "Vytvori� hru";
	private final String createGame = "Vytvor hru";
	private final String connectToGame = "Pripoji� sa k hre";
	private final String showAllPlayers = "Zobraz v�etk�ch hr��ov";
	private final String backToMenu = "Sp� do menu";
	private final String gameName = "N�zov hry";
	private final String maxPlayers = "Maxim�lny po�et hr��ov";
	private final String numberOfConnected = "Po�et prihl�sen�ch hr��ov: ";
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
