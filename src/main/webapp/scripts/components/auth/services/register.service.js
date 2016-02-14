'use strict';

angular.module('lifodApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


