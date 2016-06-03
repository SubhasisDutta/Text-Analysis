/**
 * Created by Subhasis on 5/31/2016.
 */
angular.module('app').controller('mvEntityAnalysisCtrl', function($scope,$resource,$sce) {
    $scope.searchQuery ="";
    $scope.queryAvailableFlag=false;
    $scope.resultAvailable = false;
    $scope.tabIndex = 0;

    function uniqueStrings(a) {
        return a.sort().filter(function(item, pos, ary) {
            return !pos || item != ary[pos - 1];
        })
    };
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
            $scope.entityBlocks = [];

            var response = entityResource.save({text:searchQuery},function(){

                var availableEntity = ['currency','date','location','organization','percent','person','time'];
                var availableColors = ['#00E676','#EF6C00','#CDDC39','#03A9F4','#9C27B0','#F44336','#E91E63'];
                var text = response.sentence;
                for(var i in availableEntity){
                    var cont =uniqueStrings(response[availableEntity[i]])
                                .sort(function(a, b) {return b.length - a.length;});
                    var obj = {
                        displayName : availableEntity[i].charAt(0).toUpperCase() + availableEntity[i].slice(1),
                        contents : cont,
                        bgColor : availableColors[i]
                    };
                    for(var j in obj.contents){
                        var word = obj.contents[j];
                        var re = new RegExp(word, 'g');
                        text = text.replace(re, "<span style='background-color: "+obj.bgColor+"'>"+word+"</span>");
                    }
                    $scope.entityBlocks.push(obj);
                }
                //console.log(response);
                $scope.formatedResult = $sce.trustAsHtml(text);
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

    $scope.displayBlock = function(arr){
        if(arr.length > 0)
            return true;
        return false;
    }
});