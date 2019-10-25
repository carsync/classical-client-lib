# Carsync Classic client library
This project is a java library to access and use the classical (https://portal.carsync-log.de) REST API. It provides implementations of some endpoints entities, but can be easily extended.

## How to use
Import it in your project.
```
// Provide credentials
CarsyncSession carsyncSession = new CarsyncSession("test@test.com", "password");
```
To access the vehicle API
```
VehicleService vehicleService = new VehicleServiceImpl(carsyncSession);
```
get all accessible vehicles
```
Page<Vehicle> vehiclePage = vehicleService.getList();
```
get one vehicle by id
```
Vehicle vehicle = vehicleService.get(1234);
```
creating and saving a vehicle
```
Vehicle vehicle = new Vehicle();
vehicle.setLicensePlate("AB-C 1234");
vehicle.setCountry("de");
vehicle.setManufacturer("De Dion-Bouton");
vehicle.setVehicleClass("Q 6hp");
vehicle.setFuelType("Diesel");
vehicle.setDivisionId(1234);
vehicleService.save(vehicle); // vehicle is internally updated (with id and other server-set fields)
```
update the vehicle
```
vehicle.setLicensePlate("AB-C 5678");
vehicleService.update(vehicle);

```
you cannot delete a vehicle, but for other entities you can delete it woulds look like this:
```
vehicleService.delete(vehicle);
```
or just by id
```
vehicleService.update(1234);
```