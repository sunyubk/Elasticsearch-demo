package com.sy.elasticsearchdemo.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @ClassName EsIndexDel
 * @Description
 * @Author sunyu
 * @Date 2023/7/24 12:49
 * @Version 1.0
 **/
public class Es_Index_Del {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        AcknowledgedResponse user = esClient.indices().delete(new DeleteIndexRequest("user"), RequestOptions.DEFAULT);
        System.out.println(user.isAcknowledged());

        // 关闭客户端连接
        esClient.close();
    }
}
