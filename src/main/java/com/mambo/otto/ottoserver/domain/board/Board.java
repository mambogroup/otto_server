package com.mambo.otto.ottoserver.domain.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {

    @Id
    @GeneratedValue
    private Long id;
    private String boardTitle;
    @Column(length = 1000)
    private String content;
}