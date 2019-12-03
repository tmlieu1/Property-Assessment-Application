package ca.macewan.cmpt305;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.control.ProgressBar;

import java.util.ArrayList;
import java.io.IOException;

public class ApiEdmonton {
	private List<Property> propVals;
	private ProgressBar progress;
	
	public ApiEdmonton() {
		System.out.println("0: API");
		String urlCount = "https://data.edmonton.ca/resource/q7d6-ambg.json?$select=count(total_asmt)";
		progress = new ProgressBar();
		try {
			//count
			BufferedReader bc = getBR(urlCount);
			String count = bc.readLine();
			
			//progressbar
			
			//urlstring
			String urlString = "https://data.edmonton.ca/resource/q7d6-ambg.json?$limit=" + getCount(count);
			BufferedReader br = getBR(urlString);
			extractAPIData(br);
			System.out.println("0v: API");
		} catch (Exception e) {
			System.out.println("0x: API");
			e.printStackTrace();
		}
	}
	
	private ProgressBar getProgress() {
		return progress;
	}
	
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
	
	public BufferedReader getBR(String urlString) throws Exception{
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
	
	public List<Property> getAPIData(){
		return propVals;
	}
	
	
	
	public void extractAPIData(BufferedReader data) throws IOException, JSONException {
		System.out.println("2: API");
		propVals = new ArrayList<Property>();
		String line = null;
		StringBuilder sb = new StringBuilder();
		System.out.println("3: API");
		while ((line = data.readLine()) != null) {
			sb.append(line + '\n');
		}
		System.out.println("4: API");
		JSONArray jsonArray = new JSONArray(sb.toString());
		for (int i = 0; i < jsonArray.length(); i++) {
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
			String gar = json.getString("garage");
			Double latit = json.getDouble("latitude");
			Double longit = json.getDouble("longitude");
			Address addr = new Address(suite,house_num,street_name);
			Neighbourhood nbh = new Neighbourhood(neigh_id, neigh, ward);
			Location loc = new Location(latit,longit);
			Property prop = new Property(account, addr, ass_val, ass_clas, nbh, loc);
			propVals.add(prop);
			progress.setProgress(prop.size());
		}
	}
}