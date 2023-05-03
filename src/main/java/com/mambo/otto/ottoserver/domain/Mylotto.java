package com.mambo.otto.ottoserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_mylotto")
@NoArgsConstructor
public class Mylotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_mylotto_id")
    private Long inMylottoId;
    @Column(name = "in_user_id")
    private Long inUserId;
    @Column(name = "in_mylotto_round")
    private int inMylottoRound;
    @Column(name = "in_mylotto_tr")
    private int inMylottoTr;
    @Column(name = "vc_mylotto_numbers", nullable = false)
    private String vcMylottoNumbers;
    @Column(name = "in_mylotto_rank")
    private int inMylottoRank;

    @Builder
    public Mylotto(Long lottoId, Long userId, int round, int tr, String number, int rank) {
        this.inMylottoId = lottoId;
        this.inUserId = userId;
        this.inMylottoRound = round;
        this.inMylottoTr = tr;
        this.vcMylottoNumbers = number;
        this.inMylottoRank = rank;

    }
}
