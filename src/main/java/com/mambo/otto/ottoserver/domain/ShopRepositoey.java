package com.mambo.otto.ottoserver.domain;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * AUTH : SW
 * FUNCTION : Connection with DB and Querying / ! Transectional on Service.java
 * DATE : 2023.05.08
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 외부 서버 접근 레파지토리.
 * </pre>
 * 
 */

@Repository
@RequiredArgsConstructor
public class ShopRepositoey {
    private final EntityManager eM;

    public List<Shop> findAll() {
        List<Shop> shopList = eM.createQuery("select shop from Shop shop", Shop.class).getResultList();
        return shopList;
    }

}
