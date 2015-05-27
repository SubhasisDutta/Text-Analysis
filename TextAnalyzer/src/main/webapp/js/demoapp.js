'use strict';

/**
 * @ngdoc object
 * @name dmoapp
 * @requires $routeProvider
 * @requires democontroller
 * @requires ui.bootstrap
 *
 * @description
 * Root app, which routes and specifies the partial html and controller depending on the url requested.
 *
 */
var app = angular.module('discoveorApp',
    ['demoAppControllers', 'ngRoute', 'ui.bootstrap']).
    config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider.
                when('/findentity', {
                    templateUrl: '/partials/find_entity.html'//,
                    //controller: 'FindEntityCtrl'
                }).
                when('/comparenlp', {
                    templateUrl: '/partials/compare_nlp.html',
                    controller: 'CompareNlpCtrl'
                }).
                when('/findcategory', {
                    templateUrl: '/partials/find_category.html',
                    controller: 'FindCategoryCtrl'
                }).
                when('/serendionlpdocs', {
                    templateUrl: '/partials/serendionlp_docs.html'                    
                }).
                when('/', {
                    templateUrl: '/partials/home.html'
                }).
                otherwise({
                    redirectTo: '/'
                });
        }]);

/**
 * @ngdoc filter
 * @name startFrom
 *
 * @description
 * A filter that extracts an array from the specific index.
 *
 */
app.filter('startFrom', function () {
    /**
     * Extracts an array from the specific index.
     *
     * @param {Array} data
     * @param {Integer} start
     * @returns {Array|*}
     */
    var filter = function (data, start) {
        return data.slice(start);
    }
    return filter;
});


/**
 * @ngdoc constant
 * @name HTTP_ERRORS
 *
 * @description
 * Holds the constants that represent HTTP error codes.
 *
 */
app.constant('HTTP_ERRORS', {
    'UNAUTHORIZED': 401
});