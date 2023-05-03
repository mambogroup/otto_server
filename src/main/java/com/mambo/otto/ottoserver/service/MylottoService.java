package com.mambo.otto.ottoserver.service;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mambo.otto.ottoserver.domain.Mylotto;
import com.mambo.otto.ottoserver.domain.MylottoRepository;
import com.mambo.otto.ottoserver.dto.SessionUser;
import com.mambo.otto.ottoserver.dto.MylottoReqDto.MylottoDeleteReqDto;
import com.mambo.otto.ottoserver.dto.MylottoReqDto.MylottoSaveReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MylottoService {
    private final MylottoRepository mylottoRepository;
    private final HttpSession session;

    private SessionUser getSession() {
        return (SessionUser) session.getAttribute("sessionUser");
    }

    @Transactional
    public MylottoSaveReqDto save(MylottoSaveReqDto mylotto) {
        SessionUser sUser = (SessionUser) session.getAttribute("sessionUser");

        if (sUser == null) {
            return null;
        }

        mylotto.setInUserId(sUser.getId());

        mylottoRepository.save(mylotto.toEntity());

        return mylotto;
    }

    public List<Mylotto> findAll() {
        List<Mylotto> mylottos = mylottoRepository.findAll();
        return mylottos;
    }

    public List<Mylotto> findByUserId() {
        if (getSession() == null) {
            return null;
        }

        return mylottoRepository.findByUserId(getSession().getId());
    }

    @Transactional
    public int deleteBylottoId(MylottoDeleteReqDto inMylottoId) {
        Long loginUserId = getSession().getId();

        if (inMylottoId.getInMylottoId().isEmpty()) {
            return 2;
        }

        try {

            for (int i = 0; i < inMylottoId.getInMylottoId().size(); i++) {
                mylottoRepository.findByLottoId(inMylottoId.getInMylottoId().get(i), loginUserId);
                mylottoRepository.deleteBylottoId(inMylottoId.getInMylottoId().get(i));
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }
}
