package com.routerating.api.common.gyms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<Gym, String> {

	Page<Gym> findAllByNameIgnoreCaseContaining(Pageable pageable, String query);
}
