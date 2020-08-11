package com.micro.namecard.dto;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "jobstatus", createIndex = true, replicas = 1, shards = 1)
public class JobStatus {
    @Field(type = FieldType.Text)
    private String jobId;

    public JobStatus(String jobId){
        this.jobId = jobId;
    }
}
