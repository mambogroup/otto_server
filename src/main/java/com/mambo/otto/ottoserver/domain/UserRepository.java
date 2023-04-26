package com.mambo.otto.ottoserver.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager eM;

    public User save(User user) {
        eM.persist(user);
        return user;
    }
}
