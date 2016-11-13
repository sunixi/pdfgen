<!DOCTYPE html>
<html ng-app="fileApp">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- Load Dropzone CSS -->
    <link href="css/dropzone/basic.css" rel="stylesheet" />
    <link href="css/dropzone/dropzone.css" rel="stylesheet" />

</head>

<body ng-controller="fileCtrl" ng-cloak>

    <div class="col-xs-6 col-xs-offset-3">
        <div class="well">
            <form action="" class="dropzone" dropzone="" id="dropzone">
                <div class="dz-default dz-message">
                </div>
            </form>
        </div>
        <div class="pull-right">
            <button class="btn btn-success" ng-click="uploadFile()">Upload File</button>
            <button class="btn btn-danger" ng-click="reset()">Reset Dropzone</button>
        </div>
        <div>
            <form>
                <label>File to download</label>
                <input ng-model="filename" type="text" placeholder="Filename" />
                <a class="btn btn-primary" ng-href="{{ partialDownloadLink + filename }}">Download File</a>
            </form>
        </div>
    </div>

    <!-- Load Angular scripts-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.17/angular.min.js"></script>
    <script src="js/app.js"></script>
    <script src="js/fileAppController.js"></script>
    <script src="js/fileAppDirectives.js"></script>

    <!-- Load Dropzone JS -->
    <script src="js/dropzone/dropzone.js"></script>

    <script type="text/javascript">
        Dropzone.autoDiscover = false;
    </script>

</body>
</html>