package com.mambo.otto.ottoserver.domain;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.mambo.otto.ottoserver.dto.UserReqDto.UserUpdateReqDto;

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

    public Optional<User> findByUserId(Long id) {
        try {
            Optional<User> userOp = Optional.of(eM.createQuery(
                    "select u from User u where u.inUserId=:id",
                    User.class)
                    .setParameter("id", id)
                    .getSingleResult());
            return userOp;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void update(UserUpdateReqDto user) {

        eM.createQuery(
                "update User u set u.vcUserEmail =:vcUserEmail where inUserId = :id")
                .setParameter("vcUserEmail", user.getVcUserEmail())
                .setParameter("id", user.getInUserId()).executeUpdate();

    }

    public void deleteBy(Long id) {
        eM.createQuery("delete from User u where u.inUserId = :id").setParameter("id", id)
                .executeUpdate();
    }
}
