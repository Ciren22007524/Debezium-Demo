package com.service;

import com.constant.TransactionType;
import com.dao.PointTransactionRepository;
import com.domain.PointTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointTransactionRepository pointTransactionRepository;

    public void addEarnPoints(Long orderId, int points, String description) {
        PointTransaction transaction = PointTransaction.builder()
                .orderId(orderId)
                .type(TransactionType.EARN)
                .points(points)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
        pointTransactionRepository.save(transaction);
    }

    public void addRedeemPoints(Long orderId, int points, String description) {
        PointTransaction transaction = PointTransaction.builder()
                .orderId(orderId)
                .type(TransactionType.REDEEM)
                .points(points)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
        pointTransactionRepository.save(transaction);
    }

    public List<PointTransaction> getPointsByOrder(Long orderId) {
        return pointTransactionRepository.findAllByOrderId(orderId);
    }
}
