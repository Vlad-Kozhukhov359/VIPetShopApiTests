package config;

public class PetApiEndpoints {
    public static String petById(int petId) {
        return ApiConfig.BASE_URL + "/pet/" + petId;
    }
}
