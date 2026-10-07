import asserts.PetApiAssertions;
import base.BaseApiTest;
import config.ApiConfig;
import config.PetApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class TestUpdateNonexistentPet extends BaseApiTest {

    @Step("Обновить питомца с ID: {petId}")
    public Response putPet(Pet pet) {
        return given()
                .header("Content-Type", ApiConfig.CONTENT_TYPE)
                .header("Accept", ApiConfig.ACCEPT_HEADER)
                .body(pet)
                .put(PetApiEndpoints.pet());
    }

    @Test
    @DisplayName("Обновление несуществующего питомца")
    public void testUpdateNonexistentPet() {
        step("Проверка обновления несуществующего питомца", () -> {
            Pet pet = new Pet();
            pet.setId(9999);
            pet.setName("Non-existent Pet");
            pet.setStatus("available");
            Response response = putPet(pet);
            PetApiAssertions.assertPetUpdated(response);
        });
    }
}