package com.mambo.otto.ottoserver.dto;

import java.util.List;

import com.mambo.otto.ottoserver.domain.Shop;

import lombok.Getter;

@Getter
public class ShopRespDto {
    private List<Shop> shopList;

    public ShopRespDto(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
