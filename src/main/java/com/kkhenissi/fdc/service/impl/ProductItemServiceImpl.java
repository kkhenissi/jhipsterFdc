package com.kkhenissi.fdc.service.impl;

import com.kkhenissi.fdc.service.ProductItemService;
import com.kkhenissi.fdc.domain.ProductItem;
import com.kkhenissi.fdc.repository.ProductItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductItem}.
 */
@Service
@Transactional
public class ProductItemServiceImpl implements ProductItemService {

    private final Logger log = LoggerFactory.getLogger(ProductItemServiceImpl.class);

    private final ProductItemRepository productItemRepository;

    public ProductItemServiceImpl(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    /**
     * Save a productItem.
     *
     * @param productItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductItem save(ProductItem productItem) {
        log.debug("Request to save ProductItem : {}", productItem);
        return productItemRepository.save(productItem);
    }

    /**
     * Get all the productItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductItem> findAll() {
        log.debug("Request to get all ProductItems");
        return productItemRepository.findAll();
    }


    /**
     * Get one productItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductItem> findOne(Long id) {
        log.debug("Request to get ProductItem : {}", id);
        return productItemRepository.findById(id);
    }

    /**
     * Delete the productItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductItem : {}", id);
        productItemRepository.deleteById(id);
    }
}
