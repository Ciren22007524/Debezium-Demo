package com.debenziumdemo.serviceorder.dao;

import com.debenziumdemo.serviceorder.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

}
