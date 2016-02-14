'use strict';

angular.module('lifodApp')
    .controller('GraphsController', function ($scope, Principal, Graphs) {

    	Chart.defaults.global.legendTemplate =  "device lifespan (in months)"; //"<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"";
    	

    	Graphs.getDataByBrand({brand: "Google"}, function (result) {   	    		
    				
    		console.log("JSON data received: "+JSON.stringify(result));
    		
    		//Test data
    		//result="{\"labels\":[\"Philips\",\"AEG\",\"Google\"],\"datasets\":[{\"label\":\"First\",\"data\":[108, 302, 42]}]}";
    		//{"labels":["Philips","AEG","Google"],"datasets":[{"label":"Average","data":[71,24,31]},{"label":"Min","data":[71,24,0]},{"label":"Max","data":[71,24,61]}]}
    		//result=JSON.parse(result);
    		
    		var maxMap=new Map();
    		var minMap=new Map();
    		
    		//process data before rendering
    		transformDataForStyling(result, minMap, maxMap);
    		
    		var ctx = document.getElementById("graphCanvas").getContext("2d");
    		var myBarChart = new Chart(ctx).Bar(result, {
    			legendTemplate:"test <%=name>",
    			//tooltipTemplate: "<%if (label){%><%=label%>: <%}%><%= value %> (min=<%=minMap.get(label)%>, max=<%=maxMap.get(label)%>)"
    			tooltipTemplate: function(chartData){
    				  return "Average: " + chartData.value+" days (min: "+minMap.get(chartData.label)+" days, max: "+maxMap.get(chartData.label)+" days)";
    				}
    		});

        });
    	
    	function transformDataForStyling(completeData, minMap, maxMap){
    		var datasets=completeData.datasets;
    		var labels=completeData.labels;
    		
    		//3 datasets with 3 colors
    		/*for(var i=0;i<datasets.length;i++){
    			var dataSet=datasets[i];
    			if(dataSet.label=="Average")
    				dataSet.fillColor= "rgba(220,220,220,1)"; 
    			else if(dataSet.label=="Max")
    				dataSet.fillColor= "rgba(220,220,65,0.5)"; 
    			else if(dataSet.label=="Min")
    				dataSet.fillColor= "rgba(137,220,220,0.5)"; 
    			
    			dataSet.strokeColor= "rgba(220,220,220,0.8)";
    			dataSet.highlightFill= "rgba(220,220,220,0.75)";
    			dataSet.highlightStroke= "rgba(220,220,220,1)";
    		}*/
    		
    		//one dataSet + change labels
    		//first one = Average
    		var dataSet=datasets[0];
    		dataSet.fillColor= "rgba(137,220,220,0.5)" //"rgba(220,220,220,1)"; 
    		dataSet.strokeColor= "rgba(220,220,220,0.8)";
        	dataSet.highlightFill= "rgba(220,220,220,0.75)";
        	dataSet.highlightStroke= "rgba(220,220,220,1)";
    		
        	//minMap
        	var dataSetMin=datasets[1].data;
        	for(var i=0;i<dataSetMin.length;i++){
        		var data=dataSetMin[i];
        		minMap.set(labels[i], data);
        	}
        	console.log("minMap="+minMap);
        	
        	//Max map
        	var dataSetMax=datasets[2].data;
        	for(var i=0;i<dataSetMax.length;i++){
        		var data=dataSetMax[i];
        		maxMap.set(labels[i], data);
        	}        	
        	console.log("maxMap="+maxMap);
        	
        	//remove min and max from data
        	datasets.splice(1, 2);	
    	}
    	
});
