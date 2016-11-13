

angular.module('fileApp').controller('fileCtrl', function($scope){
	$scope.partialDownloadLink = 'http://localhost:9090/download?filename=';
    $scope.filename = '';

    $scope.uploadFile = function() {
        $scope.processDropzone();
    };

    $scope.reset = function() {
        $scope.resetDropzone();
    };
});