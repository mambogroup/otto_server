package com.mambo.otto.ottoserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * AUTH : SW
 * FUNCTION : return To Client
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 클라이언트 요청결과를 반환하는 형태를 정의
 * </pre>
 * 
 */

@AllArgsConstructor
@Getter
@Setter
public class ResponseDto<R> {
    private Integer code;
    private String msg;
    private R data;
}
