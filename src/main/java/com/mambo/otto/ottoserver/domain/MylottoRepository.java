package com.mambo.otto.ottoserver.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MylottoRepository {
    private final EntityManager eM;

    @Transactional
    public void save(Mylotto mylotto) {
        eM.persist(mylotto);
    }

    @Transactional
    public void deleteById(Long id) {
        eM.createQuery("delete from Mylotto lotto where lotto.inUserId = :id").setParameter("id", id)
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

    public Mylotto findByLottoId(Long id) {
        Mylotto lottoPS = eM
                .createQuery("select lotto from Mylotto lotto where lotto.inMylottoId =:id", Mylotto.class)
                .setParameter("id", id)
                .getSingleResult();
        return lottoPS;
    }

}
