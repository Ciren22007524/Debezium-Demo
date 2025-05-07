package com.debenziumdemo.serviceproduct.dao;

import com.debenziumdemo.serviceproduct.domain.TestProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestProductRepository extends JpaRepository<TestProduct, Long> {

}
