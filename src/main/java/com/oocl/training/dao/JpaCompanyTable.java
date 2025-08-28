package com.oocl.training.dao;

import com.oocl.training.model.Company;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyTable extends JpaRepository<Company, Integer> {
}
