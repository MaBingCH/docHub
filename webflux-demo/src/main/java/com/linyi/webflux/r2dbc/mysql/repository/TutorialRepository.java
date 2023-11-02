package com.linyi.webflux.r2dbc.mysql.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.linyi.webflux.r2dbc.mysql.model.Tutorial;

import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface TutorialRepository extends R2dbcRepository<Tutorial, Integer>{

	Flux<Tutorial> findByTitleContaining(String title);
	
	Flux<Tutorial> findByPublished(boolean isPublish);

	@Query("SELECT * FROM tutorial WHERE description LIKE CONCAT('%',:desc,'%')")
	Flux<Tutorial> findByDesc(@Param("desc") String desc);
	
}
