package com.micro.namecard.core;

import com.micro.namecard.EsConfiguration;
import com.micro.namecard.utils.GeneratorUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class AdminImpl {
    private static final Logger log = LoggerFactory.getLogger(AdminImpl.class);

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private GeneratorUtility generatorUtility;

    @Autowired
    private EsConfiguration esConfiguration;

    public List<String> saveMultipleRandomNameCard(long times) {
        List<IndexQuery> indexQuery = generatorUtility.buildBulkIndexingQueries(times);
        List<String> docIds = new LinkedList<>();
        String index = esConfiguration.getElasticSearchIndex();

        try {
            docIds = elasticsearchOperations.bulkIndex(indexQuery, IndexCoordinates.of(index));
        } catch (Exception e) {
            log.error("Error to load data into Elastic Search.");
        }
        
        return docIds;
    }
}
