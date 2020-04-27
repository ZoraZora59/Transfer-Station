package cn.zora.cloud.controller;

import cn.zora.base.model.Response;
import cn.zora.base.util.IpUtils;
import cn.zora.base.model.JobEntity;
import cn.zora.cloud.model.JobListVO;
import cn.zora.cloud.model.JobSetterDTO;
import cn.zora.cloud.repository.MissionMapper;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.cloud.controller</h4>
 * <p>任务接收器</p>
 *
 * @author zora
 * @since 2020.04.24
 */
@RestController
@RequestMapping("/receiver")
@Slf4j
@Api(tags = "任务接收器")
public class ReceiverController {
    @Autowired
    private MissionMapper mapper;

    @PostMapping("/job")
    @ApiOperation("增加新任务")
    public Response<String> newJob(@RequestBody JobSetterDTO jobSetterDTO, HttpServletRequest request) {
        if (!IpUtils.isKeyRight(jobSetterDTO.getKey(), request)) {
            return Response.unAuthor();
        }
        JobEntity entity = new JobEntity();
        if (Objects.nonNull(jobSetterDTO.getDescription())) {
            log.info("Received new job : [ Name = {} , Download Url = {} , Description = {} ]", jobSetterDTO.getTitle(), jobSetterDTO.getDownloadUrl(), jobSetterDTO.getDescription());
            entity.setDescription(jobSetterDTO.getDescription());
        } else {
            log.info("Received new job : [ Name = {} , Download Url = {} ]", jobSetterDTO.getTitle(), jobSetterDTO.getDownloadUrl());
        }
        entity.setTitle(jobSetterDTO.getTitle()).setDownloadUrl(jobSetterDTO.getDownloadUrl());
        mapper.insertEntity(entity);
        String result = JSON.toJSONString(entity);
        return Response.success(result);
    }

    @GetMapping("/job/list")
    @ApiOperation("拉取符合条件的任务列表")
    public Response<JobListVO> getJobList(@RequestParam(required = false) Boolean isPushed, @RequestParam String key, HttpServletRequest request) {
        if (!IpUtils.isKeyRight(key, request)) {
            return Response.unAuthor();
        }
        int all = mapper.count(null);
        int pushed = mapper.count(true);
        int notPushed = mapper.count(false);
        List<JobEntity> entityList = mapper.selectAll(isPushed);
        JobListVO vo = JobListVO.builder()
                .jobEntityList(entityList)
                .notPushed(notPushed)
                .pushed(pushed)
                .total(all)
                .build();
        return Response.success(vo);
    }

    @DeleteMapping("/job")
    @ApiOperation("删除一个任务")
    public Response<Object> deleteJob(@RequestParam Integer id, @RequestParam String key, HttpServletRequest request) {
        if (!IpUtils.isKeyRight(key, request)) {
            return Response.unAuthor();
        }
        return Response.withRes(res -> res.setData(mapper.deleteById(id)));
    }
}
