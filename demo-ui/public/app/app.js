angular.module('app', ['ngResource', 'ngRoute','ngMaterial', 'ngMdIcons', 'ngMessages']);

angular.module('app').config(function($routeProvider, $locationProvider,$mdIconProvider, $mdThemingProvider) {
  
  $locationProvider.html5Mode(true);
  $routeProvider
      .when('/', { 
        templateUrl: '/partials/main/main-content',
        controller: 'mvMainContentCtrl'
      });

  $mdIconProvider
        .defaultIconSet('/css/svg/avatars.svg', 128)
        .icon("google_plus", "/css/svg/google_plus.svg", 512)
        .icon("hangouts", "/css/svg/hangouts.svg", 512)
        .icon("twitter", "/css/svg/twitter.svg", 512)
        .icon("phone", "/css/svg/phone.svg", 512)
        .icon('menu', '/css/svg/menu.svg', 24);

  $mdThemingProvider.theme('default')
        .primaryPalette('orange')
        .accentPalette('green');
});

angular.module('app').run(function($rootScope, $location) {
  $rootScope.$on('$routeChangeError', function(evt, current, previous, rejection) {
    if(rejection === 'not authorized') {
      $location.path('/');
    }
  })
})
