package com.micro.namecard.api;

import com.micro.namecard.ApplicationConfiguration;
import com.micro.namecard.EsConfiguration;
import com.micro.namecard.core.AdminAsyncImpl;
import com.micro.namecard.core.AdminImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminOps {
    private static final Logger log = LoggerFactory.getLogger(AdminOps.class);

    @Autowired
    AdminImpl adminOps;

    @Autowired
    AdminAsyncImpl asyncAdminOps;

    @Autowired
    EsConfiguration esConfiguration;

    @Autowired
    ApplicationConfiguration appConfig;

    /**
     * Load init data into ES
     * Bulk Indexing.
     *
     * @return IDs of Document Created
     */
    @PutMapping("/init/{count}")
    public ResponseEntity elasticSearchInitData(@PathVariable("count") Long count) {
        if (appConfig.getSyncLimit() > count) {
            return ResponseEntity.ok().body(adminOps.indexRandomNameCard(count));
        } else {
            return ResponseEntity.ok().body(asyncAdminOps.indexRandomNameCardAsync(count));
        }

    }


}
