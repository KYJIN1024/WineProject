package com.wine.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wine.demo.model.User;

@Entity
public class JobLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne  // User와의 관계 설정
    @JoinColumn(name = "user_id")  // 데이터베이스 내의 외래키 컬럼명
    private User user;

    @ManyToOne  // ProducerEntity와의 관계 설정
    @JoinColumn(name = "job_id")  // 데이터베이스 내의 외래키 컬럼명
	private JobEntity job;

    // 기본 생성자
    public JobLike() {}

    // 사용자와 생산자 엔터티를 매개변수로 받는 생성자
    public JobLike(User user, JobEntity job) {
        this.user = user;
        this.job = job;
    }
    

    // Getter와 Setter 메서드

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

    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }
}