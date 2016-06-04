/**
 * Created by Subhasis on 5/31/2016.
 */
angular.module('app').controller('mvSentimentAnalysisCtrl', function ($scope, $resource,$sce) {
    $scope.searchQuery = "";
    $scope.queryAvailableFlag = false;
    $scope.resultAvailable = false;
    $scope.tabIndex = 0;
    var availableSentimentLevel = $resource("/api/sentiment-level").get();

    function getColor(score){
        for(var i in availableSentimentLevel.types){
            var o = availableSentimentLevel.types[i];
            if(o.score === score){
                return o.color;
            }
        }
        return "#FFF";
    }
    $scope.getSearchResults = function (searchQuery) {
        //console.log(searchQuery);


        $scope.queryAvailableFlag = false;
        $scope.resultAvailable = false;
        if (searchQuery.trim() === "") {
            $scope.queryAvailableFlag = false;
            $scope.resultAvailable = false;
        } else {
            $scope.queryAvailableFlag = true;
        }
        //Go get the reusults if any one gives result make it available
        if ($scope.queryAvailableFlag) {
            var sentimentResource = $resource("/api/find-sentiments");
            $scope.sentimentBlocks = [];

            sentimentResource.save({text: searchQuery}, function (response) {
                var text = searchQuery;
                for(var i in availableSentimentLevel.types){
                    var level = availableSentimentLevel.types[i];
                    var obj = {
                        displayName: level.level,
                        contents: [" "],
                        bgColor: level.color
                    };
                    $scope.sentimentBlocks.push(obj);
                }

                for(var i in response.result){
                    var sentence = response.result[i].sentence;
                    var score = response.result[i].score;
                    if(score !==0){
                        text = text.replace(sentence, "<span style='background-color: " + getColor(score) + "'>" + sentence + "</span>");
                    }
                }

                $scope.formatedResult = $sce.trustAsHtml(text);
                $scope.resultAvailable = true;
            });

        }
    };
    $scope.isBeginingScreen = function () {
        if ($scope.queryAvailableFlag === false && $scope.resultAvailable === false) {
            return true;
        }
        return false;
    };
    $scope.isQueryProcessing = function () {
        if ($scope.queryAvailableFlag === true && $scope.resultAvailable === false) {
            return true;
        }
        return false;
    };
    $scope.isResultAvailable = function () {
        return $scope.queryAvailableFlag && $scope.resultAvailable;
    };
    $scope.resetPage = function () {
        $scope.queryAvailableFlag = false;
        $scope.resultAvailable = false;
    };

    $scope.displayBlock = function (arr) {
        if (arr.length > 0)
            return true;
        return false;
    }
});