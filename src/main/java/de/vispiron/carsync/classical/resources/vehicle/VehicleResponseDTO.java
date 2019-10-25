package de.vispiron.carsync.classical.resources.vehicle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.vispiron.carsync.classical.dto.CarsyncDTO;
import de.vispiron.carsync.classical.resources.company.CompanyResponseDTO;
import de.vispiron.carsync.classical.resources.division.DivisionResponseDTO;
import de.vispiron.carsync.classical.resources.user.UserResponseDTO;

public class VehicleResponseDTO extends CarsyncDTO {

	@JsonAlias("idVehicle")
	public Integer id;

	public String licensePlate;

	public CompanyResponseDTO company;

	public DivisionResponseDTO division;

	public String fuelType;

	public String lastReport;

	public String gear;

	public String image;

	public String imageRev;

	public Boolean bookable;

	public Boolean gps;

	public String tyreType;

	public String defaultReason;

	public Boolean isBusinessCar;

	public Boolean hasAdvert;

	@JsonProperty("class")
	public String vehicleClass;

	public String manufacturer;

	public String type;

	public String serialNumber;

	public String country;

	public String city;

	public String street;

	public String note;

	public Integer seats;

	public Integer km;

	public Integer fuelLevel;

	public Float fuelLevelMax;

	public Float consumption;

	public Integer maxRange;

	public Integer loadingTime;

	public Boolean tracking;

	public Integer power;

	public Float trunkCompartmentVolume;

	public Float feePerKm;

	public Integer comfort;

	public Boolean active;

	public String syncId;

	public Float declaredConsumption;

	public Boolean requiresMileageAdjustment;

	public Boolean ignoreUnplugTrips;

	public Integer leasingId;

	public Integer numDamages;

	//public String obu;
	public UserResponseDTO driver;
	//public String position;
	//public String leasing;
	public String lastUnlock;

	public UserResponseDTO oneToOneDriver;

}
