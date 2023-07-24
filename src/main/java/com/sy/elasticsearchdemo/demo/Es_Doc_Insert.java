package com.sy.elasticsearchdemo.demo;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @ClassName EsIndexSearch
 * @Description
 * @Author sunyu
 * @Date 2023/7/24 11:38
 * @Version 1.0
 **/
public class Es_Doc_Insert {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建数据
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("qaZAJ2kzS7KY0uZAAcBPOw");

        User user = new User();
        user.setName("张三");
        user.setAge(10);
        user.setSex("男");
        indexRequest.source(JSONObject.toJSONString(user), XContentType.JSON);
        IndexResponse index = esClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index);

        // 关闭客户端连接
        esClient.close();
    }
}
