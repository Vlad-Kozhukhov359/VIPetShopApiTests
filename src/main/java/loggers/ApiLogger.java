package loggers;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiLogger implements OrderedFilter {

    private static final Logger logger = LogManager.getLogger(ApiLogger.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        logger.info("");
        logRequest(requestSpec);
        Response response = ctx.next(requestSpec, responseSpec);
        logResponse(response);
        logger.info("");
        return response;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    private void logRequest(FilterableRequestSpecification requestSpec) {
        logger.info("==============================================================================");
        logger.info("                              REQUEST");
        logger.info("==============================================================================");
        logger.info("");
        logger.info("Method: {}", requestSpec.getMethod());
        logger.info("URI:    {}", requestSpec.getURI());
        logger.info("");
        logger.info("Headers:");
        requestSpec.getHeaders().forEach(header -> {
            logger.info("  {}: {}", header.getName(), header.getValue());
        });
        if (requestSpec.getBody() != null && !requestSpec.getBody().toString().isEmpty()) {
            logger.info("");
            logger.info("Body:");
            logger.info(formatBody(requestSpec.getBody().toString()));
        }
    }

    private void logResponse(Response response) {
        logger.info("");
        logger.info("==============================================================================");
        logger.info("                              RESPONSE");
        logger.info("==============================================================================");
        logger.info("");
        logger.info("Status Code: {}", response.getStatusCode());
        logger.info("Status Line: {}", response.getStatusLine());
        logger.info("");
        logger.info("Headers:");
        response.getHeaders().forEach(header -> {
            logger.info("  {}: {}", header.getName(), header.getValue());
        });
        logger.info("");
        logger.info("Body:");
        logger.info(formatBody(response.getBody().asString()));
    }

    private String formatBody(String body) {
        if (body == null || body.trim().isEmpty()) {
            return "  (empty)";
        }
        if (body.startsWith("{") || body.startsWith("[")) {
            try {
                return prettyPrintJson(body);
            } catch (Exception e) {
                return "  " + body;
            }
        }
        return "  " + body;
    }

    private String prettyPrintJson(String json) {
        StringBuilder sb = new StringBuilder();
        int indent = 0;
        boolean inString = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
            }

            if (!inString) {
                switch (c) {
                    case '{':
                    case '[':
                        sb.append(c).append('\n');
                        indent++;
                        addIndent(sb, indent);
                        break;
                    case '}':
                    case ']':
                        sb.append('\n');
                        indent--;
                        addIndent(sb, indent);
                        sb.append(c);
                        break;
                    case ',':
                        sb.append(c).append('\n');
                        addIndent(sb, indent);
                        break;
                    default:
                        sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }

        return "  " + sb.toString().replace("\n", "\n  ");
    }

    private void addIndent(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
    }
}
