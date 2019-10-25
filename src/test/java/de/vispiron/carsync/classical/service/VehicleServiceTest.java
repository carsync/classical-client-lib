package de.vispiron.carsync.classical.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.vispiron.carsync.classical.domain.CarsyncServerException;
import de.vispiron.carsync.classical.resources.vehicle.Vehicle;
import de.vispiron.carsync.classical.resources.vehicle.VehicleResponseDTO;
import de.vispiron.carsync.classical.resources.vehicle.VehicleService;
import de.vispiron.carsync.classical.resources.vehicle.VehicleServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

	private final VehicleService vehicleService;

	private final WireMockServer wireMockServer;

	VehicleServiceTest() {
		CarsyncSession carsyncSession = new CarsyncSession("test@test.com", "password");
		carsyncSession.setBaseUrl("http://localhost:8081");
		this.vehicleService = new VehicleServiceImpl(carsyncSession);

		wireMockServer = new WireMockServer(options().bindAddress("127.0.0.1").port(8081));

	}

	@BeforeEach
	void startMockServer() {
		wireMockServer.start();
	}

	@AfterEach
	void endMockServer() {
		wireMockServer.stop();
	}

	@Test
	void getList_InvalidSession_Exception() {
		wireMockServer.stubFor(any(urlPathEqualTo("/api/v4/vehicle")).willReturn(aResponse()
				.withStatus(401)
				.withBody("[{\"error\":\"not logged in\"}]")));

		Assertions.assertThrows(CarsyncServerException.class, vehicleService::getList);
	}

	@Test
	void getList_ValidSession_Ok() {
		wireMockServer.stubFor(get(urlPathEqualTo("/api/v4/vehicle")).willReturn(aResponse()
				.withBody("[{\"id\":\"1\"}]")
				.withHeader("Content-Range", "items 0-0/1"))
		);

		Page<Vehicle> vehiclesPage = vehicleService.getList();
		assertThat(vehiclesPage.getContent()).hasSize(1);
		assertThat(vehiclesPage.getTotalSize()).isEqualTo(1);
	}

	@Test
	void get_ValidSession_Ok() {
		wireMockServer.stubFor(get(urlPathEqualTo("/api/v4/vehicle/1007")).willReturn(aResponse()
				.withBody("{\"id\":\"1007\"}")));

		Vehicle vehicle = vehicleService.get(1007);
		assertThat(vehicle.getId()).isEqualTo(1007);
	}

	@Test
	void update_ValidSession_Ok() {
		wireMockServer.stubFor(put(urlPathEqualTo("/api/v4/vehicle/1007")).willReturn(aResponse()
				.withBody("{\"id\":\"1007\",\"licensePlate\":\"licensePlate3\"}")));

		Vehicle vehicleIn = new Vehicle();
		vehicleIn.setId(1007);
		vehicleIn.setLicensePlate("licensePlate1");
		VehicleResponseDTO vehicleDbState = new VehicleResponseDTO();
		vehicleDbState.id = 1007;
		vehicleDbState.licensePlate = "licensePlate1";
		vehicleIn.setDatabaseState(vehicleDbState);

		vehicleIn.setLicensePlate("licensePlate2");

		Vehicle vehicleOut = vehicleService.update(vehicleIn);

		assertThat(vehicleOut).isSameAs(vehicleIn);
		assertThat(vehicleOut.getLicensePlate()).isEqualTo("licensePlate3");
	}

	@Test
	void create_ValidSession_Ok() {
		wireMockServer.stubFor(post(urlPathEqualTo("/api/v4/vehicle")).willReturn(aResponse()
				.withBody("{\"id\":\"10\",\"licensePlate\":\"licensePlate2\"}")));

		Vehicle vehicleIn = new Vehicle();
		vehicleIn.setLicensePlate("licensePlate1");

		Vehicle vehicleOut = vehicleService.save(vehicleIn);

		assertThat(vehicleOut).isSameAs(vehicleIn);
		assertThat(vehicleOut.getLicensePlate()).isEqualTo("licensePlate2");
	}
}
