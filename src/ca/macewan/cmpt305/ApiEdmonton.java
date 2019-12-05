package ca.macewan.cmpt305;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.io.IOException;

public class ApiEdmonton {
	private List<Property> propVals;
	private int limit;
	
	/**
	 * Constructs the Api object
	 */
	public ApiEdmonton() {
		String urlCount = "https://data.edmonton.ca/resource/q7d6-ambg.json?$select=count(total_asmt)";
		try {
			//count
			BufferedReader bc = getBR(urlCount);
			String count = bc.readLine();
			limit = getCount(count);
			
			//bufferedreader
			String urlString = "https://data.edmonton.ca/resource/q7d6-ambg.json?$offset=395000&$limit=" + limit;
			//String urlString = "https://data.edmonton.ca/resource/q7d6-ambg.json?$limit=" + limit;
			BufferedReader br = getBR(urlString);
			extractAPIData(br);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the amount of objects in the json file and returns it.
	 * @param strCount
	 * @return
	 */
	private int getCount(String strCount) {
		String [] cArray = strCount.split("");
		List<String> num = new ArrayList<String>();
		for(String c : cArray) {
			if (c.matches("[0-9]")){
				num.add(c);
			}
		}
		return Integer.parseInt(String.join("", num)) + 1;
	}
	
	/**
	 * Gets the bufferedreader from a url given a urlstring.
	 * @param urlString
	 * @return 
	 */
	private BufferedReader getBR(String urlString) throws Exception{
		BufferedReader br = null;
		try {	
			URL url = new URL(urlString);
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));		
			return br;
		}
		finally {
			if (br != null) {
			}
		}
	}
	
	/**
	 * Returns the API Data in the class
	 * @return
	 */
	public List<Property> getAPIData(){
		return propVals;
	}
	
	/**
	 * returns the string representation of the given reader
	 * @param reader
	 * @return
	 */
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}
	
	/**
	 * Extracts the data from the API as a local arraylist
	 * @param data
	 */
	private void extractAPIData(BufferedReader data) throws IOException, JSONException {
		propVals = new ArrayList<Property>();
		String jsonText = readAll(data);
		JSONArray jsonArray = new JSONArray(jsonText);
		for (int i = 0; i < jsonArray.length(); i++) {
			//fetching property info from the json and creating new properties from it
			JSONObject json = jsonArray.getJSONObject(i);
			Integer account = json.getInt("account_number");
			String suite;
			try {
				suite = json.getString("suite");
			}
			catch(Exception e) {
				suite = "";
			}
			String house_num;			
			try {
				house_num = json.getString("house_number");
			}catch(Exception e) {
				house_num = "";
			}
			String street_name;
			try {
				street_name = json.getString("street_name");
			} catch(Exception e) {
				street_name = "";
			}
			Integer ass_val = json.getInt("total_asmt");
			String ass_clas = json.getString("tax_class");
			String neigh_id;
			try {
				neigh_id = json.getString("neighbourhood_id");
			} catch (Exception e) {
				neigh_id = "";
			}
			String neigh;
			try {
				neigh = json.getString("neighbourhood");
			}catch (Exception e) {
				neigh = "";
			}
			String ward;
			try {
				ward = json.getString("ward");
			} catch(Exception e) {
				ward = "";
			}
			Double latit = json.getDouble("latitude");
			Double longit = json.getDouble("longitude");
			Address addr = new Address(suite,house_num,street_name);
			Neighbourhood nbh = new Neighbourhood(neigh_id, neigh, ward);
			Location loc = new Location(latit,longit);
			Property prop = new Property(account, addr, ass_val, ass_clas, nbh, loc);
			propVals.add(prop);
		}
	}
}