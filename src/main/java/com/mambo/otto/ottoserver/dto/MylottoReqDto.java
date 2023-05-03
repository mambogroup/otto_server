package com.mambo.otto.ottoserver.dto;

import java.util.List;

import com.mambo.otto.ottoserver.domain.Mylotto;

import lombok.Getter;
import lombok.Setter;

public class MylottoReqDto {

    @Getter
    @Setter
    public static class MylottoSaveReqDto {
        private Long inUserId;
        private int inMylottoRound;
        private int inMylottoTr;
        private String vcMylottoNumbers;
        private int inMylottoRank;

        public Mylotto toEntity() {
            return Mylotto.builder().userId(inUserId).round(inMylottoRound).tr(inMylottoTr).number(vcMylottoNumbers)
                    .rank(inMylottoRank)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class MylottoDeleteReqDto {
        private List<Long> inMylottoId;
    }

}
