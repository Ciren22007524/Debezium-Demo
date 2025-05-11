package com.debeziumdemo.serviceproduct.domain.repository;

import com.debeziumdemo.serviceproduct.domain.model.TestProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestProductRepository extends JpaRepository<TestProduct, Long> {

}
