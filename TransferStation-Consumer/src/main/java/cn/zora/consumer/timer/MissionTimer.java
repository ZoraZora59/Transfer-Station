package cn.zora.consumer.timer;

import cn.zora.consumer.controller.MissionController;
import cn.zora.consumer.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.consumer.timer</h4>
 * <p>定时器</p>
 *
 * @author zora
 * @since 2020.04.27
 */
@Component
public class MissionTimer {
    @Autowired
    private MissionService service;
    @Scheduled(cron = "0/30 * * * * ?")
    public void getNewMission(){
        service.startNewMission();
    }
}
