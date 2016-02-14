'use strict';

angular.module('lifodApp')
    .controller('MainController', function ($scope, Principal, Statistics) {


    	Statistics.getUsers({}, function (result) {   	    		
    		$scope.statisticsNbUsers = result.statistic;      		
        });
    	Statistics.getDevices({}, function (result) {    		
            $scope.statisticsNbDevices = result.statistic;      		
        });
    	
    	Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
});
