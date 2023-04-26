package com.mambo.otto.ottoserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_mylotto")
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
}
