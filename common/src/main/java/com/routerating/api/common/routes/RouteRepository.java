package com.routerating.api.common.routes;

import com.routerating.api.common.routes.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RouteRepository extends JpaRepository<Route, String> {

	void deleteByWallId(String wallId);

	List<Route> findAllByWallId(String wallId);
}
