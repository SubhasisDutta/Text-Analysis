/**
 * Created by Subhasis on 4/22/2016.
 */
angular.module('app').controller('mvToolbarHeader', function($scope,$mdSidenav) {

    //Used to toggle the Side navigation
    $scope.toggleSideNav = function () {
        $mdSidenav('left').toggle();
    };

});
