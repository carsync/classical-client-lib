package de.vispiron.carsync.classical.resources.trip;

import de.vispiron.carsync.classical.domain.CarsyncIdDomain;
import de.vispiron.carsync.classical.resources.user.User;
import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Trip extends CarsyncIdDomain {

	private String country;

	private Float consumptionVehicle;

	private String fuelType;

	private String contact;

	private String company;

	private String occasion;

	private String costCenter;

	private String detourReason;

	private Integer detourDistance;

	private Boolean isDetour;

	private OffsetDateTime timeStart;

	private OffsetDateTime timeEnd;

	private Float latitudeStart;

	private Float longitudeStart;

	private Float latitudeEnd;

	private Float longitudeEnd;

	private Integer merged;

	private Float consumption;

	private Integer fuelStart;

	private Integer fuelEnd;

	private Integer fuelRefill;

	private String countryStart;

	private String countryEnd;

	private String postalCodeStart;

	private String postalCodeEnd;

	private String cityEnd;

	private String cityStart;

	private String streetEnd;

	private String streetStart;

	private String driversEmailIsValid;

	private Integer tripType;

	private Integer obuId;

	private Integer hwId;

	private Integer driverId;

	private String driverName;

	private String licensePlate;

	private Integer kmStart;

	private Integer kmEnd;

	private Integer distance;

	private Integer motherTrip;

	private String originalDriver;

	private String comment;

	private Boolean compliant;

	private Instant editableUntil;

	private Boolean mileageChangeAllowed;

	private Integer idPreviousTrip;

	private User assignedToDriver;

	private Instant costCenterAck;

	private List<Trip> children;

	//private signal	null
	//private parking	null

	public Trip(Integer id) {
		super(id);
	}
}
