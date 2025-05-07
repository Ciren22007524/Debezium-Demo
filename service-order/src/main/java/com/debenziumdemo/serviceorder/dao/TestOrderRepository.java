package com.debenziumdemo.serviceorder.dao;

import com.debenziumdemo.serviceorder.domain.TestOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestOrderRepository extends JpaRepository<TestOrder, Long> {

}
