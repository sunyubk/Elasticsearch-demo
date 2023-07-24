package com.sy.elasticsearchdemo.demo;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @ClassName EsIndexSearch
 * @Description
 * @Author sunyu
 * @Date 2023/7/24 11:38
 * @Version 1.0
 **/
public class Es_Doc_Update {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 修改数据
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user").id("qaZAJ2kzS7KY0uZAAcBPOw");

        // 部分更新
        // updateRequest.doc(XContentType.JSON, "sex", "女");

        // 覆盖更新
        User user = new User();
        user.setName("张三");
        user.setAge(11);
        user.setSex("男");

        updateRequest.doc(JSONObject.toJSONString(user), XContentType.JSON);


        UpdateResponse index = esClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(index);

        // 关闭客户端连接
        esClient.close();
    }
}
