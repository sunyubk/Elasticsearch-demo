package com.sy.elasticsearchdemo.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @ClassName EsIndexCreate
 * @Description
 * @Author sunyu
 * @Date 2023/7/24 11:14
 * @Version 1.0
 **/
public class Es_Index_Create {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引
        CreateIndexResponse user = esClient.indices().create(new CreateIndexRequest("user"), RequestOptions.DEFAULT);
        System.out.println(user.isAcknowledged());
        // 关闭客户端连接
        esClient.close();
    }
}
