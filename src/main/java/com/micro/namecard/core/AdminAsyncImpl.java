package com.micro.namecard.core;

import com.micro.namecard.EsConfiguration;
import com.micro.namecard.dto.JobStatus;
import com.micro.namecard.utils.GeneratorUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@Scope("prototype")
public class AdminAsyncImpl implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(AdminAsyncImpl.class);

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private GeneratorUtility generatorUtility;

    @Autowired
    private EsConfiguration esConfiguration;
    public int times;
    public String jobId;

    public AdminAsyncImpl() {
    }

    public AdminAsyncImpl(String jobId, int times, ElasticsearchOperations elasticsearchOperations) {
        this.jobId = jobId;
        this.times = times;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void run() {
        log.info("Processing in bakground");
        JobStatus js = new JobStatus(this.jobId);
        // String jobIndex = esConfiguration.getElasticSearchJobIndex();
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(this.jobId)
                .withObject(js)
                .build();

        // Add processing logic

        String docId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of("jobstatus"));
        log.info("Persisted with " + docId);
    }


}
