package ca.macewan.cmpt305;


import java.util.HashMap;
import java.util.Map;

import javafx.collections.transformation.FilteredList;
public class ChartBuilder {
	private FilteredList<Property> filteredData;
	
	
	public ChartBuilder(FilteredList<Property> filteredData1) {
		this.filteredData = filteredData1;
	}

	public Map<String, Integer> createMapAssClass() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		for (int i = 0; i < this.filteredData.size(); i++) {
			if (this.filteredData.get(i).getAssessedClass() == null) {
				continue;
			}
			try {
				name = this.filteredData.get(i).getAssessedClass();
				int val = map.get(this.filteredData.get(i).getAssessedClass());
				map.replace(name,++val);
				
			} catch (Exception e) {
				name = this.filteredData.get(i).getAssessedClass();
				map.put(name, 1);
			}
		}
		return map;
	}
	
	public Map<String, Integer> createMapWard() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		for (int i = 0; i < this.filteredData.size()-1; i++) {
			if (this.filteredData.get(i).getNeighbourhood().getNBHWard().contentEquals("")) {
				break;
				//continue;
			}
			try {
				name = this.filteredData.get(i).getNeighbourhood().getNBHWard();
				int val = map.get(this.filteredData.get(i).getNeighbourhood().getNBHWard());
				map.replace(name,++val);
				
			} catch (Exception e) {
				name = this.filteredData.get(i).getNeighbourhood().getNBHWard();
				map.put(name, 1);
			}
		}
		return map;
	}
	
	public Map<String, Integer> createMapNeigh() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		for (int i = 0; i < this.filteredData.size(); i++) {
			if (this.filteredData.get(i).getNBHName().contentEquals("")) {
				continue;
			}
			try {
				name = this.filteredData.get(i).getNBHName();
				int val = map.get(this.filteredData.get(i).getNBHName());
				map.replace(name,++val);
				
			} catch (Exception e) {
				name = this.filteredData.get(i).getNBHName();
				map.put(name, 1);
			}
		}
		return map;
	}
}
