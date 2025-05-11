package com.debeziumdemo.serviceorder.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxCleanupJob {

    private final JdbcTemplate jdbc;

    /** 每 10 分鐘把「寫入已超過 30 分鐘」的事件標記為 processed=1 */
    @Scheduled(cron = "0 */10 * * * *")
    public void markProcessed() {
        int rows = jdbc.update("""
            UPDATE outbox_event
               SET processed   = 1,
                   processed_at = NOW()
             WHERE processed   = 0
               AND createdat   < DATE_SUB(NOW(), INTERVAL 30 MINUTE)
            """);
        log.info("Outbox 標記 processed=1：{} rows", rows);
    }

    /** 每天 03:30 清掉 30 天前且已標記 processed=1 的事件 */
    @Scheduled(cron = "0 30 3 * * *")
    public void purgeOldEvents() {
        int rows = jdbc.update("""
            DELETE FROM outbox_event
             WHERE processed     = 1
               AND processed_at < DATE_SUB(NOW(), INTERVAL 30 DAY)
            """);
        log.info("Outbox 清理：刪除 {} rows", rows);
    }
}