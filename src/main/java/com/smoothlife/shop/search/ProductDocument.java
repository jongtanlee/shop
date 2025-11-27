package com.smoothlife.shop.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Getter
@Document(indexName = "shop-products")
public class ProductDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Date, format = DateFormat.date_time, pattern = "uuuu-MM-dd'T'HH:mm:ssX") // 저장할 때 패턴
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ssX", timezone = "UTC") // 응답을 반환할 때 패턴ㅋ
    private Instant updatedAt;

    public ProductDocument() {
    }

    public ProductDocument(String id, String name, String brand, String category, Integer price, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.updatedAt = updatedAt;
    }
}
