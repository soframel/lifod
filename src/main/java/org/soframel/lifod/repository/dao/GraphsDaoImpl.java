package org.soframel.lifod.repository.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.List;

import javax.inject.Inject;

import org.soframel.lifod.domain.EndOfLife;
import org.soframel.lifod.repository.EndOfLifeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public class GraphsDaoImpl implements GraphsDao{

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private EndOfLifeRepository endOfLifeRepository;
	
	public GraphsDaoImpl(MongoTemplate mongoTemplate){
		this.mongoTemplate=mongoTemplate;
	}
	
	public GraphsDaoImpl(){
		
	}
	
	@Override
	public List<GraphData> getLifespanByBrand(String brand) {

		// Mongo command line version:
		// db.end_of_life.aggregate([{$group : { _id : "$brand", lifespan: {$avg: {$subtract: [ "$end_of_life_date", "$buying_date" ]}}}}]);

		Aggregation agg = newAggregation(
				project("brand")
				.andExpression("endOfLifeDate-buyingDate").as("duration"),
				group("brand").avg("duration").as("average")
				.max("duration").as("max")
				.min("duration").as("min")
				.first("brand").as("name"));

		
		// Convert the aggregation result into a List
		AggregationResults<GraphData> groupResults = mongoTemplate.aggregate(
				agg, EndOfLife.class, GraphData.class);
		List<GraphData> result = groupResults.getMappedResults();

		return result;
	}
}
