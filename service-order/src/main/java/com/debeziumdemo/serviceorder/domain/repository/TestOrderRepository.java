package com.debeziumdemo.serviceorder.domain.repository;

import com.debeziumdemo.serviceorder.domain.model.TestOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestOrderRepository extends JpaRepository<TestOrder, Long> {

}
