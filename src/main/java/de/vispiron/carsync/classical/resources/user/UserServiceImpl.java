package de.vispiron.carsync.classical.resources.user;

import de.vispiron.carsync.classical.service.CarsyncSession;
import de.vispiron.carsync.classical.service.RestServiceImpl;

public class UserServiceImpl extends RestServiceImpl<User> implements UserService {

	public UserServiceImpl(CarsyncSession carsyncSession) {
		super(carsyncSession,
				"/api/v4/user",
				User.class,
				UserResponseDTO.class,
				UserCreateDTO.class,
				UserUpdateDTO.class);
	}
}
