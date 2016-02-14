package org.soframel.lifod.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.lifod.repository.dao.GraphData;
import org.soframel.lifod.repository.dao.GraphsDao;
import org.springframework.stereotype.Service;

@Service
public class GraphsService {

    private final Logger log = LoggerFactory.getLogger(GraphsService.class);

    
    @Inject
    private GraphsDao graphsDaoImpl;
    
    public List<GraphData> getDataByBrand(String brand) {
    	 return graphsDaoImpl.getLifespanByBrand(brand);
    }
    
   
}
