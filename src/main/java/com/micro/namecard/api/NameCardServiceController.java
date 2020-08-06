package com.micro.namecard.api;

import com.micro.namecard.dto.NameCardDTO;
import com.micro.namecard.utils.NameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class NameCardServiceController {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private NameUtils randomNameCard;

    /**
     * Saves a single name card.
     *
     * @param nameCard Card DTO object
     * @return Id of Document Created
     */
    @PostMapping("/namecard")
    public String saveNameCard(@RequestBody NameCardDTO nameCard) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(UUID.randomUUID().toString())
                .withObject(randomNameCard.createRandomNameCard())
                .build();

        String docId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of("namecard"));
        return docId;
    }

    /**
     * Saves multiple random cards.
     * Bulk Indexing.
     *
     * @return Id of Document Created
     */
    @PostMapping("/namecard/{times}")
    public List<String> saveMultipleRandomNameCard(@PathParam("times") Integer times) {
        List<IndexQuery> indexQuery = buildBulkIndexingQueries(100);
        List<String> docIds = elasticsearchOperations.bulkIndex(indexQuery, IndexCoordinates.of("namecard"));
        return docIds;
    }

    /**
     * Get all Name Cards
     * @return NameCardDTO
     */
    @GetMapping("/namecard/{name}")
    public List<String> getNameCardByName() {
        Query query = Query.findAll();
        SearchHits<NameCardDTO> search = elasticsearchOperations.search(query, NameCardDTO.class);
        List<String> listOfNameCard = new LinkedList<>();

        for (SearchHit<NameCardDTO> sr : search) {
            listOfNameCard.add(sr.getContent().toString());
        }

        return listOfNameCard;
    }

    /**
     * Builds query for bulk Indexing of name card
     */
    private List<IndexQuery> buildBulkIndexingQueries(int times) {
        List<IndexQuery> queries = new LinkedList<>();

        for (int i = 0; i < times; i++) {
            queries.add(new IndexQueryBuilder()
                .withId(UUID.randomUUID().toString())
                .withObject(randomNameCard.createRandomNameCard())
                .build());
        }

        return queries;
    }
}
