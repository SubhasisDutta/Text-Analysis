/**
 * Created by Subhasis on 5/31/2016.
 */
angular.module('app').controller('mvTopicAnalysisCtrl', function($scope,$resource,$sce) {
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
        if($scope.queryAvailableFlag){
            var topicResource = $resource("/api/find-topics");
            $scope.topicBlocks = [];
            var response = topicResource.save({text:searchQuery},function(){
                var text = response.result.text;
                var keywords = [];
                for(var i in response.result.keywords){
                    keywords.push(response.result.keywords[i]);
                }
                keywords =uniqueStrings(keywords)
                    .sort(function(a, b) {return b.length - a.length;});
                var obj = {
                    displayName : "Keywords",
                    contents : keywords,
                    bgColor : "#03A9F4"
                };
                $scope.topicBlocks.push(obj);
                var topics =[]
                for(var i in response.result.topics){
                    topics.push(response.result.topics[i]);
                }
                topics =uniqueStrings(topics)
                    .sort(function(a, b) {return b.length - a.length;});

                var obj = {
                    displayName : "Topics",
                    contents : topics,
                    bgColor : "#EF6C00"
                };
                for (var j in keywords) {
                    var words = keywords[j].split(' ');
                    for(var k in words){
                        var re = new RegExp(words[k], 'g');
                        text = text.replace(re, "<span style='background-color:#03A9F4'>" + words[k] + "</span>");
                    }
                }
                $scope.topicBlocks.push(obj);

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