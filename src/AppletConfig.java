import java.awt.Color;

/**
 * 
 * @author Jozef
 * 
 */
final class AppletConfig {
	static final String host = "http://davinci.fmph.uniba.sk:8080/~duc4/PiskvorkyServer/";
	static final int width = 600;
	static final int height = 700;
	static final int logoTop = 30;
	static final int logoLeft = 27;
	static final int buttonsWidth = 150;
	static final int buttonsHeight = 40;
	static final int loginButtonX = (width - 30 - 2 * buttonsWidth) / 2;
	static final int registerButtonX = loginButtonX + 30 + buttonsWidth;
	static final int menuButtonsWidth = 200;
	static final int menuButtonsX = (width - menuButtonsWidth) / 2;
	static final int startGameButtonY = 300;
	static final int connectToGameButtonY = startGameButtonY + buttonsHeight
			+ 30;
	static final int showAllPlayersButtonY = connectToGameButtonY
			+ buttonsHeight + 30;
	static final int textFieldWidth = 300;
	static final int textFieldHeight = 50;
	static final int textFieldX = (width - textFieldWidth) / 2;
	static final int labelUserY = 300;
	static final int gameNameY = labelUserY - 80;
	static final int errorCreatingY = gameNameY - 10;
	static final int textFieldUserY = labelUserY + 10;
	static final int labelPaswordY = textFieldUserY + 80;
	static final int textFieldPasswordY = labelPaswordY + 10;
	static final int rowY = textFieldPasswordY + 90;
	static final int buttonsY = textFieldPasswordY + textFieldHeight + 45;
	static final Color bgColor = new Color(145, 222, 162/*
														 * (int) (Math.random()
														 * * 256 + 10) % 255,
														 * (int) (Math.random()
														 * * 256 + 10) % 255,
														 * (int) (Math.random()
														 * * 256 + 10) % 255
														 */);
	static final int playersScrollWidth = 300;
	static final int playersScrollHeight = 400;
	static final int playersScrollX = (width - playersScrollWidth) / 2;
	static final int playersScrollY = 200;
	static final int createGameY = rowY + 80;
	static final int backWidth = 120;
	static final int backX = width - buttonsWidth - 20;
	static final int backY = height - buttonsHeight - 50;
	static final Color[] colors = { Color.black, Color.blue, Color.red,
			Color.yellow, Color.green, Color.orange, Color.pink };
	static final int nameY = gameNameY - 10;
	static final int maxY = textFieldUserY - 10;
	static final int sizeY = textFieldPasswordY - 10;
	static final int rowYlabel = rowY - 10;
	static final int top = 160;
	static final int left = 30;
	static final int boardSize = width - left * 2;
	static final int gameListY = 170;

}
