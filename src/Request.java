import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author Jozef
 * 
 */
public class Request extends Thread {

	private final String hostCreatePlayer = "CreatePlayer";
	private final String hostLogin = "Login";
	private final String allPlayers = "AllPlayers";
	private String name;
	private String password;
	private boolean result;
	private String resultString;
	private TypeRequest typeRequest;

	/**
	 * Konstruktor
	 */
	public Request() {
		super();
	}

	private boolean register() {
		try {
			String url = AppletConfig.host + hostCreatePlayer;
			JSONParser parser = new JSONParser();
			URL obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "user=" + name + "&password=" + password;
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
			return false;
		}
	}

	private String login() {
		try {
			String url = AppletConfig.host + hostLogin;
			URL obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "user=" + name + "&password=" + password;
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
			JSONParser parser = new JSONParser();
			Object obj1 = parser.parse(response.toString());
			JSONObject jsonObject = (JSONObject) obj1;
			return jsonObject.toJSONString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "error";
	}

	private String getPlayers() {
		try {
			String url = AppletConfig.host + allPlayers;
			JSONParser parser = new JSONParser();
			URL obj = new URL(url);
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			Object obj1 = parser.parse(response.toString());
			JSONObject jsonObject = (JSONObject) obj1;
			return jsonObject.toJSONString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "error";
	}

	@Override
	public void run() {
		synchronized (this) {
			switch (typeRequest) {
			case REGISTER:
				result = register();
				break;
			case LOGIN:
				resultString = login();
				break;
			case GETPLAYERS:
				resultString = getPlayers();
				break;
			default:
				break;
			}
			notify();
		}
	}

	/**
	 * Nastavi heslo
	 * 
	 * @param password
	 *            nove heslo
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Nastavi meno hraca
	 * 
	 * @param name
	 *            nove meno
	 */
	public void setname(String name) {
		this.name = name;
	}

	/**
	 * Vrati result requestu
	 * 
	 * @return boolean
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * Nastavi typ requestu
	 * 
	 * @param typeRequest
	 *            novy typ
	 */
	public void setTypeRequest(TypeRequest typeRequest) {
		this.typeRequest = typeRequest;
	}

	/**
	 * Vrati vysledok requestu ako string
	 * 
	 * @return vysledok
	 */
	public String getResultString() {
		return resultString;
	}

}
