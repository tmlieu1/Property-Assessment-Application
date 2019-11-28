package ca.macewan.cmpt305;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.io.IOException;

public class ApiEdmonton {
	
	public BufferedReader getUrl() throws IOException{
		URL url =  new URL("https://data.edmonton.ca/resource/q7d6-ambg.json?$limit=401117");
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		return br;
	}
	
	public List<Property> getExtractedAPIData(BufferedReader data) throws IOException, JSONException {
		List<Property> propVals = new ArrayList<Property>();
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = data.readLine()) != null) {
			sb.append(line + '\n');
		}
		JSONArray jsonArray = new JSONArray(sb.toString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			Integer account = json.getInt("account_number");
			String suite = null;
			try {
				suite = json.getString("suite");
			} catch (Exception e) {
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
			String gar = json.getString("garage");
			Double latit = json.getDouble("latitude");
			Double longit = json.getDouble("longitude");
			Address addr = new Address(suite,house_num,street_name);
			Neighbourhood nbh = new Neighbourhood(neigh_id, neigh, ward);
			Location loc = new Location(latit,longit);
			Property prop = new Property(account, addr, ass_val, ass_clas, nbh, loc);
			propVals.add(prop);
		}
		System.out.println(jsonArray.length());
		return propVals;
	}
}
