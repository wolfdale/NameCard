package com.micro.namecard.async;

import com.micro.namecard.ApplicationConfiguration;
import com.micro.namecard.EsConfiguration;
import com.micro.namecard.core.AdminImpl;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataLoaderJob {
    private static final Logger log = LoggerFactory.getLogger(DataLoaderJob.class);

    @Autowired
    ApplicationConfiguration appConfig;

    @Autowired
    EsConfiguration esConfig;

    @Autowired
    AdminImpl adminImpl;

    @Qualifier("esClient")
    @Autowired
    RestHighLevelClient client;

    @Async
    public void loadDataIntoElasticSearch(String jobId, long count) throws IOException {
        log.info("Processing data in background with total count : {} & page Size : {}", count,
                appConfig.getPageSize());

        long pageSize = appConfig.getPageSize();
        String jobIndex = esConfig.getElasticSearchJobIndex();

        long numberOfPages = 0;

        if (count % pageSize == 0) {
            numberOfPages = count / pageSize;
        } else {
            numberOfPages = count / pageSize + 1;
        }

        log.debug("Total Number of pages {}", numberOfPages);

        // Create the job status document inside 'jobstatus' index
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("jobId", jobId);
        IndexRequest indexRequest = new IndexRequest().id(jobId).index(jobIndex).source(
                json, XContentType.JSON);

        RequestOptions rq = RequestOptions.DEFAULT;
        IndexResponse jobStatusDocument = client.index(indexRequest, rq);
        log.debug("Job status document created with {}", jobStatusDocument.toString());
        for (int i = 1; i <= numberOfPages; i++) {
            // This will persist 1000 Name cars in 'namecard' index
            List<String> docIds = adminImpl.indexRandomNameCard(pageSize);

            // Update the Job status in 'jobstatus' index
            Map<String, List<String>> pageDoc = new HashMap<>();
            pageDoc.put(Integer.toString(i), docIds);
            UpdateRequest updateRequest = new UpdateRequest().id(jobId).index(jobIndex).doc(pageDoc, XContentType.JSON);
            UpdateResponse res = client.update(updateRequest, rq);
            log.debug("Job status document Updated with {}", res.toString());
        }

    }
}
