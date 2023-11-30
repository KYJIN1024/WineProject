package com.wine.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.model.VerificationCode;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, Long> {
	
	 // 인증 코드와 이메일을 기반으로 VerificationCode 엔터티를 찾는 메서드
	  VerificationCode findByCodeAndEmail(String code, String email);
}