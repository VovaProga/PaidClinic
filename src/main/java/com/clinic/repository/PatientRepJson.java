package com.clinic.repository;

import com.clinic.domain.Patient;
import com.clinic.domain.PatientSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PatientRepJson {
    private String filePath;
    private List<Patient> patients = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public PatientRepJson(String filePath) {
        this.filePath = filePath;
        readFromFile();
    }

    // a. Чтение всех значений
    public void readFromFile() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Patient.class);
                this.patients = mapper.readValue(file, listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // b. Запись всех значений
    public void writeToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), patients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // c. Получить по ID
    public Patient getById(UUID id) {
        return patients.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    // d. Пагинация (k - размер страницы, n - номер страницы)
    public List<PatientSummary> getKNShortList(int k, int n) {
        return patients.stream()
                .skip((long) (n - 1) * k)
                .limit(k)
                .map(PatientSummary::of)
                .collect(Collectors.toList());
    }

    // e. Сортировка (выбрали фамилию)
    public void sortByLastName() {
        patients.sort(Comparator.comparing(Patient::getLastName));
    }

    // f. Добавить (с генерацией нового ID)
    public void add(Patient p) {
        Patient newPatient = new Patient(UUID.randomUUID(), p.getLastName(), p.getFirstName(),
                p.getMiddleName(), p.getBirthYear(), p.getPhone(), p.getEmail(), p.getAddress());
        patients.add(newPatient);
        writeToFile();
    }

    // g. Заменить
    public void replaceById(UUID id, Patient p) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) {
                patients.set(i, p);
                writeToFile();
                return;
            }
        }
    }

    // h. Удалить
    public void deleteById(UUID id) {
        patients.removeIf(p -> p.getId().equals(id));
        writeToFile();
    }

    // i. Количество
    public int getCount() { return patients.size(); }
}