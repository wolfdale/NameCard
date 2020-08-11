package com.micro.namecard;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class EsConfiguration extends AbstractElasticsearchConfiguration {

    @Value(value = "${es.bootstrap.server}")
    private String elasticBootServer;

    @Value(value = "${es.index.name}")
    private String elasticSearchIndex;

    @Value(value = "${es.job.index.name}")
    private String elasticSearchJobIndex;

    @Value(value = "${data.generator.limit}")
    private int syncLimit;

    /**
     * Elastic Search Client Configuration and Creation.
     */
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticBootServer)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    public String getElasticSearchIndex() {
        return this.elasticSearchIndex;
    }

    public int getSyncLimit() {
        return this.syncLimit;
    }

    public String getElasticSearchJobIndex(){
        return this.elasticSearchJobIndex;
    }
}
