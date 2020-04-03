package com.routerating.api.routes;

import com.routerating.api.common.routes.Route;
import com.routerating.api.common.utils.Annotations.Controller;
import com.routerating.api.common.utils.BodyUtils;
import com.routerating.api.common.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller("/api")
public class RouteController {

	private static Logger LOG = LoggerFactory.getLogger(RouteController.class.getName());

	private RouteService routeService;

	@Autowired
	public RouteController(RouteService routeService) {
		this.routeService = routeService;
	}

	@PostMapping("")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> createRoute(Authentication authentication, @RequestBody Route body) {
		LOG.debug("Creating new route {}", body.toString());
		Map<String, String> response = new HashMap<>();

		if (!routeService.validateEditor(authentication, body)) {
			return ResponseUtils.unauthorized();
		}

		response.putAll(routeService.validateRoute(body));
		response.putAll(routeService.validWallTypes(body));

		if (response.isEmpty()) {
			Optional<Route> route = routeService.createRoute(body);

			if (!route.isPresent()) {
				return ResponseUtils.internalServerError(BodyUtils.error("error creating route"));
			}
			else {
				return ResponseUtils.ok(route.get());
			}
		}
		else {
			return ResponseUtils.badRequest(response);
		}
	}

	@DeleteMapping("")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> deleteRoute(Authentication authentication, @RequestBody Route body) {
		LOG.debug("Deleting route {}", body.getId());

		Route route = routeService.deleteRoute(authentication, body);

		if (route == null) {
			return ResponseUtils.badRequest(BodyUtils.error("Error deleting route."));
		}
		else {
			return ResponseUtils.ok(route);
		}
	}

	@GetMapping("/{wallId}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> getRoute(Authentication authentication, @PathVariable String wallId) {
		LOG.debug("Getting routes from wall {}", wallId);

		List<Route> routes = routeService.getRoutesByWall(wallId);

		return ResponseUtils.ok(routes);
	}

	@PutMapping("")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> updateRoute(Authentication authentication, @RequestBody Route body) {
		LOG.debug("Updating route {}", body.getId());

		Route route = routeService.updateRoute(
				authentication,
				body.getId(),
				body.getGymId(),
				body.getWallId(),
				body.getTypes(),
				body.getHoldColor(),
				body.getSetter(),
				body.getName()
		);

		if (route == null) {
			return ResponseUtils.badRequest(BodyUtils.error("Error updating route."));
		}
		else {
			return ResponseUtils.ok(route);
		}
	}
}
