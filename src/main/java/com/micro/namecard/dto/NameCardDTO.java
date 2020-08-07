package com.micro.namecard.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "namecard", createIndex = true, replicas = 3, shards = 1)
public class NameCardDTO {
    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Long)
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public NameCardDTO(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "[NameCard] With Name " + this.name + " Age : " + this.age;
    }
}
