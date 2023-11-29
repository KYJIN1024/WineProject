package com.wine.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wine.demo.model.User;

@Entity
public class ProducerLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne  // User와의 관계 설정
    @JoinColumn(name = "user_id")  // 데이터베이스 내의 외래키 컬럼명
    private User user;

    @ManyToOne  // ProducerEntity와의 관계 설정
    @JoinColumn(name = "producer_id")  // 데이터베이스 내의 외래키 컬럼명
    private ProducerEntity producer;

    // 기본 생성자
    public ProducerLike() {}

    // 사용자와 생산자 엔터티를 매개변수로 받는 생성자
    public ProducerLike(User user, ProducerEntity producer) {
        this.user = user;
        this.producer = producer;
    }  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProducerEntity getProducer() {
        return producer;
    }

    public void setProducer(ProducerEntity producer) {
        this.producer = producer;
    }
}