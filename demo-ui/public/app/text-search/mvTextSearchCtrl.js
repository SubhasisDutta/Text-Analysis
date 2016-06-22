/**
 * Created by Subhasis on 5/31/2016.
 */
angular.module('app').controller('mvTextSearchCtrl', function($scope,$resource) {
    $scope.searchQuery ="";
    $scope.queryAvailableFlag=false;
    $scope.resultAvailable = false;
    $scope.tabIndex = 0;

    $scope.noresultsFound = false;

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
            var googleSearchResource = $resource("/api/googlesearch/:query");
            $scope.googleSearchResults = googleSearchResource.get({query:searchQuery},function(){
                $scope.resultAvailable = true;
                //console.log($scope.googleSearchResults.items[0]);
            });

            var bingSearchResource = $resource("/api/bingsearch/:query");
            $scope.bingSearchResults = bingSearchResource.get({query:searchQuery},function(){
                $scope.resultAvailable = true;
                //console.log($scope.bingSearchResults.d.results[0]);
                //console.log($scope.googleSearchResults.items[0]);
            });

            /*var searchQueryResource = $resource("/api/search/:query");
            $scope.searchQuerySearchResults = searchQueryResource.get({query:searchQuery},function(){
                $scope.resultAvailable = true;

                if(searchQuerySearchResults.resultCount === 0 || searchQuerySearchResults.resultCount === undefined){
                    $scope.noresultsFound = true;
                }else{
                    $scope.noresultsFound = false;
                }
                //console.log($scope.searchQuerySearchResults);
            });*/
            /*var searchQueryExpansionResource = $resource("/api/queryexpansion/:query");
             $scope.searchQueryExpansionSearchResults = searchQueryExpansionResource.get({query:searchQuery},function(){
             $scope.resultAvailable = true;
             console.log($scope.searchQueryExpansionSearchResults);
             });
             var searchClusterResource = $resource("/api/clustering/:query");
             $scope.searchClusterSearchResults = searchClusterResource.get({query:searchQuery},function(){
             $scope.resultAvailable = true;
             console.log($scope.searchClusterSearchResults);
             });*/
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

});