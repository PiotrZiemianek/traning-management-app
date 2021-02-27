package pl.sda.training.management.app.utils;

import org.springframework.hateoas.MediaTypes;

public class Constants {
    //validation messages
    public static final String LOGIN_AT_LEAST_3_CHAR = "Login musi posiadać conajmniej 3 znaki.";
    public static final String LOGIN_NOT_LONGER_THAN_16_CHAR = "Login nie może być dłuższy niż 16 znaków.";
    public static final String PASSWORD_AT_LEAST_6_CHAR = "Hasło jest za którkie. Minimum 6 znaków.";
    public static final String NAME_TOO_SHORT = "Imię jest za krótkie.";
    public static final String LASTNAME_TOO_SHORT = "Nazwisko jest za krótkie.";
    public static final String EDITION_CODE_INVALID_FORMAT = "Nieprawidłowy format. Przykład poprawnego: \"javaKRK22\".";
    public static final String AT_LEAST_3_CHAR = "Conajmniej 3 znaki.";
    public static final String INVALID_FORMAT = "Nieprawidłowy format.";
    public static final String NON_UNIQUE_EDITION_CODE = "Istnieje już inna edycja z takim kodem.";

    //api
    public static final String API_PRODUCES = MediaTypes.HAL_JSON_VALUE;
    public static final String API_URL = "/api";
    public static final int DEFAULT_API_PAGE_SIZE = 10;
}
