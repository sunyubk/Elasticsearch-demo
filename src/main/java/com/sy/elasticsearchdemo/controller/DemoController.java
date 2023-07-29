package com.sy.elasticsearchdemo.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sy.elasticsearchdemo.entity.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DemoController
 * @Description
 * @Author sunyu
 * @Date 2023/7/29 13:58
 * @Version 1.0
 **/
@RestController()
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @GetMapping("/search")
    public JSONArray search() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(termQueryBuilder).addSort(Sort.by(Sort.Order.desc("price")));
        SearchHits<Product> search = restTemplate.search(nativeSearchQuery, Product.class);
        JSONArray jsonArray = new JSONArray();
        search.forEach(a -> {
            JSONObject from = JSONObject.from(a.getContent());
            jsonArray.add(from);
        });
        return jsonArray;
    }
}
