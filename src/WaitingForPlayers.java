import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 * @author Jozef
 * 
 */
public class WaitingForPlayers extends Thread {

	private final String createGame = "CreateGame";
	private final String deleteGame = "DeleteGame";
	private final String startGame = "StartGame";
	private final String getOnlinePlayers = "GetOnlinePlayers";
	private JSONParser parser = new JSONParser();
	private URL obj;
	private Game game;
	private boolean delete = false;

	public WaitingForPlayers(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		if (!createGame()) {
			return;
		}
		while (true) {
			try {
				// System.out.println("bezi wait for playes");
				waitForPlayers();
				sleep(300);
			} catch (InterruptedException e) {
				if (delete) {
					startGame();
				} else {
					stopWaiting();
				}
				break;
			}

		}
	}

	private boolean createGame() {
		try {
			String url = AppletConfig.host + createGame;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "name=" + game.getGameName() + "&size="
					+ game.getSize() + "&playerid=" + game.getPlayerID()
					+ "&host=" + game.getPlayerName() + "&max="
					+ game.getMaxPlayers() + "&rows=" + game.getRows();
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			Object obj1 = parser.parse(response.toString());
			JSONObject jsonObject = (JSONObject) obj1;
			if ((boolean) jsonObject.get("result")) {
				game.setGameId(Long.valueOf((Long) jsonObject.get("id"))
						.intValue());
				game.repaint();
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	private void waitForPlayers() {
		try {
			String url = AppletConfig.host + getOnlinePlayers;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "id=" + game.getGameId();
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			Object obj1 = parser.parse(response.toString());
			JSONObject jsonObject = (JSONObject) obj1;
			if ((boolean) jsonObject.get("result")) {
				int serverOnlinePlayers = Long.valueOf(
						(Long) jsonObject.get("online")).intValue();
				if (serverOnlinePlayers != game.getOnlinePlayers()) {
					game.setOnlinePlayers(serverOnlinePlayers);
					game.repaint();
				}
			} else {
				interrupt();
				game.setMode(Mode.MENU);
				game.repaint();
			}
		} catch (Exception e) {

		}
	}

	private void stopWaiting() {
		try {
			String url = AppletConfig.host + deleteGame;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "id=" + game.getGameId();
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			con.getInputStream();
		} catch (Exception e) {
		}
	}

	private void startGame() {
		try {
			String url = AppletConfig.host + startGame;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "id=" + game.getGameId();
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			con.getInputStream();
			game.setRank(1);
			game.repaint();
		} catch (Exception e) {
		}
	}

	/**
	 * Nastavi vymazanie hry na serveri
	 * 
	 * @param delete
	 *            true ak sa maze na serveri
	 */
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

}
