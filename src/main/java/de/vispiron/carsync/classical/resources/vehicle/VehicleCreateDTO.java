package de.vispiron.carsync.classical.resources.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.vispiron.carsync.classical.dto.CarsyncDTO;

public class VehicleCreateDTO extends CarsyncDTO {

	public String licensePlate;

	public Integer idCompany;

	public Integer idDivision;

	public String fuelType;

	public String gear;

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

	public String note;

	public Integer seats;

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

	public Boolean ignoreUnplugTrips;

	//public String obu;
	//public String leasing;
	//public String oneToOneDriver;
}
