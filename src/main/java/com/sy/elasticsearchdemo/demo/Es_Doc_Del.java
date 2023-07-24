package com.sy.elasticsearchdemo.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @ClassName EsIndexSearch
 * @Description
 * @Author sunyu
 * @Date 2023/7/24 11:38
 * @Version 1.0
 **/
public class Es_Doc_Del {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 根据索引和id指定查询文档
        DeleteResponse index = esClient.delete(new DeleteRequest("user").id("qaZAJ2kzS7KY0uZAAcBPOw"), RequestOptions.DEFAULT);
        System.out.println(index.toString());

        // 关闭客户端连接
        esClient.close();
    }
}
