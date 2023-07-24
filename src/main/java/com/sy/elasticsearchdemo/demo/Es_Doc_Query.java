package com.sy.elasticsearchdemo.demo;

import org.apache.http.HttpHost;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName EsIndexSearch
 * @Description
 * @Author sunyu
 * @Date 2023/7/24 11:38
 * @Version 1.0
 **/
public class Es_Doc_Query {

    public static void main(String[] args) throws IOException {
        // 创建客户端，并进行连接
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 高级查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        // 查询所有
        // searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));

        // 根据条件进行查询
        // searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchQuery("name", "张三")));
        // searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.rangeQuery("age").gt(11)));
        // searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 11)));

        //想要查询的字段
        // String[] includesField = {"name", "age"};
        //排除的字段
        // String[] excludesField = {};


        // 分页查询
        // searchRequest.source(
        //         new SearchSourceBuilder().query(
        //                         QueryBuilders.matchAllQuery())
        //                 .fetchSource(includesField, excludesField)
        //                 .from(0)
        //                 .size(3)
        //                 .sort("age", SortOrder.DESC)
        // );

        // 组合查询
        // searchRequest.source(
        //         new SearchSourceBuilder().query(
        //                 QueryBuilders.boolQuery()
        //                         .must(QueryBuilders.matchQuery("age", 11))
        //                         .should(QueryBuilders.matchQuery("name", "李四"))
        //         )
        // );

        // 范围查询
        // searchRequest.source(
        //         new SearchSourceBuilder().query(
        //                 QueryBuilders.rangeQuery("age").gte(11)
        //         )
        // );

        // 模糊查询
        // searchRequest.source(
        //         new SearchSourceBuilder().query(
        //                 QueryBuilders.fuzzyQuery("name", "张")
        //                         .fuzziness(Fuzziness.ONE) // 指定可以相差几个字符
        //         )
        // );

        // 高亮查询
        // searchRequest.source(
        //         new SearchSourceBuilder().query(
        //                 QueryBuilders.fuzzyQuery("name", "张")
        //                         .fuzziness(Fuzziness.ONE) // 指定可以相差几个字符
        //         ).highlighter(new HighlightBuilder()
        //                 .preTags("<font color='red'>") // 设置高亮的前置标签
        //                 .postTags("</font>") // 设置高亮的后置标签
        //                 .field("name")
        //         )
        // );

        // 聚合查询
        searchRequest.source(
                new SearchSourceBuilder().aggregation(
                        // AggregationBuilders.max("maxAge").field("age") // 查询最大值
                        AggregationBuilders.terms("termsAge").field("age") // 分组查询
                )
        );


        SearchResponse search = esClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search);
        SearchHits hits = search.getHits();
        hits.forEach(a -> {
            System.out.println(a.getSourceAsString());
        });

        // 关闭客户端连接
        esClient.close();
    }
}
