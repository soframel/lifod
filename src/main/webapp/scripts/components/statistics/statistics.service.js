'use strict';

angular.module('lifodApp')
    .factory('Statistics', function ($resource) {
        return $resource('api/statistics/:what', {}, {
                'getUsers': {
                	method: 'GET', 
                	params: {what: "users"}                	
                },
                'getDevices': {
                	method: 'GET', 
                	params: {what: "devices"}                 
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
                		
