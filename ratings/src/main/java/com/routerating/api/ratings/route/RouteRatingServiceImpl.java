package com.routerating.api.ratings.route;

import com.routerating.api.common.routes.RouteRepository;
import com.routerating.api.common.routes.Route;
import com.routerating.api.common.routes.RouteRating;
import com.routerating.api.common.user.User;
import com.routerating.api.common.utils.AuthenticationUtils;
import com.routerating.api.common.utils.BodyUtils;
import com.routerating.api.common.utils.PageableUtils;
import com.routerating.api.common.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteRatingServiceImpl implements RouteRatingService {

	private static Logger LOG = LoggerFactory.getLogger(RouteRatingServiceImpl.class.getName());

	@Autowired private RouteRatingRepository ratingRepository;
	@Autowired private RouteRepository routeRepository;

	@Override
	public ResponseEntity<?> createRating(Authentication authentication, RouteRating rating) {
		LOG.debug("Creating rating {}", rating.toString());
		User user = AuthenticationUtils.getUser(authentication);
		String routeId = rating.getRouteId();

		rating.setCreatorId(user.getId());
		rating.setCreatorUsername(user.getUsername());

		Route route = routeRepository.findById(routeId).orElse(null);

		if (!validateRating(rating) || route == null) {
			LOG.debug("Rating is invalid");
			return ResponseUtils.badRequest(BodyUtils.error("Rating is invalid."));
		}

		RouteRating newRating = ratingRepository.save(rating);

		List<RouteRating> ratings = ratingRepository.findAllByRouteId(routeId);

		route.updateAverages(ratings);

		routeRepository.save(route);

		return ResponseUtils.ok(newRating);
	}

	@Override
	public Page<RouteRating> getRatingsByRouteId(
			String routeId, String sorts, Integer limit, Integer page
	) {
		if (sorts == null) {
			sorts = "createdDate:desc";
		}

		return ratingRepository.findAllByRouteId(PageableUtils.buildPageRequest(page, limit, sorts), routeId);
	}

	private boolean validateRating(RouteRating rating) {
		return rating.getCreatorId() != null && rating.getCreatorUsername() != null && rating.getRouteId() != null && rating
				.getRating() != 0 && rating.getRating() <= 5 && rating.getGrade() != null;
	}
}
