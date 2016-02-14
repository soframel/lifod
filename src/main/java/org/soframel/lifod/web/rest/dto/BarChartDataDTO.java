package org.soframel.lifod.web.rest.dto;

import java.util.List;

/** 
 * Used For serializing data for Chartsjs
 * Example: 
 *
var data = {
labels: ["January", "February", "March", "April", "May", "June", "July"],
datasets: [
    {
        label: "My First dataset",
        fillColor: "rgba(220,220,220,0.5)",
        strokeColor: "rgba(220,220,220,0.8)",
        highlightFill: "rgba(220,220,220,0.75)",
        highlightStroke: "rgba(220,220,220,1)",
        data: [65, 59, 80, 81, 56, 55, 40]
    },
    {
        label: "My Second dataset",
        fillColor: "rgba(151,187,205,0.5)",
        strokeColor: "rgba(151,187,205,0.8)",
        highlightFill: "rgba(151,187,205,0.75)",
        highlightStroke: "rgba(151,187,205,1)",
        data: [28, 48, 40, 19, 86, 27, 90]
    }
]
};
**/
public class BarChartDataDTO {
	private List<String> labels;
	private List<ChartDataSetDTO> datasets;
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<ChartDataSetDTO> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<ChartDataSetDTO> datasets) {
		this.datasets = datasets;
	}
	
	
}

