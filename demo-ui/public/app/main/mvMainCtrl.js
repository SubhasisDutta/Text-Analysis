angular.module('app').controller('mvMainCtrl', function($scope,$location) {
    $scope.demoTypes =[
        {name:"Entity Extraction",url:"/entity-analysis"},
        {name:"Topic Finder",url:"/topic-analysis"},
        {name:"Sentiment Analysis",url:"/sentiment-analysis"},
        {name:"Text Search - Products",url:"/text-search"},
        {name:"Data Simulator",url:"/data-simulator"}];
    $scope.demoSelected = $scope.demoTypes[0];

    $scope.selectDemoType = function(demo){
        $scope.demoSelected = demo;
        $location.url(demo.url);
    };

});