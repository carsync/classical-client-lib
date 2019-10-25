package de.vispiron.carsync.classical.resources.vehicle;

import de.vispiron.carsync.classical.domain.CarsyncIdDomain;
import de.vispiron.carsync.classical.resources.company.Company;
import de.vispiron.carsync.classical.resources.division.Division;
import de.vispiron.carsync.classical.resources.user.User;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Vehicle extends CarsyncIdDomain {

	private String licensePlate;

	private Company company;

	private Division division;

	private String fuelType;

	private Instant lastReport;

	private String gear;

	private String image;

	private String imageRev;

	private Boolean bookable;

	private Boolean gps;

	private String tyreType;

	private String defaultReason;

	private Boolean isBusinessCar;

	private Boolean hasAdvert;

	private String vehicleClass;

	private String manufacturer;

	private String type;

	private String serialNumber;

	private String country;

	private String city;

	private String street;

	private String note;

	private Integer seats;

	private Integer km;

	private Integer fuelLevel;

	private Integer fuelLevelMax;

	private Float consumption;

	private Integer maxRange;

	private Integer loadingTime;

	private Boolean tracking;

	private Integer power;

	private Float trunkCompartmentVolume;

	private Float feePerKm;

	private Integer comfort;

	private Boolean active;

	private String syncId;

	private Float declaredConsumption;

	private Boolean requiresMileageAdjustment;

	private Boolean ignoreUnplugTrips;

	private Integer leasingId;

	private Integer numDamages;

	//private String obu;
	private User driver;
	//private String position;
	//private String leasing;
	private Instant lastUnlock;

	private User oneToOneDriver;

	public void setCompanyId(Integer id) {
		if (company == null || !company.getId().equals(id)) {
			company = new Company(id);
		}
	}

	public void setDivisionId(Integer id) {
		if (division == null || !division.getId().equals(id)) {
			division = new Division(id);
		}
	}
}
