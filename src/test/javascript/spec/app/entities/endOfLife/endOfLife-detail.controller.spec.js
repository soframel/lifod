'use strict';

describe('EndOfLife Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEndOfLife;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEndOfLife = jasmine.createSpy('MockEndOfLife');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EndOfLife': MockEndOfLife
        };
        createController = function() {
            $injector.get('$controller')("EndOfLifeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'lifodApp:endOfLifeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
