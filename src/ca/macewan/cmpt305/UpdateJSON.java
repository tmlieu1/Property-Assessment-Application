package ca.macewan.cmpt305;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UpdateJSON {
	JSONArray data;
	Gson theData;
	
	
	public UpdateJSON() {
		this.data = new JSONArray();
		this.theData = new GsonBuilder()
				.disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting()
				.serializeNulls()
				.create();
	}
	
	public void add(JSONObject property){
		this.data.put(property);
	}
	
	public void reset() {
		this.data = new JSONArray();
	}
	
	public void update() throws IOException {
		try(FileWriter json = new FileWriter("Properties.JSON")){
			json.write(data.toString());
			json.flush();
		} catch(IOException error) {
			error.printStackTrace();
		}
	}
	
}
