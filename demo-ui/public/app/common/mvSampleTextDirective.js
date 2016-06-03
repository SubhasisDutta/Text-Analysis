/**
 * Created by Subhasis on 5/31/2016.
 */
angular.module('app').directive('sdBlockListDisplay', function() {
    return {
        restrict : 'E',
        scope:{
            dataobj : '='
        },
        templateUrl: '/partials/common/block-list-display'
    };
});