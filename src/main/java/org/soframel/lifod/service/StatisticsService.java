package org.soframel.lifod.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.lifod.repository.EndOfLifeRepository;
import org.soframel.lifod.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */
@Service
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    @Inject
    private UserRepository userRepository;
    
    @Inject
    private EndOfLifeRepository eofRepository;

    public Long getNumberOfUsers() {
    	long nb=userRepository.count();
    	log.debug("getNumberOfUsers: "+nb);

       return nb;  		
    }
    
    public Long getNumberOfDevices() {
    	long nb=eofRepository.count();  ;
        log.debug("getNumberOfDevices: "+nb);

        return 	nb;	
     }

}
