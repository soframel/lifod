package org.soframel.lifod.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.soframel.lifod.domain.Authority;
import org.soframel.lifod.domain.User;
import org.soframel.lifod.repository.AuthorityRepository;
import org.soframel.lifod.repository.UserRepository;
import org.soframel.lifod.security.AuthoritiesConstants;
import org.soframel.lifod.service.StatisticsService;
import org.soframel.lifod.service.UserService;
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
public class StatisticsResource {

    private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

    @Inject
    private StatisticsService statisticsService;

    /**
     * GET  /statistics/users -> get number of users.
     */
    @RequestMapping(value = "/statistics/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatisticsDTO> getNumberOfUsers()
        throws URISyntaxException {       
        
    	 return Optional.ofNullable(statisticsService.getNumberOfUsers())
    	            .map(nbUsers -> new ResponseEntity<>(new StatisticsDTO(nbUsers), HttpStatus.OK))
    	            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /statistics/devices -> get number of devices.
     */
    @RequestMapping(value = "/statistics/devices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatisticsDTO> getNumberOfDevices()
        throws URISyntaxException {       
    	return Optional.ofNullable(statisticsService.getNumberOfDevices())
	            .map(nDevices -> new ResponseEntity<>(new StatisticsDTO(nDevices), HttpStatus.OK))
	            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
   
}
