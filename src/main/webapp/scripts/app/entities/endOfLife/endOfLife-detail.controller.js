'use strict';

angular.module('lifodApp')
    .controller('EndOfLifeDetailController', function ($scope, $rootScope, $stateParams, entity, EndOfLife) {
        $scope.endOfLife = entity;
        $scope.load = function (id) {
            EndOfLife.get({id: id}, function(result) {
                $scope.endOfLife = result;
            });
        };
        var unsubscribe = $rootScope.$on('lifodApp:endOfLifeUpdate', function(event, result) {
            $scope.endOfLife = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
