'use strict'

angular.module('products.services', []).factory('Service',
    [ "$http", function($http) {
        var service = {};
        service.listAll = function() {
            return $http.get("/products");
        }
        service.saveProduct = function(product) {
            return $http.post("/products", product);
        }
        service.deleteProduct = function(product) {
            return $http.delete("/products/" + product.id);
        }
        return service;
    } ]);