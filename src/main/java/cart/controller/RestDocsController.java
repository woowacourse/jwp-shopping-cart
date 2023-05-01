package cart.controller;

import cart.common.property.RestDocsProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {
    
    private final RestDocsProperties restDocsProperties;

    public RestDocsController(RestDocsProperties restDocsProperties) {
        this.restDocsProperties = restDocsProperties;
    }

    @GetMapping("/api-docs")
    public String apiDocs() {
        return restDocsProperties.getFilePath();
    }
}
