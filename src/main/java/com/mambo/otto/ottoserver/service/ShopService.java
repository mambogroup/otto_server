package com.mambo.otto.ottoserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mambo.otto.ottoserver.domain.Shop;
import com.mambo.otto.ottoserver.domain.ShopRepositoey;
import com.mambo.otto.ottoserver.dto.ShopRespDto;

import lombok.RequiredArgsConstructor;

/**
 * AUTH : SW
 * FUNCTION : Connection with DB and Querying / ! Transectional on Service.java
 * DATE : 2023.05.08
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 외부 서버 접근 레파지토리
 * </pre>
 * 
 */

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepositoey sR;

    public ShopRespDto findAll() {
        List<Shop> shopList = sR.findAll();
        ShopRespDto shopPS = new ShopRespDto(shopList);
        return shopPS;
    }
}
