'use strict';

angular.module('lifodApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('endOfLife', {
                parent: 'entity',
                url: '/endOfLifes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lifodApp.endOfLife.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/endOfLife/endOfLifes.html',
                        controller: 'EndOfLifeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('endOfLife');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('endOfLife.detail', {
                parent: 'entity',
                url: '/endOfLife/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lifodApp.endOfLife.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/endOfLife/endOfLife-detail.html',
                        controller: 'EndOfLifeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('endOfLife');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EndOfLife', function($stateParams, EndOfLife) {
                        return EndOfLife.get({id : $stateParams.id});
                    }]
                }
            })
            .state('endOfLife.new', {
                parent: 'endOfLife',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/endOfLife/endOfLife-dialog.html',
                        controller: 'EndOfLifeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    user: null,
                                    endOfLifeDate: null,
                                    buyingDate: null,
                                    product: null,
                                    productVersion: null,
                                    brand: null,
                                    reason: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('endOfLife', null, { reload: true });
                    }, function() {
                        $state.go('endOfLife');
                    })
                }]
            })
            .state('endOfLife.edit', {
                parent: 'endOfLife',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/endOfLife/endOfLife-dialog.html',
                        controller: 'EndOfLifeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EndOfLife', function(EndOfLife) {
                                return EndOfLife.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('endOfLife', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
