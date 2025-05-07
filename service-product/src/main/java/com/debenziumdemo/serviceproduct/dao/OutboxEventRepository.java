package com.debenziumdemo.serviceproduct.dao;

import com.debenziumdemo.serviceproduct.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

}
