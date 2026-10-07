package asserts;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetApiAssertions {

    @Step("Проверка удаления несуществующего питомца")
    public static void assertPetDeleted(Response response) {
        String responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode(),
                "Код ответа не совпал с ожидаемым. Ответ: " + responseBody);

        assertEquals("Pet deleted", responseBody,
                "Текст ошибки не совпал с ожидаемым. Получен: " + responseBody);
    }

    @Step("Проверка обновления несуществующего питомца")
    public static void assertPetUpdated(Response response) {
        String responseBody = response.getBody().asString();

        assertEquals(404, response.getStatusCode(),
                "Код ответа не совпал с ожидаемым. Ответ: " + responseBody);

        assertTrue(responseBody.contains("Pet not found"),
                "Ответ не содержит ожидаемое сообщение об ошибке. Получен: " + responseBody);
    }
}
