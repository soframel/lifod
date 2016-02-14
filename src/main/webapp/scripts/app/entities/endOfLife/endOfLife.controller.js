'use strict';

angular.module('lifodApp')
    .controller('EndOfLifeController', function ($scope, EndOfLife, ParseLinks) {
        $scope.endOfLifes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EndOfLife.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.endOfLifes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EndOfLife.get({id: id}, function(result) {
                $scope.endOfLife = result;
                $('#deleteEndOfLifeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EndOfLife.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEndOfLifeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.endOfLife = {
                user: null,
                endOfLifeDate: null,
                buyingDate: null,
                product: null,
                productVersion: null,
                brand: null,
                reason: null,
                id: null
            };
        };
    });
