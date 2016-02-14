package org.soframel.lifod.repository;

import java.util.List;

import org.soframel.lifod.domain.EndOfLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the EndOfLife entity.
 */
public interface EndOfLifeRepository extends MongoRepository<EndOfLife,String> {
	List<EndOfLife> findByUser(String user);
	Page<EndOfLife> findByUser(String user, Pageable pageable);	
	List<EndOfLife> findByProduct(String product);
	
	EndOfLife save(EndOfLife eof);
	
	//TODO: query db.end_of_life.aggregate([{$group : { _id : "$brand", lifespan: {$avg: {$subtract: [ "$end_of_life_date", "$buying_date" ]}}}}]);
}
