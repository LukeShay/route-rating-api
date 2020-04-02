package com.routerating.api.gyms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface GymService {

	Logger LOG = LoggerFactory.getLogger(GymService.class.getName());

	Gym createGym(Gym gym);

	@Deprecated
	Iterable<Gym> getAllGyms();

	Gym getGymById(String gymId);

	Page<Gym> getGyms(
			Optional<String> query,
            Optional<String> sorts,
			Optional<Integer> limit,
			Optional<Integer> page
	);

	Gym updateGym(
			Authentication authentication,
			String gymId,
			String name,
			String address,
			String city,
			String state,
			String zipCode,
			String email,
			String phoneNumber,
			String website,
			List<String> authorizedEditors
	);

	ResponseEntity<?> uploadPhoto(
			Authentication authentication, MultipartFile file, String gymId, String imageName
	);
}
