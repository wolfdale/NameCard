package com.micro.namecard.core;

import com.micro.namecard.async.DataLoaderJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SecureRandom;

@Component
public class AdminAsyncImpl {
    private static final Logger log = LoggerFactory.getLogger(AdminAsyncImpl.class);

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    DataLoaderJob job;

    public String loadDataAsync(int count) throws IOException {
        String jobId = createJobId();
        job.loadDataIntoElasticSearch(jobId, count);
        return jobId;
    }

    private String createJobId() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        return String.format("%05d", num);
    }

}
