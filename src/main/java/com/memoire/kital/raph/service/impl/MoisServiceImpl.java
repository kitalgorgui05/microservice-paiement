package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.MoisService;
import com.memoire.kital.raph.domain.Mois;
import com.memoire.kital.raph.repository.MoisRepository;
import com.memoire.kital.raph.service.dto.MoisDTO;
import com.memoire.kital.raph.service.mapper.MoisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Mois}.
 */
@Service
@Transactional
public class MoisServiceImpl implements MoisService {

    private final Logger log = LoggerFactory.getLogger(MoisServiceImpl.class);

    private final MoisRepository moisRepository;

    private final MoisMapper moisMapper;

    public MoisServiceImpl(MoisRepository moisRepository, MoisMapper moisMapper) {
        this.moisRepository = moisRepository;
        this.moisMapper = moisMapper;
    }

    @Override
    public MoisDTO save(MoisDTO moisDTO) {
        log.debug("Request to save Mois : {}", moisDTO);
        Mois mois = moisMapper.toEntity(moisDTO);
        mois = moisRepository.save(mois);
        return moisMapper.toDto(mois);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MoisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mois");
        return moisRepository.findAll(pageable)
            .map(moisMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MoisDTO> findOne(String id) {
        log.debug("Request to get Mois : {}", id);
        return moisRepository.findById(id)
            .map(moisMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Mois : {}", id);
        moisRepository.deleteById(id);
    }
}
