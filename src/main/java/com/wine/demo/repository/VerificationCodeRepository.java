package com.wine.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wine.demo.model.VerificationCode;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, Long> {

	  VerificationCode findByCodeAndEmail(String code, String email);
}