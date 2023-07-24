package com.sy.elasticsearchdemo.demo;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
public class Es_Doc_Insert_Batch {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 批量插入数据
        User user = new User();
        user.setName("张三");
        user.setAge(10);
        user.setSex("男");

        User user1 = new User();
        user1.setName("李四");
        user1.setAge(11);
        user1.setSex("女");
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest().index("user").id("1001").source(JSONObject.toJSONString(user), XContentType.JSON));
        bulkRequest.add(new IndexRequest().index("user").id("1002").source(JSONObject.toJSONString(user1), XContentType.JSON));
        bulkRequest.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON,"name","王五","age","12","sex","男"));
        bulkRequest.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON,"name","赵六","age","13","sex","男"));
        bulkRequest.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON,"name","田七","age","14","sex","女"));
        bulkRequest.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON,"name","吴八","age","11","sex","男"));
        BulkResponse bulk = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.getTook());
        System.out.println(bulk.getItems());
        // 关闭客户端连接
        esClient.close();
    }
}
