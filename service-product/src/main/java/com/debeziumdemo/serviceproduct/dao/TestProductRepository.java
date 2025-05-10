package com.debeziumdemo.serviceproduct.dao;

import com.debeziumdemo.serviceproduct.domain.TestProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestProductRepository extends JpaRepository<TestProduct, Long> {

}
