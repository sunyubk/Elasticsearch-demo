package com.sy.elasticsearchdemo.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @ClassName Product
 * @Description
 * @Author sunyu
 * @Date 2023/7/27 20:55
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@Document(indexName = "product", shards = 3, replicas = 1)
public class Product {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword, index = false)
    private String images;
}
