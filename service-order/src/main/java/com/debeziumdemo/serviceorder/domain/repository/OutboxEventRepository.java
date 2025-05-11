package com.debeziumdemo.serviceorder.domain.repository;

import com.debeziumdemo.serviceorder.domain.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

}
