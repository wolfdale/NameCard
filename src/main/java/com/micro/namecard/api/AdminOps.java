package com.micro.namecard.api;

import com.micro.namecard.core.AdminImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminOps {
    private static final Logger log = LoggerFactory.getLogger(AdminOps.class);

    @Autowired
    AdminImpl adminOps;

    /**
     * Load init data into ES
     * Bulk Indexing.
     *
     * @return IDs of Document Created
     */
    @PutMapping("/init/{times}")
    public List<String> elasticSearchInitData(@PathVariable("times") Integer times) {
        return adminOps.saveMultipleRandomNameCard(times);
    }


}
