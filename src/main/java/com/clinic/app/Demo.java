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
import com.clinic.repository.*;
import java.util.UUID;

public class Demo {
    public static void main(String[] args) {
        try {
            // 1. Проверка работы с БД через Адаптер (Пункт 6)
            System.out.println("=== Тест БД (Adapter) ===");
            IPatientRepository dbRepo = new PatientRepAdapter();

            Patient newPatient = Patient.builder()
                    .lastName("Тестовый")
                    .firstName("Пациент")
                    .birthYear(2000)
                    .build();



            dbRepo.add(newPatient);
            // Должно улететь в Postgres
            System.out.println("Запись в БД успешна. Всего записей: " + dbRepo.getCount());

            // 2. Проверка Фильтрации (Декоратор - Пункты 7-8)
            System.out.println("\n=== Тест Декоратора ===");
            IPatientRepository filtered = new PatientFilterDecorator(dbRepo, "Тестовый");
            System.out.println("Найдено по фильтру 'Тестовый': " + filtered.getKNShortList(10, 1).size());

            // 3. Проверка работы с файлами (JSON)
            System.out.println("\n=== Тест JSON ===");
            // Убедись, что папка 'data' существует в корне проекта
            PatientRepYaml jsonRepo = new PatientRepYaml("data/patients.yaml");
            jsonRepo.add(newPatient);
            System.out.println("Запись в JSON успешна. Файл обновлен.");

        } catch (Exception e) {
            System.err.println("ОШИБКА: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
//        Patient p1 = Patient.builder()
//                .id(UUID.randomUUID())
//                .lastName("Иванов")
//                .firstName("Пётр")
//                .middleName("Сергеевич")
//                .birthYear(1990)
//                .phone("+79991234567")
//                .email("p.ivanov@example.com")
//                .address("Москва, ул. Примерная, д.1")
//                .build();
//
//        System.out.println("FULL:  " + p1);
//        System.out.println("SHORT: " + PatientSummary.of(p1));
//
//
//        String csv = UUID.randomUUID()
//                + ";Петров;Игорь;;1985;;petrov@example.com;Санкт-Петербург";
//        Patient p2 = Patient.fromCsv(csv);
//        System.out.println("CSV:   " + p2);
//
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", UUID.randomUUID().toString());
//        map.put("lastName", "Сидорова");
//        map.put("firstName", "Анна");
//        map.put("birthYear", 1995);
//        map.put("email", "anna@example.com");
//        Patient p3 = new Patient(map);
//        System.out.println("MAP:   " + p3);
//
//
//        String json = "{ \"id\": \"" + UUID.randomUUID() + "\", " +
//                "\"lastName\": \"Кузнецов\", " +
//                "\"firstName\": \"Кузьма\", " +
//                "\"middleName\": \"\", " +
//                "\"birthYear\": \"1999\", " +
//                "\"phone\": \"+79995554433\", " +
//                "\"email\": \"kuz@example.com\", " +
//                "\"address\": \"Екатеринбург\" }";
//        Patient p4 = Patient.fromJson(json);
//        System.out.println("JSON:  " + p4);
//    }