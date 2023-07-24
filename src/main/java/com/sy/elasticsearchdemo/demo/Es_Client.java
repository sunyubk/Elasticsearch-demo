package com.sy.elasticsearchdemo.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @ClassName EsClient
 * @Description
 * @Author sunyu
 * @Date 2023/7/24 11:06
 * @Version 1.0
 **/
public class Es_Client {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 关闭客户端连接
        esClient.close();
    }
}
