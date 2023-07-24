package com.sy.elasticsearchdemo.demo;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
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
public class Es_Doc_Get {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 根据索引和id指定查询文档
        GetResponse index = esClient.get(new GetRequest("user").id("1001"), RequestOptions.DEFAULT);
        System.out.println(index.getSourceAsString());

        // 关闭客户端连接
        esClient.close();
    }
}
