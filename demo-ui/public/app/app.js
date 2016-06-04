angular.module('app', ['ngResource', 'ngRoute','ngMaterial', 'ngMdIcons', 'ngMessages','ngSanitize']);

angular.module('app').config(function($routeProvider, $locationProvider,$mdIconProvider, $mdThemingProvider) {
  $locationProvider.html5Mode(true);
  $routeProvider
      .when('/text-search', {
          templateUrl: '/partials/text-search/text-search',
          controller: 'mvTextSearchCtrl'
      })
      .when('/sentiment-analysis', {
          templateUrl: '/partials/sentiment-analysis/sentiment-analysis',
          controller: 'mvSentimentAnalysisCtrl'
      })
      .when('/topic-analysis', {
          templateUrl: '/partials/topic-analysis/topic-analysis',
          controller: 'mvTopicAnalysisCtrl'
      })
      .when('/data-simulator', {
          templateUrl: '/partials/data-simulator/data-simulator',
          controller: 'mvDataSimulatorCtrl'
      })
      .when('/entity-analysis', {
        templateUrl: '/partials/entity-analysis/entity-analysis',
        controller: 'mvEntityAnalysisCtrl'
      })
      .when('/', {
          templateUrl: '/partials/entity-analysis/entity-analysis',
          controller: 'mvEntityAnalysisCtrl'
      });

  $mdIconProvider
        .defaultIconSet('/css/svg/avatars.svg', 128)
        .icon("google_plus", "/css/svg/google_plus.svg", 512)
        .icon("hangouts", "/css/svg/hangouts.svg", 512)
        .icon("twitter", "/css/svg/twitter.svg", 512)
        .icon("phone", "/css/svg/phone.svg", 512)
        .icon('menu', '/css/svg/menu.svg', 24);

  $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('red');
});

angular.module('app').run(function($rootScope, $location) {
  $rootScope.$on('$routeChangeError', function(evt, current, previous, rejection) {
    if(rejection === 'not authorized') {
      $location.path('/');
    }
  })
})
