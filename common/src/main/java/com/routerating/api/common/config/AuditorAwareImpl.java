package com.routerating.api.common.config;

import com.routerating.api.common.utils.AuthenticationUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		String userId = "";

		try {
			userId = AuthenticationUtils.getUser(SecurityContextHolder.getContext().getAuthentication()).getId();
		} catch (Exception ignore) {
		}
		return Optional.of(userId);
	}
}
