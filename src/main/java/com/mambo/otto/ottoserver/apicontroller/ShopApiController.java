package com.mambo.otto.ottoserver.apicontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.service.ShopService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShopApiController {
    private final ShopService sS;

    @GetMapping("/shop")
    public ResponseDto<?> findAll() {
        return new ResponseDto<>(200, "판매점을 모두 찾았습니다", sS.findAll());
    }

}
