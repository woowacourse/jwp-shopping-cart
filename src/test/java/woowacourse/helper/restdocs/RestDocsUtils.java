package woowacourse.helper.restdocs;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

public class RestDocsUtils {

    public static OperationResponsePreprocessor getResponsePreprocessor() {
        return Preprocessors.preprocessResponse(prettyPrint());
    }

    public static OperationRequestPreprocessor getRequestPreprocessor() {
        return Preprocessors.preprocessRequest(prettyPrint());
    }

}
