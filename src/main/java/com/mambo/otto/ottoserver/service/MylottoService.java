package com.mambo.otto.ottoserver.service;

import org.springframework.stereotype.Service;

import com.mambo.otto.ottoserver.domain.MylottoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MylottoService {
    private final MylottoRepository mylottoRepository;
}
