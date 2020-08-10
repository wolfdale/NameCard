package com.micro.namecard.dto;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "${es.index.name}", createIndex = true, replicas = 3, shards = 1)
public class NameCard {
    @Field(type = FieldType.Text)
    private String uuid;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Long)
    private String age;

    public NameCard(){

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public NameCard(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "[NameCard ID " + this.uuid + "] With Name " + this.name + " Age : " + this.age;
    }
}
