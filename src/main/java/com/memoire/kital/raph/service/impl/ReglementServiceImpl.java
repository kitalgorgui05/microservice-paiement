package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.ReglementService;
import com.memoire.kital.raph.domain.Reglement;
import com.memoire.kital.raph.repository.ReglementRepository;
import com.memoire.kital.raph.service.dto.ReglementDTO;
import com.memoire.kital.raph.service.mapper.ReglementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Reglement}.
 */
@Service
@Transactional
public class ReglementServiceImpl implements ReglementService {

    private final Logger log = LoggerFactory.getLogger(ReglementServiceImpl.class);

    private final ReglementRepository reglementRepository;

    private final ReglementMapper reglementMapper;

    public ReglementServiceImpl(ReglementRepository reglementRepository, ReglementMapper reglementMapper) {
        this.reglementRepository = reglementRepository;
        this.reglementMapper = reglementMapper;
    }

    @Override
    public ReglementDTO save(ReglementDTO reglementDTO) {
        log.debug("Request to save Reglement : {}", reglementDTO);
        Reglement reglement = reglementMapper.toEntity(reglementDTO);
        reglement = reglementRepository.save(reglement);
        return reglementMapper.toDto(reglement);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReglementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reglements");
        return reglementRepository.findAll(pageable)
            .map(reglementMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ReglementDTO> findOne(String id) {
        log.debug("Request to get Reglement : {}", id);
        return reglementRepository.findById(id)
            .map(reglementMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Reglement : {}", id);
        reglementRepository.deleteById(id);
    }
}
