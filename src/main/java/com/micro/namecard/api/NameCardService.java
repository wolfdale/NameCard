package com.micro.namecard.api;

import com.micro.namecard.dto.NameCard;
import com.micro.namecard.utils.NameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/namecard")
public class NameCardService {
    private static final Logger log = LoggerFactory.getLogger(NameCardService.class);
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
    public String saveNameCard(@RequestBody NameCard nameCard) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(UUID.randomUUID().toString())
                .withObject(randomNameCard.createRandomNameCard())
                .build();

        String docId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of("namecard"));
        return docId;
    }

    /**
     * Get all Name Cards
     *
     * @return NameCardDTO
     */
    @GetMapping("/namecard/{name}")
    public List<String> getNameCardByName() {
        Query query = Query.findAll();
        SearchHits<NameCard> search = elasticsearchOperations.search(query, NameCard.class);
        List<String> listOfNameCard = new LinkedList<>();

        for (SearchHit<NameCard> sr : search) {
            listOfNameCard.add(sr.getContent().toString());
        }

        return listOfNameCard;
    }
}
