package com.codingapi.txlcn.tc.id;

import com.codingapi.txlcn.protocol.message.event.SnowFlakeCreateEvent;
import com.codingapi.txlcn.tc.cache.Cache;
import com.codingapi.txlcn.tc.constant.SnowFlakeConstant;
import com.codingapi.txlcn.tc.reporter.TxManagerReporter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WhomHim
 * @description
 * @date Create in 2020-8-15 22:55:46
 */
@Slf4j
public class SnowFlakeInfo implements SnowFlakeStep {

    private TxManagerReporter txManagerReporter;

    public SnowFlakeInfo(TxManagerReporter txManagerReporter) {
        this.txManagerReporter = txManagerReporter;
    }

    @Override
    public void getGroupIdAndLogId() {
        //是否请求过 TM 的标志位
        if (Cache.getKey(SnowFlakeConstant.TAG) == null) {
            log.debug("==> getGroupIdAndLogId");
            // 请求 TM 获得全局唯一 Id
            SnowFlakeCreateEvent snowFlakeCreateEvent =
                    (SnowFlakeCreateEvent) txManagerReporter.requestMsg(new SnowFlakeCreateEvent());
            log.debug("==> snowFlakeCreateEvent:{}", snowFlakeCreateEvent);
            Cache.setKey(SnowFlakeConstant.GROUP_ID, snowFlakeCreateEvent.getGroupId());
            Cache.setKey(SnowFlakeConstant.LOG_ID, snowFlakeCreateEvent.getLogId());
            Cache.setKey(SnowFlakeConstant.TAG, true);
        }

    }
}
