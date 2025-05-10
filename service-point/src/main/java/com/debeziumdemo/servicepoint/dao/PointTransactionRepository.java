package com.debeziumdemo.servicepoint.dao;

import com.debeziumdemo.servicepoint.domain.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    List<PointTransaction> findAllByOrderId(Long orderId);
}
