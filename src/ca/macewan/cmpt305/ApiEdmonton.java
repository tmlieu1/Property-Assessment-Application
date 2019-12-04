package ca.macewan.cmpt305;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.io.IOException;

public class ApiEdmonton {
	private List<Property> propVals;
	
	public ApiEdmonton() {
		System.out.println("0: API");
		String urlCount = "https://data.edmonton.ca/resource/q7d6-ambg.json?$select=count(total_asmt)";
		try {
			//count
			BufferedReader bc = getbufferRead(urlCount);
			String count = bc.readLine();
			
			//urlstring
			String urlString = "https://data.edmonton.ca/resource/q7d6-ambg.json?$limit=" + getCount(count);
			BufferedReader bufferRead = getbufferRead(urlString);
			
			extractAPIData(bufferRead);
			System.out.println("0v: API");
		} catch (Exception e) {
			System.out.println("0x: API");
			e.printStackTrace();
		}
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
	
	public BufferedReader getbufferRead(String urlString) throws Exception{
		BufferedReader bufferRead = null;
		try {	
			URL url = new URL(urlString);
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			bufferRead = new BufferedReader(new InputStreamReader(is));		
			return bufferRead;
		}
		finally {
			if (bufferRead != null) {
			}
		}
	}
	
	public List<Property> getAPIData(){
		return propVals;
	}
	
//	private void createLocalJSON(Property data) throws IOException {
//		FileWriter localJSON = new FileWriter("Properties.json");
//		JsonObject
////		Gson gson = new GsonBuilder()
////				.disableHtmlEscaping()
////				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
////				.setPrettyPrinting()
////				.serializeNulls()
////				.create();
//		
//		localJSON.write(gson.toJson(data));
//		localJSON.flush();
//		System.out.println(gson.toJson(data));
//	}
	
	public void extractAPIData(BufferedReader data) throws IOException, JSONException {
		System.out.println("2: API");
//		Gson gson = new Gson();
		propVals = new ArrayList<Property>();
		String line = null;
		StringBuilder stringBuild = new StringBuilder();
		System.out.println("3: API");
		while ((line = data.readLine()) != null) {
			stringBuild.append(line + '\n');
//			propVals.add(gson.fromJson(line, Property.class));
		}
		System.out.println("4: API");
		
//		File theFile = new File("Properties.json");
//		theFile.createNewFile();
		
//		FileWriter localJSON = new FileWriter("Properties.json");
		
		JSONArray jsonArray = new JSONArray(stringBuild.toString());
		
		for (int i = 0; i < jsonArray.length(); i++) {
//			Property convertedData = gson.fromJson(jsonArray.toJSONObject(JsonArray), Property.class);
			JSONObject json = jsonArray.getJSONObject(i);
//			localJSON.write(json.toString());
//			localJSON.flush();
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
//			createLocalJSON(prop);
			propVals.add(prop);
		}
		
//		localJSON.close();
	}
}