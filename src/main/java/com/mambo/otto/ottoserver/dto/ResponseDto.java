package com.mambo.otto.ottoserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDto<R> {
    private Integer code;
    private String msg;
    private R data;
}
