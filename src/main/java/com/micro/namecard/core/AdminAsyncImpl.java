package com.micro.namecard.core;

import com.micro.namecard.async.DataLoaderJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AdminAsyncImpl {
    private static final Logger log = LoggerFactory.getLogger(AdminAsyncImpl.class);

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    DataLoaderJob job;

    /**
     * Index random NameCard Async.
     *
     * @param count - Number of documents to indexed
     * @return jobId - Async Job Id
     */
    public String indexRandomNameCardAsync(long count) {
        String jobId = createJobId();

        try {
            job.loadDataIntoElasticSearch(jobId, count);
        } catch (Exception ex){
            // ToDo - Need better error handling
            log.error("Unable to perform indexing at this moment.");
        }

        return jobId;
    }

    /**
     * Creates a Job ID.
     *
     * @return Job Id
     */
    private String createJobId() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        return String.format("%05d", num);
    }

}
