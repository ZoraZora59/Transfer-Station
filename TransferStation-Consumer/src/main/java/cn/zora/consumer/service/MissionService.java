package cn.zora.consumer.service;

import cn.zora.base.config.KeyConfig;
import cn.zora.consumer.config.Constants;
import cn.zora.consumer.model.ConsumerResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * <h3>ZoraCloudTransferStation</h3>
 * <h4>cn.zora.consumer.service</h4>
 * <p>任务中心</p>
 *
 * @author zora
 * @since 2020.04.27
 */
@Service
@Slf4j
public class MissionService {
    public boolean startNewMission() {
        String requestUrl = Constants.STATION_PREFIX + "/latestJob";
        HttpClient client = HttpClientBuilder.create().build();
        RequestConfig requestConfig = RequestConfig.DEFAULT;
        URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(requestUrl);
            uriBuilder.addParameter("latest", String.valueOf(false));
            uriBuilder.addParameter("key", KeyConfig.SECRET_KEY);
            requestUrl = uriBuilder.build().toString();
        } catch (URISyntaxException ignore) {
            log.error("获取新任务的URL生成失败");
            return false;
        }
        HttpPost httpRequest = new HttpPost(requestUrl);
        httpRequest.setConfig(requestConfig);
        try {
            HttpResponse response = client.execute(httpRequest);
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");
            ConsumerResponse consumerResponse = JSON.parseObject(json, ConsumerResponse.class);
            if (consumerResponse.getCode().equals(207)) {
                log.info(consumerResponse.getMessage());
            } else {
                String downloadUrl = consumerResponse.getData().getDownloadUrl();
                Pair<Boolean, String> callBack = callAria2Rpc(downloadUrl);
                //TODO:增加服务端反馈
                return callBack.getLeft();
            }
        } catch (IOException e) {
            log.error("HTTP连接服务器失败", e);
            return false;
        }
        return true;
    }

    public Pair<Boolean, String> callAria2Rpc(String url) {
        HttpClient client = HttpClientBuilder.create().build();
        String rpcJson = "{\"jsonrpc\":\"2.0\",\"method\":\"aria2.addUri\",\"id\":1,\"params\":[[\"" + url + "\"],{}]}";
        String requestUrl = "http://localhost:6800/jsonrpc?tm=" + System.currentTimeMillis();
        log.info("Reuqest Url = {}", requestUrl);
        log.info("Rpc Json = {}", rpcJson);
        HttpPost httpRequest = new HttpPost(requestUrl);
        StringEntity stringEntity = new StringEntity(rpcJson, ContentType.APPLICATION_JSON);
        httpRequest.setEntity(stringEntity);
        try {
            HttpResponse response = client.execute(httpRequest);
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
            if (jsonObject.containsKey("error")) {
                JSONObject innerObject = JSON.parseObject(jsonObject.get("error").toString());
                String errorMessage = "任务下载失败，地址为：[ " + url + " ]，失败原因：[ " + innerObject.get("message") + " ]";
                log.error(errorMessage);
                return Pair.of(false, errorMessage);
            } else {
                log.info("任务加载到Aria2下载列表成功，地址：[ {} ]", url);
                return Pair.of(true, "任务加载到Aria2下载列表成功");
            }
        } catch (IOException e) {
            log.error("连接本地Aria2失败");
            return Pair.of(false, "连接本地Aria2失败");
        }
    }
}
