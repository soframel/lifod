package org.soframel.lifod.web.rest;

import org.soframel.lifod.Application;
import org.soframel.lifod.domain.EndOfLife;
import org.soframel.lifod.repository.EndOfLifeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EndOfLifeResource REST controller.
 *
 * @see EndOfLifeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EndOfLifeResourceTest {

    private static final String DEFAULT_USER = "AAAAA";
    private static final String UPDATED_USER = "BBBBB";

    private static final LocalDate DEFAULT_END_OF_LIFE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_OF_LIFE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BUYING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUYING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_PRODUCT = "AAAAA";
    private static final String UPDATED_PRODUCT = "BBBBB";
    private static final String DEFAULT_PRODUCT_VERSION = "AAAAA";
    private static final String UPDATED_PRODUCT_VERSION = "BBBBB";
    private static final String DEFAULT_BRAND = "AAAAA";
    private static final String UPDATED_BRAND = "BBBBB";
    private static final String DEFAULT_REASON = "AAAAA";
    private static final String UPDATED_REASON = "BBBBB";

    @Inject
    private EndOfLifeRepository endOfLifeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEndOfLifeMockMvc;

    private EndOfLife endOfLife;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EndOfLifeResource endOfLifeResource = new EndOfLifeResource();
        ReflectionTestUtils.setField(endOfLifeResource, "endOfLifeRepository", endOfLifeRepository);
        this.restEndOfLifeMockMvc = MockMvcBuilders.standaloneSetup(endOfLifeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        endOfLifeRepository.deleteAll();
        endOfLife = new EndOfLife();
        endOfLife.setUser(DEFAULT_USER);
        endOfLife.setEndOfLifeDate(DEFAULT_END_OF_LIFE_DATE);
        endOfLife.setBuyingDate(DEFAULT_BUYING_DATE);
        endOfLife.setProduct(DEFAULT_PRODUCT);
        endOfLife.setProductVersion(DEFAULT_PRODUCT_VERSION);
        endOfLife.setBrand(DEFAULT_BRAND);
        endOfLife.setReason(DEFAULT_REASON);
    }

    @Test
    public void createEndOfLife() throws Exception {
        int databaseSizeBeforeCreate = endOfLifeRepository.findAll().size();

        // Create the EndOfLife

        restEndOfLifeMockMvc.perform(post("/api/endOfLifes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(endOfLife)))
                .andExpect(status().isCreated());

        // Validate the EndOfLife in the database
        List<EndOfLife> endOfLifes = endOfLifeRepository.findAll();
        assertThat(endOfLifes).hasSize(databaseSizeBeforeCreate + 1);
        EndOfLife testEndOfLife = endOfLifes.get(endOfLifes.size() - 1);
        assertThat(testEndOfLife.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testEndOfLife.getEndOfLifeDate()).isEqualTo(DEFAULT_END_OF_LIFE_DATE);
        assertThat(testEndOfLife.getBuyingDate()).isEqualTo(DEFAULT_BUYING_DATE);
        assertThat(testEndOfLife.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testEndOfLife.getProductVersion()).isEqualTo(DEFAULT_PRODUCT_VERSION);
        assertThat(testEndOfLife.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testEndOfLife.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    public void getAllEndOfLifes() throws Exception {
        // Initialize the database
        endOfLifeRepository.save(endOfLife);

        // Get all the endOfLifes
        restEndOfLifeMockMvc.perform(get("/api/endOfLifes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(endOfLife.getId())))
                .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
                .andExpect(jsonPath("$.[*].endOfLifeDate").value(hasItem(DEFAULT_END_OF_LIFE_DATE.toString())))
                .andExpect(jsonPath("$.[*].buyingDate").value(hasItem(DEFAULT_BUYING_DATE.toString())))
                .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT.toString())))
                .andExpect(jsonPath("$.[*].productVersion").value(hasItem(DEFAULT_PRODUCT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
                .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    public void getEndOfLife() throws Exception {
        // Initialize the database
        endOfLifeRepository.save(endOfLife);

        // Get the endOfLife
        restEndOfLifeMockMvc.perform(get("/api/endOfLifes/{id}", endOfLife.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(endOfLife.getId()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.endOfLifeDate").value(DEFAULT_END_OF_LIFE_DATE.toString()))
            .andExpect(jsonPath("$.buyingDate").value(DEFAULT_BUYING_DATE.toString()))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT.toString()))
            .andExpect(jsonPath("$.productVersion").value(DEFAULT_PRODUCT_VERSION.toString()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    public void getNonExistingEndOfLife() throws Exception {
        // Get the endOfLife
        restEndOfLifeMockMvc.perform(get("/api/endOfLifes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEndOfLife() throws Exception {
        // Initialize the database
        endOfLifeRepository.save(endOfLife);

		int databaseSizeBeforeUpdate = endOfLifeRepository.findAll().size();

        // Update the endOfLife
        endOfLife.setUser(UPDATED_USER);
        endOfLife.setEndOfLifeDate(UPDATED_END_OF_LIFE_DATE);
        endOfLife.setBuyingDate(UPDATED_BUYING_DATE);
        endOfLife.setProduct(UPDATED_PRODUCT);
        endOfLife.setProductVersion(UPDATED_PRODUCT_VERSION);
        endOfLife.setBrand(UPDATED_BRAND);
        endOfLife.setReason(UPDATED_REASON);

        restEndOfLifeMockMvc.perform(put("/api/endOfLifes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(endOfLife)))
                .andExpect(status().isOk());

        // Validate the EndOfLife in the database
        List<EndOfLife> endOfLifes = endOfLifeRepository.findAll();
        assertThat(endOfLifes).hasSize(databaseSizeBeforeUpdate);
        EndOfLife testEndOfLife = endOfLifes.get(endOfLifes.size() - 1);
        assertThat(testEndOfLife.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testEndOfLife.getEndOfLifeDate()).isEqualTo(UPDATED_END_OF_LIFE_DATE);
        assertThat(testEndOfLife.getBuyingDate()).isEqualTo(UPDATED_BUYING_DATE);
        assertThat(testEndOfLife.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testEndOfLife.getProductVersion()).isEqualTo(UPDATED_PRODUCT_VERSION);
        assertThat(testEndOfLife.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testEndOfLife.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    public void deleteEndOfLife() throws Exception {
        // Initialize the database
        endOfLifeRepository.save(endOfLife);

		int databaseSizeBeforeDelete = endOfLifeRepository.findAll().size();

        // Get the endOfLife
        restEndOfLifeMockMvc.perform(delete("/api/endOfLifes/{id}", endOfLife.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EndOfLife> endOfLifes = endOfLifeRepository.findAll();
        assertThat(endOfLifes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
