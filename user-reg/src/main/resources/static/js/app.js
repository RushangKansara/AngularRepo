var app = angular.module('userregistrationsystem', [ 'ngRoute', 'ngResource' ]);

app.config(function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl : '/template/home.html',
		controller : 'homeController'
	})
	.when('/list-all-users', {
		templateUrl : '/template/listuser.html',
		controller : 'listUserController'
	}).when('/register-new-user',{
		templateUrl : '/template/userregistration.html',
		controller : 'registerUserController'
	}).when('/update-user/:id',{
		templateUrl : '/template/userupdation.html' ,
		controller : 'usersDetailsController'
	})
	.when('/login',{
                        templateUrl : '/login/login.html',
                        controller : 'loginController'
	})
	.when('/logout',{
        controller : 'logoutController'
	})
	.otherwise({
	        redirectTo : '/login'
	});
	
});

app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
}]);