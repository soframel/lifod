'use strict';

angular.module('lifodApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('graphs', {
                parent: 'site',
                url: '/graphs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'global.menu.graphs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/graphs/graphs.html',
                        controller: 'GraphsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('graphs');
                        return $translate.refresh();
                    }]
                }
            });
    });
