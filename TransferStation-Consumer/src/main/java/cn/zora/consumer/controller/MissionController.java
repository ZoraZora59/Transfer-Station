package cn.zora.consumer.controller;

import cn.zora.base.model.Response;
import cn.zora.consumer.service.MissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.consumer.timer</h4>
 * <p>任务获取</p>
 *
 * @author zora
 * @since 2020.04.27
 */
@Slf4j
@RestController
@RequestMapping("/getter")
public class MissionController {
    @Autowired
    private MissionService service;

    @PostMapping("/newMission")
    public Response<Object> getNewMission() {
        return Response.withRes(res -> res.setData(service.startNewMission()));
    }
}
