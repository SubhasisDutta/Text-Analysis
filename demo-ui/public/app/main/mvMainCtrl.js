angular.module('app').controller('mvMainCtrl', function($scope,$location) {
    $scope.demoTypes =[
        {name:"Entities",url:"/entity-analysis"},
        {name:"Topics",url:"/topic-analysis"},
        {name:"Sentiments",url:"/sentiment-analysis"},
        {name:"Text Search",url:"/text-search"}];
    $scope.demoSelected = $scope.demoTypes[0];

    $scope.selectDemoType = function(demo){
        $scope.demoSelected = demo;
        $location.url(demo.url);
    };

});