package com.project.excelreader.dao;

import com.project.excelreader.model.LdRBankDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LdRBankDetailDao extends JpaRepository<LdRBankDetail, Integer> {
    Optional<LdRBankDetail> findByBankCode(String bankCode);
}
