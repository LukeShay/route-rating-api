package com.routerating.api.ratings.route;

import com.routerating.api.common.routes.RouteRating;
import com.routerating.api.common.utils.Annotations.Controller;
import com.routerating.api.common.utils.Annotations.AuthPostMapping;
import com.routerating.api.common.utils.Annotations.UnauthGetMapping;
import com.routerating.api.common.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller("/api")
public class RouteRatingController {

	private RouteRatingService ratingService;

	@Autowired
	public RouteRatingController(RouteRatingService ratingService) {
		this.ratingService = ratingService;
	}

	@AuthPostMapping("")
	public ResponseEntity<?> createRating(
			Authentication authentication, @RequestBody RouteRating rating
	) {
		return ratingService.createRating(authentication, rating);
	}

	@UnauthGetMapping("/{routeId}")
	public ResponseEntity<?> getRatings(
			@PathVariable String routeId,
			@PathParam("sort") String sort,
			@PathParam("limit") Integer limit,
			@PathParam("page") Integer pageNumber
	) {

		Page<RouteRating> page = ratingService.getRatingsByRouteId(routeId, sort, limit, pageNumber);

		return ResponseUtils.ok(page);
	}
}
