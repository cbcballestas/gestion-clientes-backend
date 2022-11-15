package com.cballestas.springboot.backend.apirest.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    // Utils
    public static final String PATTERN_EMAIL = "[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}";

    // Pagination
    public static final String PAGE = "0";
    public static final String SIZE = "4";
    public static final String SORT_ORDER = "id";
    public static final String SORT_DIR = "ASC";

    // Mensajes LOGGER
    public static final String ERROR_MESSAGE_NOT_FOUND_LOGGER = "El cliente con ID {} NO existe";

    // Mensajes error API
    public static final String ERROR_MESSAGE_NOT_FOUND = "El cliente con ID %s NO existe";
    public static final String ERROR_MESSAGE_EXISTING_EMAIL = "El email ingresado YA se encuentra registrado";

}
