package org.soframel.lifod.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.soframel.lifod.domain.EndOfLife;
import org.soframel.lifod.repository.EndOfLifeRepository;
import org.soframel.lifod.security.SecurityUtils;
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
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EndOfLife.
 */
@RestController
@RequestMapping("/api")
public class EndOfLifeResource {

    private final Logger log = LoggerFactory.getLogger(EndOfLifeResource.class);

    @Inject
    private EndOfLifeRepository endOfLifeRepository;

    /**
     * POST  /endOfLifes -> Create a new endOfLife.
     */
    @RequestMapping(value = "/endOfLifes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EndOfLife> createEndOfLife(@RequestBody EndOfLife endOfLife) throws URISyntaxException {
        log.debug("REST request to save EndOfLife : {}", endOfLife);
        if (endOfLife.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new endOfLife cannot already have an ID").body(null);
        }
        endOfLife.setUser(SecurityUtils.getCurrentUserLogin());      
        EndOfLife result = endOfLifeRepository.save(endOfLife);
        return ResponseEntity.created(new URI("/api/endOfLifes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("endOfLife", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /endOfLifes -> Updates an existing endOfLife.
     */
    @RequestMapping(value = "/endOfLifes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EndOfLife> updateEndOfLife(@RequestBody EndOfLife endOfLife) throws URISyntaxException {
        log.debug("REST request to update EndOfLife : {}", endOfLife);
        if (endOfLife.getId() == null) {
            return createEndOfLife(endOfLife);
        }
        endOfLife.setUser(SecurityUtils.getCurrentUserLogin());      
        EndOfLife result = endOfLifeRepository.save(endOfLife);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("endOfLife", endOfLife.getId().toString()))
            .body(result);
    }

    /**
     * GET  /endOfLifes -> get all the endOfLifes.
     */
    @RequestMapping(value = "/endOfLifes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EndOfLife>> getAllEndOfLifes(Pageable pageable)
        throws URISyntaxException {
        //Page<EndOfLife> page = endOfLifeRepository.findAll(pageable);
    	Page<EndOfLife> page =endOfLifeRepository.findByUser(SecurityUtils.getCurrentUserLogin(),pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/endOfLifes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /endOfLifes/:id -> get the "id" endOfLife.
     */
    @RequestMapping(value = "/endOfLifes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EndOfLife> getEndOfLife(@PathVariable String id) {
        log.debug("REST request to get EndOfLife : {}", id);
        EndOfLife found=endOfLifeRepository.findOne(id);
        if(found!=null && found.getUser()!=null && found.getUser().equals(SecurityUtils.getCurrentUserLogin()))
	        return Optional.ofNullable(found)
	            .map(eof -> new ResponseEntity<>(
	            		eof,
	                HttpStatus.OK))
	            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        else
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE  /endOfLifes/:id -> delete the "id" endOfLife.
     */
    @RequestMapping(value = "/endOfLifes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEndOfLife(@PathVariable String id) {
        log.debug("REST request to delete EndOfLife : {}", id);
        EndOfLife found=endOfLifeRepository.findOne(id);
        if(found!=null && found.getUser()!=null && found.getUser().equals(SecurityUtils.getCurrentUserLogin()))        
        	endOfLifeRepository.delete(id);
        
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("endOfLife", id.toString())).build();
    }
}
