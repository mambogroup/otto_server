package com.mambo.otto.ottoserver.domain;

import java.util.Optional;

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

    public Optional<User> findByUserPhoneNumber(String phoneNumber) {
        try {
            Optional<User> userOp = Optional.of(eM.createQuery(
                    "select u from User u where u.vcUserHpp=:phonenumber",
                    User.class)
                    .setParameter("phonenumber", phoneNumber)
                    .getSingleResult());
            return userOp;
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
