package org.soframel.lifod.web.rest.dto;

import java.util.List;

/**
 * Used For serializing data for Chartsjs
 * Example: 
 *   {
        label: "My Second dataset",
        
        data: [28, 48, 40, 19, 86, 27, 90]
    }
    
    Removed (added in js): 
    fillColor: "rgba(151,187,205,0.5)",
        strokeColor: "rgba(151,187,205,0.8)",
        highlightFill: "rgba(151,187,205,0.75)",
        highlightStroke: "rgba(151,187,205,1)",
    
 * @author sophie
 *
 */
public class ChartDataSetDTO {
	private String label;
	private List<Integer> data;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}	
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	
	
}
