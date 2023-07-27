package com.sy.elasticsearchdemo.dao;

import com.sy.elasticsearchdemo.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName ProductDao
 * @Description
 * @Author sunyu
 * @Date 2023/7/27 21:22
 * @Version 1.0
 **/
@Repository
public interface ProductDao extends ElasticsearchRepository<Product,Long> {
}
