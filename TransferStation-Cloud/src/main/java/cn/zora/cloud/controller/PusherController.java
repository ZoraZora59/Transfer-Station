package cn.zora.cloud.controller;

import cn.zora.base.enumerate.ResultCodeEnum;
import cn.zora.base.model.Response;
import cn.zora.base.util.IpUtils;
import cn.zora.base.model.JobEntity;
import cn.zora.cloud.repository.MissionMapper;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.cloud.controller</h4>
 * <p>任务下发器</p>
 *
 * @author zora
 * @since 2020.04.27
 */
@RestController
@RequestMapping("/pusher")
@Slf4j
@Api(tags = "任务下发器")
public class PusherController {
    @Autowired
    private MissionMapper mapper;

    @PostMapping("/latestJob")
    @ApiOperation("拉取一个未下发的任务")
    @Transactional(rollbackFor = Exception.class)
    public Response<JobEntity> latestJob(@RequestParam Boolean latest, @RequestParam String key, HttpServletRequest request) {
        if (!IpUtils.isKeyRight(key, request)) {
            return Response.unAuthor();
        }
        JobEntity missionEntity = mapper.selectNotPushedOne(latest);
        if (Objects.isNull(missionEntity)) {
            return Response.type(ResultCodeEnum.common_0_207, "没有可下发的任务");
        } else {
            mapper.updatePushTime(missionEntity.getId());
            return Response.success(missionEntity);
        }
    }

    @PostMapping("/returnJob")
    @ApiOperation("任务下载失败，返还到任务列表")
    @Transactional(rollbackFor = Exception.class)
    public Response<Integer> returnJob(@RequestParam Integer id, @RequestParam String key, HttpServletRequest request) {
        if (!IpUtils.isKeyRight(key, request)) {
            return Response.unAuthor();
        }
        return Response.withRes(res -> res.setData(mapper.updatePushTimeReset(id)));
    }
}
