'use strict';

var app = angular.module('blueBank');

app.controller('LoginController', function($scope, LoginFactory, md5, $window, $http) {
    
    $scope.account = {};
    
    $scope.login = function (){

        $scope.account.password = md5.createHash($scope.account.password);
        LoginFactory.login({}, $scope.account, function (response) {
            $http.defaults.headers.common.Authorization = response.token;
            $window.sessionStorage.setItem('token', response.token);
            $window.sessionStorage.setItem('account', JSON.stringify(response));
            $window.location.reload();
        }, function (error) {
            console.log(error)
        })
    }
    
});

app.factory('LoginFactory', function ($resource) {

    return $resource(endpoint + '/api/login', {}, {

        login:  { method: 'POST' }
    });
});