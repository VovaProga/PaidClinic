//package com.clinic.domain;
//
//import java.time.Year;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public final class PatientValidators {
//    private PatientValidators() {}
//
//    public static UUID requireValidId(UUID id) {
//        if (id == null) throw new IllegalArgumentException("id is required");
//        return id;
//    }
//
//    public static String requireHumanName(String v, String field) {
//        if (v == null || v.isBlank()) throw new IllegalArgumentException(field + " is required");
//        if (!v.matches("^[A-Za-zА-Яа-яЁё\-']{1,100}$"))
//            throw new IllegalArgumentException(field + " must be alphabetic (1..100)");
//        return v;
//    }
//
//    public static String optionalHumanName(String v, String field) {
//        if (v == null || v.isBlank()) return null;
//        return requireHumanName(v, field);
//    }
//
//    public static int requireBirthYear(int y) {
//        int now = Year.now().getValue();
//        if (y < 1900 || y > now) throw new IllegalArgumentException("birthYear must be 1900.." + now);
//        return y;
//    }
//
//    public static String optionalPhone(String v) {
//        if (v == null || v.isBlank()) return null;
//        if (!v.matches("^[+0-9][0-9\-()\s]{5,31}$"))
//            throw new IllegalArgumentException("phone invalid");
//        return v;
//    }
//
//    public static String optionalEmail(String v) {
//        if (v == null || v.isBlank()) return null;
//        if (!v.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"))
//            throw new IllegalArgumentException("email invalid");
//        return v;
//    }
//
//    public static String optionalAddress(String v) {
//        if (v == null || v.isBlank()) return null;
//        if (v.length() > 500) throw new IllegalArgumentException("address too long");
//        return v;
//    }
//
//    public static Map<String, Object> simpleJsonToMap(String json) {
//        if (json == null) throw new IllegalArgumentException("json is null");
//        String s = json.trim();
//        if (!s.startsWith("{") || !s.endsWith("}"))
//            throw new IllegalArgumentException("expected JSON object");
//        s = s.substring(1, s.length() - 1).trim();
//        Map<String, Object> map = new HashMap<>();
//        if (s.isEmpty()) return map;
//        String[] pairs = s.split("\s*,\s*");
//        for (String pair : pairs) {
//            String[] kv = pair.split(":", 2);
//            if (kv.length != 2) throw new IllegalArgumentException("invalid JSON pair: " + pair);
//            String key = stripQuotes(kv[0].trim());
//            String valRaw = kv[1].trim();
//            Object val;
//            if (valRaw.equals("null")) {
//                val = null;
//            } else if (valRaw.startsWith(""") && valRaw.endsWith(""")) {
//                val = stripQuotes(valRaw);
//            } else if (valRaw.matches("^-?\d+$")) {
//                val = Integer.parseInt(valRaw);
//            } else {
//                val = stripQuotes(valRaw);
//            }
//            map.put(key, val);
//        }
//        return map;
//    }
//
//    private static String stripQuotes(String s) {
//        String t = s.trim();
//        if (t.startsWith(""") && t.endsWith(""")) {
//            return t.substring(1, t.length() - 1);
//        }
//        return t;
//    }
//}


package com.clinic.domain;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PatientValidators {
    private PatientValidators() {}

    public static UUID requireValidId(UUID id) {
        if (id == null) throw new IllegalArgumentException("id is required");
        return id;
    }

    public static String requireHumanName(String v, String field) {
        if (v == null || v.isBlank())
            throw new IllegalArgumentException(field + " is required");

        if (!v.matches("^[A-Za-zА-Яа-яЁё\\-']{1,100}$"))
            throw new IllegalArgumentException(field + " must be alphabetic (1..100)");
        return v;
    }

    public static String optionalHumanName(String v, String field) {
        if (v == null || v.isBlank()) return null;
        return requireHumanName(v, field);
    }

    public static int requireBirthYear(int y) {
        int now = Year.now().getValue();
        if (y < 1900 || y > now) throw new IllegalArgumentException("birthYear must be 1900.." + now);
        return y;
    }

    public static String optionalPhone(String v) {
        if (v == null || v.isBlank()) return null;
        // + или цифра, далее цифры/скобки/дефисы/пробелы 5..31
        if (!v.matches("^[+0-9][0-9\\-()\\s]{5,31}$"))
            throw new IllegalArgumentException("phone invalid");
        return v;
    }

    public static String optionalEmail(String v) {
        if (v == null || v.isBlank()) return null;
        if (!v.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new IllegalArgumentException("email invalid");
        return v;
    }

    public static String optionalAddress(String v) {
        if (v == null || v.isBlank()) return null;
        if (v.length() > 500) throw new IllegalArgumentException("address too long");
        return v;
    }

    public static Map<String, Object> simpleJsonToMap(String json) {
        if (json == null) throw new IllegalArgumentException("json is null");
        String s = json.trim();
        if (!s.startsWith("{") || !s.endsWith("}"))
            throw new IllegalArgumentException("expected JSON object");
        s = s.substring(1, s.length() - 1).trim();

        Map<String, Object> map = new HashMap<>();
        if (s.isEmpty()) return map;


        String[] pairs = s.split("\\s*,\\s*");
        for (String pair : pairs) {
            String[] kv = pair.split(":", 2);
            if (kv.length != 2) throw new IllegalArgumentException("invalid JSON pair: " + pair);

            String key = stripQuotes(kv[0].trim());
            String valRaw = kv[1].trim();
            Object val;

            if (valRaw.equals("null")) {
                val = null;
            } else if (valRaw.startsWith("\"") && valRaw.endsWith("\"")) {
                val = stripQuotes(valRaw);
            } else if (valRaw.matches("^-?\\d+$")) {
                val = Integer.parseInt(valRaw);
            } else {
                val = stripQuotes(valRaw);
            }
            map.put(key, val);
        }
        return map;
    }

    private static String stripQuotes(String s) {
        String t = s.trim();
        if (t.startsWith("\"") && t.endsWith("\"") && t.length() >= 2) {
            return t.substring(1, t.length() - 1);
        }
        return t;
    }
}
