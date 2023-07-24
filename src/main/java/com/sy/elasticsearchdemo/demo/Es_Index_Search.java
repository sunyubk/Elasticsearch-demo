package com.sy.elasticsearchdemo.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * @ClassName EsIndexSearch
 * @Description
 * @Author sunyu
 * @Date 2023/7/24 11:38
 * @Version 1.0
 **/
public class Es_Index_Search {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 获取索引
        SearchResponse user = esClient.search(new SearchRequest("user"), RequestOptions.DEFAULT);
        GetIndexResponse user1 = esClient.indices().get(new GetIndexRequest("user"), RequestOptions.DEFAULT);
        System.out.println("查询索引user：" + user);
        System.out.println("查询索引user：" + user1.getAliases());

        // 关闭客户端连接
        esClient.close();
    }
}
