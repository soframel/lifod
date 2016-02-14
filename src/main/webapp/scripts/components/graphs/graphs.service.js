'use strict';

angular.module('lifodApp')
    .factory('Graphs', function ($resource) {
        return $resource('api/graphs/:what', {}, {
                'getDataByBrand': {
                	method: 'GET', 
                	params: {what: "dataByBrand", 
                		brand: ":brand"}                	
                }
            });
        });

/*transformResponse: function (data) { 
                		return data.statistic; 
                		}
             	transformResponse: function (data) { 
                		return data.statistic; 
                		}
                		*/   		
                		
