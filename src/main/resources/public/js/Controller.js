'use strict'

var module = angular.module('products.controllers', []);
module.controller("Controller", [ "$scope", "Service",
    function($scope, Service) {
        $scope.mode = [];

        Service.listAll().then(function(value) {
            $scope.products = value.data;
        });

        $scope.updateProduct = function(index) {
            $scope.mode = [];
            $scope.mode[index] = "edit";
        };

        $scope.cancelEdit = function() {
            $scope.mode = [];
        };

        $scope.saveProduct = function(index, product) {
            Service.saveProduct(product).then(function(value) {
                $scope.products[index] = value.data;
                $scope.mode = [];
            });
        };

        $scope.addProduct = function() {
            Service.saveProduct($scope.product).then(function(value) {
                $scope.products.push(value.data);
            });
        };

        $scope.deleteProduct = function(index) {
            Service.deleteProduct($scope.products[index]).then(function(value) {
                $scope.products.splice(index, 1);
                $scope.mode = [];
            });
        };
    }
]);