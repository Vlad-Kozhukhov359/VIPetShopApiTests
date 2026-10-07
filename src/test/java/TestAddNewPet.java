import asserts.PetApiAssertions;
import base.BaseApiTest;
import config.ApiConfig;
import config.PetApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class TestAddNewPet extends BaseApiTest {

    @Step("Добавить питомца")
    public Response postPet(Pet pet) {
        return given()
                .header("Content-Type", ApiConfig.CONTENT_TYPE)
                .header("Accept", ApiConfig.ACCEPT_HEADER)
                .body(pet)
                .post(PetApiEndpoints.pet());
    }

    @ParameterizedTest(name = "Добавление питомца со статусом: {2}")
    @CsvSource({
            "200, Kiwi, available",
            "201, Buddy, pending",
            "202, Garfield, sold"
    })
    @DisplayName("Добавление нового питомца")
    public void testAddNewPet(int id, String name, String status) {
        step("Проверка добавления питомца с ID: " + id, () -> {
            Pet pet = new Pet();
            pet.setId(id);
            pet.setName(name);
            pet.setStatus(status);

            Response response = postPet(pet);
            PetApiAssertions.assertPetCreated(response, pet);
        });
    }

    @Test
    @DisplayName("Добавление питомца с невалидным статусом")
    public void testAddPetWithInvalidStatus() {
        step("Проверка добавления питомца с невалидным статусом", () -> {
            Pet pet = new Pet();
            pet.setId(203);
            pet.setName("Gosha");
            pet.setStatus("closed");

            Response response = postPet(pet);
            PetApiAssertions.assertInvalidStatus(response);
        });
    }
}
