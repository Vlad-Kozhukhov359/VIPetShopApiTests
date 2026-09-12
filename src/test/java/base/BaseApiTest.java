package base;

import config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import loggers.ApiLogger;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.requestSpecification;

public class BaseApiTest {

    @BeforeAll
    public static void setup() {
        RequestSpecification spec = new RequestSpecBuilder()
                .setContentType(ApiConfig.CONTENT_TYPE)
                .addHeader("Accept", ApiConfig.ACCEPT_HEADER)
                .addFilter(new ApiLogger())
                .build();

        requestSpecification = spec;
    }
}
