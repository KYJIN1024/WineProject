package com.wine.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wine.demo.model.User;

@Entity
public class ShopLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne  // User와의 관계 설정
    @JoinColumn(name = "user_id")  // 데이터베이스 내의 외래키 컬럼명
    private User user;

    @ManyToOne  // ProducerEntity와의 관계 설정
    @JoinColumn(name = "shop_id")  // 데이터베이스 내의 외래키 컬럼명
    private ShopEntity shop;

    // 기본 생성자
    public ShopLike() {}

    // 사용자와 생산자 엔터티를 매개변수로 받는 생성자
    public ShopLike(User user, ShopEntity shop) {
        this.user = user;
        this.shop = shop;
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

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }
}