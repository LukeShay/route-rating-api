package com.routerating.api.walls;

import com.routerating.api.common.utils.Annotations.Controller;
import com.routerating.api.common.utils.BodyUtils;
import com.routerating.api.common.utils.ResponseUtils;
import com.routerating.api.common.walls.Wall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller("/api")
public class WallController {

	private static Logger LOG = LoggerFactory.getLogger(WallController.class.getName());

	private WallService wallService;

	@Autowired
	public WallController(WallService wallService) {
		this.wallService = wallService;
	}

	@PostMapping("")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> createWall(Authentication authentication, @RequestBody Wall body) {
		LOG.debug("Adding wall {}", body);
		return wallService.createWall(authentication, body);
	}

	@DeleteMapping("/{wallId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> deleteWall(Authentication authentication, @PathVariable String wallId) {
		LOG.debug("Deleting wall {}", wallId);

		Wall wall = wallService.deleteWall(authentication, wallId);

		if (wall == null) {
			return ResponseUtils.badRequest(BodyUtils.error("Error deleting wall."));
		}
		else {
			return ResponseUtils.ok(wall);
		}
	}

	@GetMapping("/{gymId}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> getWalls(
			Authentication authentication,
			@PathVariable String gymId,
			@PathParam("query") String query,
			@PathParam("sort") String sort,
			@PathParam("limit") Integer limit,
			@PathParam("page") Integer page
	) {
		LOG.debug("Getting gym {} walls", gymId);

		return wallService.getWalls(gymId, query, sort, limit, page);
	}

	@PutMapping("")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> updateWall(Authentication authentication, @RequestBody Wall body) {
		LOG.debug("Updating wall {}", body);

		Wall wall =
				wallService.updateWall(authentication, body.getId(), body.getGymId(), body.getName(), body.getTypes());

		if (wall == null) {
			return ResponseUtils.badRequest(BodyUtils.error("Error updating wall."));
		}
		else {
			return ResponseUtils.ok(wall);
		}
	}
}
