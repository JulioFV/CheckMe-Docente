package com.itsoeh.checkmedocente.utils;

import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dia_Fecha {
    public static String aFechaLarga(String fechaYMD) {
        if (fechaYMD == null || fechaYMD.trim().isEmpty()) return "";
        final boolean usaSlash = fechaYMD.contains("/");
        final String pattern = usaSlash ? "yyyy/MM/dd" : "yyyy-MM-dd";
        final Locale esMX = new Locale("es", "MX");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                DateTimeFormatter inFmt = DateTimeFormatter.ofPattern(pattern);
                LocalDate d = LocalDate.parse(fechaYMD, inFmt);

                String dia = capitalizar(d.getDayOfWeek().getDisplayName(TextStyle.FULL, esMX)); // Lunes
                String mes = capitalizar(d.getMonth().getDisplayName(TextStyle.FULL, esMX));     // Septiembre
                int diaNum = d.getDayOfMonth();
                int anio = d.getYear();

                return String.format(esMX, "%s, %d %s %d", dia, diaNum, mes, anio);
            } catch (DateTimeParseException e) {
                return "";
            }
        }

        // Fallback: APIs < 26
        try {
            SimpleDateFormat in = new SimpleDateFormat(pattern, Locale.US);
            Date date = in.parse(fechaYMD);
            if (date == null) return "";

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String dia = new SimpleDateFormat("EEEE", esMX).format(date);   // lunes
            String mes = new SimpleDateFormat("MMMM", esMX).format(date);   // septiembre
            int diaNum = cal.get(Calendar.DAY_OF_MONTH);
            int anio = cal.get(Calendar.YEAR);

            return capitalizar(dia) + ", " + diaNum + " " + capitalizar(mes) + " " + anio;
        } catch (ParseException e) {
            return "";
        }
    }

    /** Capitaliza la primera letra respetando tildes (p. ej., "miércoles" -> "Miércoles"). */
    private static String capitalizar(String s) {
        if (s == null || s.isEmpty()) return "";
        return s.substring(0, 1).toUpperCase(new Locale("es", "MX")) + s.substring(1);
    }
}
