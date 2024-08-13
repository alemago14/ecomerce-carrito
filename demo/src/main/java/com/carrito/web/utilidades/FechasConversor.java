package com.carrito.web.utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

@Component
public class FechasConversor {
    // Define el formato de la fecha y hora
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate stringToLocalDate(String dateString) {
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El formato de la fecha no es válido. Debe ser 'dd/MM/yyyy'", e);
        }
    }

    public static String localDateToString(LocalDate date) {
        return date.format(formatter);
    }
}
