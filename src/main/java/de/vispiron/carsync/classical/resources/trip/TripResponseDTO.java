package de.vispiron.carsync.classical.resources.trip;

import de.vispiron.carsync.classical.dto.CarsyncDTO;
import de.vispiron.carsync.classical.resources.user.User;

import java.util.List;

public class TripResponseDTO extends CarsyncDTO {

	public Integer id;

	public String country;

	public Float consumptionVehicle;

	public String fuelType;

	public String contact;

	public String company;

	public String occasion;

	public String costCenter;

	public String detourReason;

	public Integer detourDistance;

	public Boolean isDetour;

	public String timeStart;

	public String timeEnd;

	public Float latitudeStart;

	public Float longitudeStart;

	public Float latitudeEnd;

	public Float longitudeEnd;

	public Integer merged;

	public Float consumption;

	public Integer fuelStart;

	public Integer fuelEnd;

	public Integer fuelRefill;

	public String countryStart;

	public String countryEnd;

	public String postalCodeStart;

	public String postalCodeEnd;

	public String cityEnd;

	public String cityStart;

	public String streetEnd;

	public String streetStart;

	public String driversEmailIsValid;

	public Integer tripType;

	public Integer obuId;

	public Integer hwId;

	public Integer driverId;

	public String driverName;

	public String licensePlate;

	public Integer kmStart;

	public Integer kmEnd;

	public Integer distance;

	public Integer motherTrip;

	public String originalDriver;

	public String comment;

	public Boolean compliant;

	public String editableUntil;

	public Boolean mileageChangeAllowed;

	public Integer idPreviousTrip;

	public User assignedToDriver;

	public String costCenterAck;

	public List<TripResponseDTO> children;

	//public signal	null
	//public parking	null

}
