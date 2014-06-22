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
public class WaitingForStart extends Thread {

	private Game game;
	private int id;
	private final String Connect = "Connect";
	private final String Disconnect = "Disconnect";
	private final String getOnlinePlayers = "GetOnlinePlayers";
	private JSONParser parser = new JSONParser();
	private URL obj;
	private boolean delete = false;

	public WaitingForStart(Game game, int id) {
		this.game = game;
		this.id = id;
	}

	@Override
	public void run() {
		connect();
		while (true) {
			try {
				if (delete = waitForPlayers()) {
					interrupt();
				}
				sleep(300);
			} catch (InterruptedException e) {
				if (!delete) {
					disconnect();
				}
				break;
			}

		}
	}

	private boolean waitForPlayers() {
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
				int gamestatus = Long.valueOf(
						(Long) jsonObject.get("is_active")).intValue();

				switch (gamestatus) {
				case 1:
					int serverOnlinePlayers = Long.valueOf(
							(Long) jsonObject.get("online")).intValue();
					if (serverOnlinePlayers != game.getOnlinePlayers()) {
						game.setOnlinePlayers(serverOnlinePlayers);
						game.repaint();
					}
					return false;
				case 2:
					game.setMode(Mode.IN_GAME);
					game.setRank(Long.valueOf((Long) jsonObject.get("rank"))
							.intValue());
					game.setRows((Long.valueOf((Long) jsonObject.get("rows"))
							.intValue()));
					game.initialize();
					game.repaint();
					return true;
				}
			}
			game.setMode(Mode.MENU);
			game.repaint();
			interrupt();
		} catch (Exception e) {
		}
		return false;
	}

	private void connect() {
		try {
			String url = AppletConfig.host + Connect;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "id=" + id;
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
				game.setOnlinePlayers(Long.valueOf(
						(Long) jsonObject.get("online")).intValue());
				game.setMaxPlayers(Long.valueOf((Long) jsonObject.get("max"))
						.intValue());
				game.setSize(Long.valueOf((Long) jsonObject.get("size"))
						.intValue());
				game.repaint();
			}
		} catch (Exception e) {

		}
	}

	private void disconnect() {
		try {
			String url = AppletConfig.host + Disconnect;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "id=" + id;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			con.getInputStream();
		} catch (Exception e) {

		}
	}

}
