package com.kkhenissi.fdc.web.rest;

import com.kkhenissi.fdc.JhipsterFdcApp;
import com.kkhenissi.fdc.domain.ProductItem;
import com.kkhenissi.fdc.repository.ProductItemRepository;
import com.kkhenissi.fdc.service.ProductItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductItemResource} REST controller.
 */
@SpringBootTest(classes = JhipsterFdcApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductItemResourceIT {

    private static final Integer DEFAULT_QUANTITY_ITEM = 1;
    private static final Integer UPDATED_QUANTITY_ITEM = 2;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductItemService productItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductItemMockMvc;

    private ProductItem productItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductItem createEntity(EntityManager em) {
        ProductItem productItem = new ProductItem()
            .quantityItem(DEFAULT_QUANTITY_ITEM);
        return productItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductItem createUpdatedEntity(EntityManager em) {
        ProductItem productItem = new ProductItem()
            .quantityItem(UPDATED_QUANTITY_ITEM);
        return productItem;
    }

    @BeforeEach
    public void initTest() {
        productItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductItem() throws Exception {
        int databaseSizeBeforeCreate = productItemRepository.findAll().size();
        // Create the ProductItem
        restProductItemMockMvc.perform(post("/api/product-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productItem)))
            .andExpect(status().isCreated());

        // Validate the ProductItem in the database
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeCreate + 1);
        ProductItem testProductItem = productItemList.get(productItemList.size() - 1);
        assertThat(testProductItem.getQuantityItem()).isEqualTo(DEFAULT_QUANTITY_ITEM);
    }

    @Test
    @Transactional
    public void createProductItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productItemRepository.findAll().size();

        // Create the ProductItem with an existing ID
        productItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductItemMockMvc.perform(post("/api/product-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productItem)))
            .andExpect(status().isBadRequest());

        // Validate the ProductItem in the database
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductItems() throws Exception {
        // Initialize the database
        productItemRepository.saveAndFlush(productItem);

        // Get all the productItemList
        restProductItemMockMvc.perform(get("/api/product-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityItem").value(hasItem(DEFAULT_QUANTITY_ITEM)));
    }
    
    @Test
    @Transactional
    public void getProductItem() throws Exception {
        // Initialize the database
        productItemRepository.saveAndFlush(productItem);

        // Get the productItem
        restProductItemMockMvc.perform(get("/api/product-items/{id}", productItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productItem.getId().intValue()))
            .andExpect(jsonPath("$.quantityItem").value(DEFAULT_QUANTITY_ITEM));
    }
    @Test
    @Transactional
    public void getNonExistingProductItem() throws Exception {
        // Get the productItem
        restProductItemMockMvc.perform(get("/api/product-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductItem() throws Exception {
        // Initialize the database
        productItemService.save(productItem);

        int databaseSizeBeforeUpdate = productItemRepository.findAll().size();

        // Update the productItem
        ProductItem updatedProductItem = productItemRepository.findById(productItem.getId()).get();
        // Disconnect from session so that the updates on updatedProductItem are not directly saved in db
        em.detach(updatedProductItem);
        updatedProductItem
            .quantityItem(UPDATED_QUANTITY_ITEM);

        restProductItemMockMvc.perform(put("/api/product-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductItem)))
            .andExpect(status().isOk());

        // Validate the ProductItem in the database
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeUpdate);
        ProductItem testProductItem = productItemList.get(productItemList.size() - 1);
        assertThat(testProductItem.getQuantityItem()).isEqualTo(UPDATED_QUANTITY_ITEM);
    }

    @Test
    @Transactional
    public void updateNonExistingProductItem() throws Exception {
        int databaseSizeBeforeUpdate = productItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductItemMockMvc.perform(put("/api/product-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productItem)))
            .andExpect(status().isBadRequest());

        // Validate the ProductItem in the database
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductItem() throws Exception {
        // Initialize the database
        productItemService.save(productItem);

        int databaseSizeBeforeDelete = productItemRepository.findAll().size();

        // Delete the productItem
        restProductItemMockMvc.perform(delete("/api/product-items/{id}", productItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductItem> productItemList = productItemRepository.findAll();
        assertThat(productItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
