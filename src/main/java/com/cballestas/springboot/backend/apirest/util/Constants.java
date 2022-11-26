package com.cballestas.springboot.backend.apirest.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    // Utils
    public static final String PATTERN_EMAIL = "[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}";
    public static final String UPLOAD_DIR = "uploads";
    public static final String STATIC_FILES_DIR = "src/main/resources/static/images";
    public static final String NO_PROFILE_PHOTO_FILE = "no-usuario.png";

    // Pagination
    public static final String PAGE = "0";
    public static final String SIZE = "4";
    public static final String SORT_ORDER = "id";
    public static final String SORT_DIR = "ASC";

    // Mensajes flujo éxitoso
    public static final String MESSAGE_UPDATED_PROFILE_PHOTO = "Se ha subido la imágen de forma éxitosa";

    // Mensajes LOGGER
    public static final String ERROR_MESSAGE_NOT_FOUND_LOGGER = "El cliente con ID {} NO existe";
    public static final String MESSAGE_INIT_FILE_UPLOAD = "Se empieza carga de archivo";
    public static final String MESSAGE_INICIO_BORRADO_FOTO_PERFIL = "Se inicia borrado foto de perfil";
    public static final String MESSAGE_FINALIZA_BORRADO_FOTO_PERFIL = "Se finaliza borrado foto de perfil";
    public static final String MESSAGE_FINALIZE_FILE_UPLOAD = "Se finaliza carga de archivo";

    // Mensajes ERROR foto perfil
    public static final String ERROR_MESSAGE_UPLOAD_FILE = "Error al subir archivo";
    public static final String ERROR_MESSAGE_UPLOAD_FILE_DETAILED = ERROR_MESSAGE_UPLOAD_FILE + ", Error:[{}], Excepcion:[{}]";
    public static final String ERROR_MESSAGE_DELETE_FILE = "Error al eliminar archivo";
    public static final String ERROR_MESSAGE_DELETE_FILE_DETAILED = ERROR_MESSAGE_UPLOAD_FILE + ", Error:[{}], Excepcion:[{}]";
    public static final String ERROR_CARGA_IMAGEN = "Error al cargar imagen";
    public static final String ERROR_CARGA_IMAGEN_DETAILED = ERROR_CARGA_IMAGEN + ", Imágen: [{}]";

    // Mensajes error API
    public static final String ERROR_MESSAGE_NOT_FOUND = "El cliente con ID %s NO existe";
    public static final String ERROR_MESSAGE_EXISTING_EMAIL = "El email ingresado YA se encuentra registrado";


}
