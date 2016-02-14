package org.soframel.lifod.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.soframel.lifod.domain.Authority;
import org.soframel.lifod.domain.User;
import org.soframel.lifod.repository.AuthorityRepository;
import org.soframel.lifod.repository.UserRepository;
import org.soframel.lifod.repository.dao.GraphData;
import org.soframel.lifod.security.AuthoritiesConstants;
import org.soframel.lifod.service.GraphsService;
import org.soframel.lifod.service.StatisticsService;
import org.soframel.lifod.service.UserService;
import org.soframel.lifod.web.rest.dto.BarChartDataDTO;
import org.soframel.lifod.web.rest.dto.ChartDataSetDTO;
import org.soframel.lifod.web.rest.dto.ManagedUserDTO;
import org.soframel.lifod.web.rest.dto.StatisticsDTO;
import org.soframel.lifod.web.rest.dto.UserDTO;
import org.soframel.lifod.web.rest.util.HeaderUtil;
import org.soframel.lifod.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing statistics.
 *
 */
@RestController
@RequestMapping("/api")
public class GraphsResource {

    private final Logger log = LoggerFactory.getLogger(GraphsResource.class);
    
    private final static long MILLISECONDS_IN_A_MONTH=1000*60*60*24*30l;

    @Inject
    private GraphsService graphsService;

    /**
     * GET  /graphs/dataByBrand -> get number of users.
     */
    @RequestMapping(value = "/graphs/dataByBrand",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BarChartDataDTO> getDataByBrand(String brand)
        throws URISyntaxException {       
        
    	List<GraphData> dataList=graphsService.getDataByBrand(brand);
    	BarChartDataDTO dto=this.transformForChartJS(dataList);
    	
    	 //.map(nbUsers -> new ResponseEntity<>(new StatisticsDTO(nbUsers), HttpStatus.OK))
    	
    	 return Optional.ofNullable(new ResponseEntity<>(dto, HttpStatus.OK))
    	   .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
    
    private  BarChartDataDTO transformForChartJS(List<GraphData> dataList){
    	BarChartDataDTO result=new BarChartDataDTO();
    	List<String> labels=new ArrayList<String>();
    	result.setLabels(labels);
    	List<ChartDataSetDTO> dataSets=new ArrayList<ChartDataSetDTO>();
    	result.setDatasets(dataSets);
    	
    	ChartDataSetDTO averageDS=new ChartDataSetDTO();
    	dataSets.add(averageDS);
    	averageDS.setLabel("Average");
    	List<Integer> avgValues=new ArrayList<Integer>();
    	averageDS.setData(avgValues);
    	
    	ChartDataSetDTO minDS=new ChartDataSetDTO();
    	dataSets.add(minDS);
    	minDS.setLabel("Min");
    	List<Integer> minValues=new ArrayList<Integer>();
    	minDS.setData(minValues);
    	
    	ChartDataSetDTO maxDS=new ChartDataSetDTO();
    	dataSets.add(maxDS);
    	maxDS.setLabel("Max");
    	List<Integer> maxValues=new ArrayList<Integer>();
    	maxDS.setData(maxValues);
    	
    	//find all brands
    	log.debug("Transforming graph data: ");
    	for(GraphData data: dataList){
    		labels.add(data.getName());
    		
    		avgValues.add(this.getMonthsForMilliseconds(data.getAverage()));
    		minValues.add(this.getMonthsForMilliseconds(data.getMin()));
    		maxValues.add(this.getMonthsForMilliseconds(data.getMax()));
    	}
    	
    	
    	return result;   	
    }
    
    private int getMonthsForMilliseconds(long duration){
    	long months=duration/MILLISECONDS_IN_A_MONTH;
    	return (int) months;
    }
    
}
