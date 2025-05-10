package com.debeziumdemo.servicepoint.controller;

import com.debeziumdemo.servicepoint.service.PointService;
import com.debeziumdemo.servicepoint.domain.PointTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping("/earn")
    public void earnPoints(@RequestParam Long orderId,
                           @RequestParam int points,
                           @RequestParam(required = false) String description) {
        pointService.addEarnPoints(orderId, points, description);
    }

    @PostMapping("/redeem")
    public void redeemPoints(@RequestParam Long orderId,
                             @RequestParam int points,
                             @RequestParam(required = false) String description) {
        pointService.addRedeemPoints(orderId, points, description);
    }

    @GetMapping("/order/{orderId}")
    public List<PointTransaction> getPointsByOrder(@PathVariable Long orderId) {
        return pointService.getPointsByOrder(orderId);
    }
}
