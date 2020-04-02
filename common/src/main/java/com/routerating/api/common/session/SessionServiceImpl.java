package com.routerating.api.common.session;

import com.routerating.api.common.jwt.RouteRatingJwt;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

	@Autowired private SessionRepository sessionRepository;

	@Override
	public Session createSession(
			String jwtToken, Claims jwtClaims, Long expiresIn, String refreshToken, Claims refreshClaims, String userId
	) {
		return new Session(new RouteRatingJwt(jwtToken, jwtClaims, expiresIn, refreshToken, refreshClaims), userId);
	}

	@Override
	public void deleteSession(Session session) {
		sessionRepository.delete(session);
	}

	@Override
	public Session saveSession(Session session) {
		return sessionRepository.save(session);
	}
}
