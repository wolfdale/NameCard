package com.micro.namecard.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Component
public class GeneratorUtility {

    @Autowired
    private NameUtils nameUtils;

    /**
     * Builds query for bulk Indexing of name card
     */
    public List<IndexQuery> buildBulkIndexingQueries(long times) {
        List<IndexQuery> queries = new LinkedList<>();

        for (int i = 0; i < times; i++) {
            queries.add(new IndexQueryBuilder()
                    .withId(UUID.randomUUID().toString())
                    .withObject(nameUtils.createRandomNameCard())
                    .build());
        }

        return queries;
    }
}
