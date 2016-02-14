'use strict';

angular.module('lifodApp').controller('EndOfLifeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'EndOfLife',
        function($scope, $stateParams, $modalInstance, entity, EndOfLife) {

        $scope.endOfLife = entity;
        $scope.load = function(id) {
            EndOfLife.get({id : id}, function(result) {
                $scope.endOfLife = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('lifodApp:endOfLifeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.endOfLife.id != null) {
                EndOfLife.update($scope.endOfLife, onSaveFinished);
            } else {
                EndOfLife.save($scope.endOfLife, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
