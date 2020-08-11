package com.micro.namecard.api;

import com.micro.namecard.EsConfiguration;
import com.micro.namecard.core.AdminAsyncImpl;
import com.micro.namecard.core.AdminImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

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
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    @Qualifier("taskExecutor")
    TaskExecutor taskExecutor;

    /**
     * Load init data into ES
     * Bulk Indexing.
     *
     * @return IDs of Document Created
     */
    @PutMapping("/init/{times}")
    public ResponseEntity elasticSearchInitData(@PathVariable("times") Integer times) {
        if (esConfiguration.getSyncLimit() > times) {
            return ResponseEntity.ok().body(adminOps.saveMultipleRandomNameCard(times));
        }
        else {
            String jobId = createJobId();
            taskExecutor.execute(new AdminAsyncImpl(jobId, times, elasticsearchOperations));
            return ResponseEntity.ok().body(jobId);
        }

    }

    private String createJobId() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        return String.format("%05d", num);
    }
}
