'use strict';

angular.module('lifodApp')
    .factory('EndOfLife', function ($resource, DateUtils) {
        return $resource('api/endOfLifes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.endOfLifeDate = DateUtils.convertLocaleDateFromServer(data.endOfLifeDate);
                    data.buyingDate = DateUtils.convertLocaleDateFromServer(data.buyingDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.endOfLifeDate = DateUtils.convertLocaleDateToServer(data.endOfLifeDate);
                    data.buyingDate = DateUtils.convertLocaleDateToServer(data.buyingDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.endOfLifeDate = DateUtils.convertLocaleDateToServer(data.endOfLifeDate);
                    data.buyingDate = DateUtils.convertLocaleDateToServer(data.buyingDate);
                    return angular.toJson(data);
                }
            }
        });
    });
