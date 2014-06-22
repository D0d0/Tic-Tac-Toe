import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 * @author Jozef
 * 
 */
public class OnlineGames extends Thread {

	private TicTacToe app;
	private final String getOnlineGames = "GetOnlineGames";
	private JSONParser parser = new JSONParser();
	private URL obj;
	private Map<String, int[]> games = new HashMap<>();

	/**
	 * Konstruktor hry
	 * 
	 * @param app
	 *            applet
	 */
	public OnlineGames(TicTacToe app) {
		this.app = app;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// System.out.println("bezi get online");
				getOnlineGames();
				sleep(150);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	private void getOnlineGames() {
		try {
			String url = AppletConfig.host + getOnlineGames;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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
			boolean tmp = false;
			JSONArray array = null;
			int[] arr = new int[4];
			if (games.keySet().equals(jsonObject.keySet())) {
				for (Object o : new TreeSet<>(jsonObject.keySet())) {
					array = (JSONArray) jsonObject.get(o);
					if (Long.valueOf((Long) array.get(0)).intValue() != games
							.get(o)[0]
							|| Long.valueOf((Long) array.get(1)).intValue() != games
									.get(o)[1]
							|| Long.valueOf((Long) array.get(2)).intValue() != games
									.get(o)[2]
							|| Long.valueOf((Long) array.get(3)).intValue() != games
									.get(o)[3]) {
						tmp = true;
						break;
					}
				}
			}
			if (!games.keySet().equals(jsonObject.keySet()) || tmp) {
				games.clear();

				for (Object o : new TreeSet<>(jsonObject.keySet())) {
					arr = new int[4];
					array = (JSONArray) jsonObject.get(o);
					arr[0] = Long.valueOf((Long) array.get(0)).intValue();
					arr[1] = Long.valueOf((Long) array.get(1)).intValue();
					arr[2] = Long.valueOf((Long) array.get(2)).intValue();
					arr[3] = Long.valueOf((Long) array.get(3)).intValue();
					games.put((String) o, arr);
				}
				app.empyView();
				fillViewWithButtons();
				app.refreshGames();
			}
		} catch (Exception e) {
		}
	}

	private void fillViewWithButtons() {
		for (Object o : games.keySet()) {
			String s = (String) o;
			String label = s + " " + games.get(o)[1] + "/" + games.get(o)[2]
					+ " " + games.get(o)[3];
			app.addButton(label, games.get(o)[0], games.get(o)[2],
					games.get(o)[3]);
		}
	}

}
