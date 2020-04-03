package com.routerating.api.gyms;

import com.routerating.api.common.gyms.Gym;
import com.routerating.api.common.utils.Annotations.*;
import com.routerating.api.common.utils.BodyUtils;
import com.routerating.api.common.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller("/api")
public class GymController {

	private static Logger LOG = LoggerFactory.getLogger(GymController.class.getName());

	private GymService gymService;

	@Autowired
	public GymController(GymService gymService) {
		this.gymService = gymService;
	}

	@AdminPostMapping("")
	public ResponseEntity<?> createGym(@RequestBody Gym body) {
		Gym gym = gymService.createGym(body);

		return ResponseUtils.ok(gym);
	}

	@Deprecated
	@UnauthGetMapping("/all")
	public ResponseEntity<?> getAllGyms() {
		LOG.debug("Getting all gyms");

		Iterable<Gym> gyms = gymService.getAllGyms();

		return ResponseUtils.ok(gyms);
	}

	@UnauthGetMapping("/{gymId}")
	public ResponseEntity<?> getGymById(@PathVariable String gymId) {
		LOG.debug("Getting gym {}", gymId);

		Gym foundGym = gymService.getGymById(gymId);

		if (foundGym == null) {
			return ResponseUtils.badRequest(BodyUtils.error("Gym not found."));
		}
		else {
			return ResponseUtils.ok(foundGym);
		}
	}

	@UnauthGetMapping
	public ResponseEntity<?> getGyms(
			@RequestParam(value = "query", required=false) String query,
			@RequestParam(value = "sort", required=false) String sort,
			@RequestParam(value = "limit", required=false) Integer limit,
			@RequestParam(value = "page", required=false) Integer page
	) {
		LOG.debug("Getting gyms, query: {}, limit: {}, page: {}", query, limit, page);

		Page<Gym> gyms = gymService.getGyms(
				Optional.ofNullable(query),
				Optional.ofNullable(sort),
				Optional.ofNullable(limit),
				Optional.ofNullable(page)
		);

		return ResponseUtils.ok(gyms);
	}

	@AuthPutMapping("/{gymId}")
	public ResponseEntity<?> updateGym(
			Authentication authentication, @PathVariable String gymId, @RequestBody Gym gym
	) {

		LOG.debug("Updating {}", gymId);

		gym = gymService.updateGym(
				authentication,
				gymId,
				gym.getName(),
				gym.getAddress(),
				gym.getCity(),
				gym.getState(),
				gym.getZipCode(),
				gym.getEmail(),
				gym.getPhoneNumber(),
				gym.getWebsite(),
				gym.getAuthorizedEditors()
		);

		if (gym == null) {
			return ResponseUtils.badRequest(BodyUtils.error("Gym not found"));
		}
		else {
			return ResponseUtils.ok(gym);
		}
	}

	@AuthPostMapping("/image/{imageName}")
	public ResponseEntity<?> uploadLogo(
			Authentication authentication,
			@RequestParam("file") MultipartFile file,
			@RequestParam("gymId") String gymId,
			@PathVariable String imageName
	) {
		LOG.debug("Uploading logo to gym {}", gymId);

		return gymService.uploadPhoto(authentication, file, gymId, imageName);
	}
}
