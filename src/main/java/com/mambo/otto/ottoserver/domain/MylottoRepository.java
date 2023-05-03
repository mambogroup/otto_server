package com.mambo.otto.ottoserver.domain;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MylottoRepository {
    private final EntityManager eM;

    public void save(Mylotto mylotto) {
        eM.persist(mylotto);
    }

    public void deleteBylottoId(Long id) {
        eM.createQuery("delete from Mylotto lotto where lotto.inMylottoId = :id").setParameter("id", id)
                .executeUpdate();
    }

    public List<Mylotto> findAll() {
        List<Mylotto> mylottosPs = eM.createQuery("select lotto from Mylotto lotto", Mylotto.class)
                .getResultList();
        return mylottosPs;
    }

    public List<Mylotto> findByUserId(Long id) {
        List<Mylotto> lottoPS = eM
                .createQuery("select lotto from Mylotto lotto where lotto.inUserId =:id", Mylotto.class)
                .setParameter("id", id)
                .getResultList();
        return lottoPS;
    }

    public Mylotto findByLottoId(Long id, Long userId) {
        Mylotto lottoPS = eM
                .createQuery("select lotto from Mylotto lotto where lotto.inMylottoId =:id and lotto.inUserId =:userId",
                        Mylotto.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getSingleResult();
        return lottoPS;
    }

}
