package asserts;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetApiAssertions {

    @Step("Попытка удаления несуществующего питомца")
    public static void assertPetDeleted(Response response) {
        String responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode(),
                "Код ответа не совпал с ожидаемым. Ответ: " + responseBody);

        assertEquals("Pet deleted", responseBody,
                "Текст ошибки не совпал с ожидаемым. Получен: " + responseBody);
    }
}
