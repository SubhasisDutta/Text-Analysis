/**
 * Created by Subhasis on 5/31/2016.
 */
angular.module('app').controller('mvEntityAnalysisCtrl', function($scope,$resource) {
    $scope.searchQuery ="";
    $scope.queryAvailableFlag=false;
    $scope.resultAvailable = false;
    $scope.tabIndex = 0;

    $scope.getSearchResults = function(searchQuery){
        //console.log(searchQuery);
        $scope.queryAvailableFlag=false;
        $scope.resultAvailable = false;
        if(searchQuery.trim() === ""){
            $scope.queryAvailableFlag = false;
            $scope.resultAvailable = false;
        }else{
            $scope.queryAvailableFlag = true;
        }
        //Go get the reusults if any one gives result make it available
        if($scope.queryAvailableFlag){
            var entityResource = $resource("/api/find-entity");

            var response = entityResource.save({text:searchQuery},function(){
                console.log(response);
                $scope.resultAvailable = true;
            });
        }
    };
    $scope.isBeginingScreen = function(){
        if($scope.queryAvailableFlag === false && $scope.resultAvailable === false){
            return true;
        }
        return false;
    };
    $scope.isQueryProcessing = function(){
        if($scope.queryAvailableFlag === true && $scope.resultAvailable === false){
            return true;
        }
        return false;
    };
    $scope.isResultAvailable = function(){
        return $scope.queryAvailableFlag && $scope.resultAvailable;
    };
    $scope.resetPage =function(){
        $scope.queryAvailableFlag=false;
        $scope.resultAvailable = false;
    };
});