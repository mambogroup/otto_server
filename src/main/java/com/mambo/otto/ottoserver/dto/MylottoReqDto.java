package com.mambo.otto.ottoserver.dto;

import java.util.List;

import com.mambo.otto.ottoserver.domain.Mylotto;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTH : SW
 * FUNCTION : only can Access to Entity Object
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 클라이언트 요청 시 도메인의 엔티티 객체 대신 사용함
 * </pre>
 * 
 * @toEntity : using for Convert to Object to querying
 */

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
