package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.FactureService;
import com.memoire.kital.raph.domain.Facture;
import com.memoire.kital.raph.repository.FactureRepository;
import com.memoire.kital.raph.service.dto.FactureDTO;
import com.memoire.kital.raph.service.mapper.FactureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Facture}.
 */
@Service
@Transactional
public class FactureServiceImpl implements FactureService {

    private final Logger log = LoggerFactory.getLogger(FactureServiceImpl.class);

    private final FactureRepository factureRepository;

    private final FactureMapper factureMapper;

    public FactureServiceImpl(FactureRepository factureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
    }

    @Override
    public FactureDTO save(FactureDTO factureDTO) {
        log.debug("Request to save Facture : {}", factureDTO);
        Facture facture = factureMapper.toEntity(factureDTO);
        facture = factureRepository.save(facture);
        return factureMapper.toDto(facture);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FactureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Factures");
        return factureRepository.findAll(pageable)
            .map(factureMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FactureDTO> findOne(String id) {
        log.debug("Request to get Facture : {}", id);
        return factureRepository.findById(id)
            .map(factureMapper::toDto);
    }

    @Override
    public Optional<List<FactureDTO>> rechercheFactureByCode(String code) {
        return Optional.of(factureRepository.getFactureByCodeContains(code)
            .stream().map(factureMapper::toDto).collect(Collectors.toList()));
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Facture : {}", id);
        factureRepository.deleteById(id);
    }
}
