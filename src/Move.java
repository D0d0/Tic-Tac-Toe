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
public class Move {
	private String canMove = "CanMove";
	private String changeMove = "ChangeMove";
	private String updateArea = "UpdateArea";
	private String setWinner = "SetWinner";
	private int rank, id;
	private JSONParser parser = new JSONParser();
	private URL obj;

	/**
	 * Konstruktor pohybu
	 * 
	 * @param rank
	 *            poradie hraca
	 * @param id
	 *            ID hry na serveri
	 */
	public Move(int rank, int id) {
		this.rank = rank;
		this.id = id;
	}

	/**
	 * Zisti ci hrac moze polozit figurku
	 * 
	 * @param area
	 *            plocha
	 * @return moze, nemoze
	 */
	public boolean can(String area) {
		try {
			String url = AppletConfig.host + canMove;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "id=" + id + "&rank=" + rank + "&area="
					+ area;
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
			return (boolean) jsonObject.get("result");
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Ak polozil figuku, tak posunie pohyb na serveri
	 */
	public void changeMove() {
		try {
			String url = AppletConfig.host + changeMove;
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

	/**
	 * Aktualizuje plochu na serveri
	 * 
	 * @param newArea
	 *            nova plocha
	 */
	public void updateArea(String newArea) {
		try {
			String url = AppletConfig.host + updateArea;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "id=" + id + "&area=" + newArea;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			con.getInputStream();
		} catch (Exception e) {
		}
	}

	/**
	 * Nastavi vitaza na serveri
	 */
	public void setWinner() {
		try {
			String url = AppletConfig.host + setWinner;
			obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "id=" + id + "&winner=" + rank;
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
