package asserts;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Pet;

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

    @Step("Проверка создания питомца")
    public static void assertPetCreated(Response response, Pet expectedPet) {
        String responseBody = response.getBody().asString();

        assertEquals(200, response.getStatusCode(),
                "Код ответа не совпал с ожидаемым. Ответ: " + responseBody);

        Pet createdPet = response.as(Pet.class);
        assertEquals(expectedPet.getId(), createdPet.getId(), "id питомца не совпадает с ожидаемым");
        assertEquals(expectedPet.getName(), createdPet.getName(), "имя питомца не совпадает с ожидаемым");
        assertEquals(expectedPet.getStatus(), createdPet.getStatus(), "статус питомца не совпадает с ожидаемым");
    }

    @Step("Проверка невалидного статуса")
    public static void assertInvalidStatus(Response response) {
        String responseBody = response.getBody().asString();

        assertEquals(400, response.getStatusCode(),
                "Код ответа не совпал с ожидаемым. Ответ: " + responseBody);

        assertEquals("Invalid pet status. Valid values: [available, pending, sold]", responseBody,
                "Сообщение об ошибке не совпало с ожидаемым. Получен: " + responseBody);
    }
}
