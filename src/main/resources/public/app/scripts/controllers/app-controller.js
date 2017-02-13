'use strict';

var app = angular.module('blueBank');

app.controller('AppController', function( $scope, $window) {

    $scope.account = $window.sessionStorage.getItem('account');

    if($scope.account == null){
        $scope.account = {};
        $scope.account.logged = false;
    }else{
        $scope.account = JSON.parse($scope.account);
    }

    $scope.logout = function () {
        $window.sessionStorage.removeItem('account');
        $window.location.reload();
    }

});