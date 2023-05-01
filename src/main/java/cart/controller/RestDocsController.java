package cart.controller;

import cart.common.property.RestDocsProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {

    private final ResourceLoader resourceLoader;
    private final RestDocsProperties restDocsProperties;

    public RestDocsController(ResourceLoader resourceLoader,
                              RestDocsProperties restDocsProperties) {
        this.resourceLoader = resourceLoader;
        this.restDocsProperties = restDocsProperties;
    }

    @GetMapping("/api-docs")
    public String apiDocs() {
        return restDocsProperties.getFilePath();
    }
}
