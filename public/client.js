var module = angular.module('myapp', ['ngResource']);

module.factory('Client', function ($resource) {
    return $resource(':id/client', {id: '@firstname'});
})
    .controller('ClientsController', function ($scope, Client) {
        var url = function () {
            return {id: $scope.selectedRow || '1'};
        };
        var update = function () {
            $scope.clients = Client.query(url());
        };
        $scope.clear = function () {
            $scope.namefirst = "";
            $scope.lastnames = "";
            $scope.address = "";
            $scope.phone = "";
            $scope.email = "";
            $scope.date = "";
            $scope.selectedRow = "";
            $scope.deleteButtonDisabled = true;

        };
        $scope.selectClient = function (selectedClient) {

            $scope.namefirst = selectedClient.firstName;
            $scope.lastnames = selectedClient.lastName;
            $scope.address = selectedClient.address;
            $scope.phone = selectedClient.phone;
            $scope.email = selectedClient.email;
            $scope.date = selectedClient.date;
            $scope.selectedRow = selectedClient.id;
            $scope.deleteButtonDisabled = false;
            $scope.clientSelect = selectedClient;
            $scope.updatecars();
            $scope.$digest();

        };
        $scope.add = function () {
            var client = new Client();
            client.firstName = $scope.namefirst;
            client.lastName = $scope.lastnames;
            client.address = $scope.address;
            client.phone = $scope.phone;
            client.email = $scope.email;
            client.date = $scope.date;
            client.$save(url(), function () {
                $scope.namefirst = "";
                $scope.lastnames = "";
                $scope.address = "";
                $scope.phone = "";
                $scope.email = "";
                $scope.date = "";
                update();
            });
        };
        $scope.delete_client = function () {
            var client = new Client();
            client.id = $scope.clientSelect.id;
            client.$delete(url(), function () {
                update();
                $scope.deleteButtonDisabled = true;
            });
        };
        update();

    });

module.factory('Car', function ($resource) {
    return $resource('jhoeller/client/:carid/cars', {carid: '@carid'});
})
    .controller('CarController', function ($scope, Car) {
        var urlcar = function () {
            return {carid: $scope.selectedRow};
        };
        var urldelcar = function () {
            return {carid: $scope.selectedRowCar}
        }
        var updatecar = function () {
            $scope.cars = Car.query(urlcar());
            $scope.make = "";
            $scope.model = "";
            $scope.year = "";
            $scope.vin = "";
            $scope.deleteButtonDisabled = true;
        };
        $scope.updatecars = function () {
            updatecar();
        };

        $scope.selectCar = function (selectedCar) {
            $scope.selectedRowCar = selectedCar.id;
            $scope.carSelect = selectedCar;
            $scope.make = selectedCar.make;
            $scope.model = selectedCar.model;
            $scope.year = selectedCar.year;
            $scope.vin = selectedCar.vin;
            $scope.deleteButtonDisabled = false;
        };
        $scope.addcar = function () {
            var car = new Car();
            car.make = $scope.make;
            car.model = $scope.model;
            car.year = $scope.year;
            car.vin = $scope.vin;
            car.$save(urlcar(), function () {
                $scope.make = "";
                $scope.model = "";
                $scope.year = "";
                $scope.vin = "";
                updatecar();
            });
        };
        $scope.delete_car = function () {
            var car = new Car();
            car.id = $scope.carSelect.id;
            car.$delete(urldelcar(), function () {
                updatecar();
                $scope.deleteButtonDisabled = true;
            });
        };
        updatecar();

        //$scope.$watch($scope.clientSelect,$scope.updatecars())
    });

module.factory('Order', function ($resource) {
    return $resource('jhoeller/client/:orderid/orders', {orderid: '@orderid'});
})
    .controller('OrderController', function ($scope, Order) {
        var urlord = function () {
            return {orderid: $scope.selectedRowCar};
        };
        $scope.colors = [
            {name:'Complited'},
            {name:'In Progress'},
            {name:'Cancelled'}
        ];
        var urldelord = function () {
            return {orderid: $scope.selectedRowOrder}
        }
        var updateorder = function () {
            $scope.orders = Order.query(urlord());
            $scope.dateOrder = "";
            $scope.amount = "";
            $scope.status = "";
            $scope.deleteButtonDisabled = true;
        };
        $scope.updateorders = function () {
            updateorder();
        };
        $scope.selectOrder = function (selectOrder) {
            $scope.selectedRowOrder = selectOrder.id;
            $scope.orderSelect = selectOrder;
            $scope.dateOrder = selectOrder.date;
            $scope.amount = selectOrder.amount;
            $scope.status.name = selectOrder.status;
            $scope.deleteButtonDisabled = false;
        };
        $scope.addorder = function () {
            var clientOrder = new Order();
            clientOrder.date = $scope.dateOrder.value;
            clientOrder.amount = $scope.amount;
            clientOrder.status = $scope.status.name;
            clientOrder.$save(urlord(), function () {
                updateorder();
            });
        };
        $scope.deleteorder = function () {
            var order = new Order();
            order.id = $scope.orderSelect.id;
            order.$delete(urldelord(), function () {
                updateorder();
                $scope.deleteButtonDisabled = true;
            });
        };
        updateorder();

    });