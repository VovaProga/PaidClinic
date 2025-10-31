//package com.clinic.app;
//
//import com.clinic.domain.*;
//
//import java.util.UUID;
//
//public class Demo {
//    public static void main(String[] args) {
//        Patient p1 = Patient.builder()
//                .lastName("Иванов")
//                .firstName("Пётр")
//                .middleName("Сергеевич")
//                .birthYear(1990)
//                .phone("+7 999 123-45-67")
//                .email("p.ivanov@example.com")
//                .address("Москва, ул. Примерная, д.1")
//                .build();
//
//        Patient p2 = new Patient(UUID.randomUUID() + ";Петров;Игорь;;1985;;petrov@example.com;Санкт-Петербург");
//        Patient p3 = Patient.fromJson("{"id":"" + UUID.randomUUID() + "","lastName":"Сидорова","firstName":"Анна","birthYear":1995,"email":"anna@example.com"}");
//
//        System.out.println(p1);
//        System.out.println(p1.toShortString());
//
//        PatientSummary s = PatientSummary.of(p1);
//        System.out.println(s);
//    }
//}


package com.clinic.app;

import com.clinic.domain.Patient;
import com.clinic.domain.PatientSummary;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Demo {
    public static void main(String[] args) {
        // 1) Билдер: создаём пациента с корректными полями
        Patient p1 = Patient.builder()
                .id(UUID.randomUUID())
                .lastName("Иванов")
                .firstName("Пётр")
                .middleName("Сергеевич")
                .birthYear(1990)
                .phone("+79991234567")
                .email("p.ivanov@example.com")
                .address("Москва, ул. Примерная, д.1")
                .build();

        System.out.println("FULL:  " + p1);
        System.out.println("SHORT: " + PatientSummary.of(p1));

        // 2) CSV: id;last;first;middle;birthYear;phone;email;address
        String csv = UUID.randomUUID()
                + ";Петров;Игорь;;1985;;petrov@example.com;Санкт-Петербург";
        Patient p2 = new Patient(csv);
        System.out.println("CSV:   " + p2);

        // 3) Map JSON: конструктор от Map<String,Object>
        Map<String, Object> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("lastName", "Сидорова");
        map.put("firstName", "Анна");
        map.put("birthYear", 1995);
        map.put("email", "anna@example.com");
        Patient p3 = new Patient(map);
        System.out.println("MAP:   " + p3);

        // 4) JSON string: парсится через PatientValidators.simpleJsonToMap(...)
        String json = "{ \"id\": \"" + UUID.randomUUID() + "\", " +
                "\"lastName\": \"Кузнецов\", " +
                "\"firstName\": \"Кузьма\", " +
                "\"middleName\": \"\", " +
                "\"birthYear\": \"1999\", " +
                "\"phone\": \"+79995554433\", " +
                "\"email\": \"kuz@example.com\", " +
                "\"address\": \"Екатеринбург\" }";
        Patient p4 = Patient.fromJson(json);
        System.out.println("JSON:  " + p4);
    }
}
