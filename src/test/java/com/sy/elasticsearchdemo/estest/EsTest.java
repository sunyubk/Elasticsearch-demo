package com.sy.elasticsearchdemo.estest;

import com.sy.elasticsearchdemo.dao.ProductDao;
import com.sy.elasticsearchdemo.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName estest
 * @Description 只列举一部分使用方式。
 * @Author sunyu
 * @Date 2023/7/27 21:32
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EsTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // 注入 ElasticsearchRestTemplate
    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @Autowired
    ProductDao productDao;

    @Test
    public void createIndex() {
        // 创建索引，系统初始化会自动创建索引
        logger.info("创建索引");
    }

    @Test
    public void deleteIndex() {
        // 删除索引，老版本的spring-data-elasticsearch 是deleteIndex，新版本的对整体进行了修改，索引操作由 indexOps() 获取到索引操作类，再调用想要操作索引的方法
        boolean delete = restTemplate.indexOps(Product.class).delete();
        logger.info("删除索引 = {}", delete);
    }


    /**
     * 新增文档
     */
    @Test
    public void addDco() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(59999.0D);
        product.setImages("test1");
        Product save = productDao.save(product);
        logger.info("新增文档 = {}", save);
    }

    /**
     * 修改文档
     */
    @Test
    public void updateDoc() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(59998.0D);
        product.setImages("test1");
        Product save = productDao.save(product);
        logger.info("更新文档 = {}", save);
    }

    /**
     * 根据id查询文档
     */
    @Test
    public void findById() {
        Optional<Product> product = productDao.findById(1L);
        logger.info("根据id查询文档 = {}", product.get());
    }

    /**
     * 查询所有文档
     */
    @Test
    public void findAll() {
        Iterable<Product> products = productDao.findAll();
        products.forEach(System.out::println);
    }

    /**
     * 删除
     */
    @Test
    public void delete() {
        productDao.delete(new Product().setId(1L));
    }

    /**
     * 批量新增
     */
    @Test
    public void saveBatch() {
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setTitle("["+i+"]小米手机");
            product.setCategory("手机");
            product.setPrice(59999.0 + i);
            product.setImages("test" + i);
            list.add(product);
        }
        Iterable<Product> products = productDao.saveAll(list);
        products.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void findByPageable() {
        Sort sort = Sort.by(Sort.Order.desc("price"));
        PageRequest pageRequest = PageRequest.of(0, 2, sort);
        Page<Product> products = productDao.findAll(pageRequest);
        products.getContent().forEach(System.out::println);
    }

    /**
     * 条件查询
     * termQuery：精确查询，不对字段的值进行分词
     */
    @Test
    public void termQuery() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(termQueryBuilder);
        SearchHits<Product> search = restTemplate.search(nativeSearchQuery, Product.class);
        search.forEach(a -> {
            System.out.println(a.getContent());
        });
    }

    /**
     * 条件分页查询
     */
    @Test
    public void termQueryByPage() {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Order.desc("price")));
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(termQueryBuilder).withPageable(pageRequest).build();
        SearchHits<Product> search = restTemplate.search(nativeSearchQuery, Product.class);
        for (SearchHit<Product> productSearchHit : search) {
            System.out.println(productSearchHit.getContent());
        }
    }

    /**
     * 多条件查询
     * boolQuery：多条件查询
     * must：所有条件必须都符合   and
     * should：有符合的条件即可   or
     */
    @Test
    public void boolQuery() {
        List<MatchQueryBuilder> matchQueryBuilders = List.of(QueryBuilders.matchQuery("title", "9"), QueryBuilders.matchQuery("title", "8"));
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // boolQueryBuilder.must().addAll(matchQueryBuilders);
        boolQueryBuilder.should().addAll(matchQueryBuilders);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
        SearchHits<Product> search = restTemplate.search(nativeSearchQuery, Product.class);
        for (SearchHit<Product> productSearchHit : search) {
            System.out.println(productSearchHit.getContent());
        }
    }

    /**
     * 范围查询
     */
    @Test
    public void rangeQuery() {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").gte(60005);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(rangeQueryBuilder).build().addSort(Sort.by(Sort.Order.desc("price")));
        SearchHits<Product> search = restTemplate.search(nativeSearchQuery, Product.class);
        for (SearchHit<Product> productSearchHit : search) {
            System.out.println(productSearchHit.getContent());
        }
    }

}
