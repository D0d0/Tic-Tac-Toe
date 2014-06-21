import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AreaGet extends Thread {

	private Game game;
	private String url = AppletConfig.host + "GetArea";
	private JSONParser parser = new JSONParser();
	private URL obj;
	private String urlParameters;

	public AreaGet(Game game) {
		super();
		this.game = game;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
		}
		urlParameters = "id=" + game.getGameId();
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("bezi area get");
				java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
						.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", "Mozilla/5.0");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(
						con.getOutputStream());
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
				int i = 0;
				if ((boolean) jsonObject.get("result")
						&& (!jsonObject.get("area").toString()
								.equals(game.getGamearea()) || (i = Long
								.valueOf((Long) jsonObject.get("winner"))
								.intValue()) > 0)) {
					game.setGamearea(jsonObject.get("area").toString());
					if (i > 0) {
						System.out.print("vyrhral " + i);
						game.repaint();
						return;
					}
					game.repaint();
				}
				sleep(800);
			} catch (Exception e) {
				break;
			}
		}
	}
}
