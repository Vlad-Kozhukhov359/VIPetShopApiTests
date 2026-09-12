import asserts.PetApiAssertions;
import base.BaseApiTest;
import config.PetApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;

public class TestDeleteNonexistentPet extends BaseApiTest {

    @Step("Удалить питомца с ID: {petId}")
    public Response deletePet(int petId) {
        return when()
                .delete(PetApiEndpoints.petById(petId));
    }

    @Test
    @DisplayName("Удаление несуществующего питомца")
    public void testDeleteNonexistentPet() {
        Response response = deletePet(9999);
        PetApiAssertions.assertPetDeleted(response);
    }
}