package com.clinic.repository;

import com.clinic.domain.Patient;
import com.clinic.domain.PatientSummary;
import java.util.*;
import java.util.stream.Collectors;

public abstract class PatientRepFileBase {
    // Общий список данных в памяти
    protected List<Patient> patients = new ArrayList<>();

    // Эти методы каждый формат (JSON/YAML) реализует по-своему
    public abstract void readFromFile();
    public abstract void writeToFile();

    // c. Получить объект по ID
    public Patient getById(UUID id) {
        return patients.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // d. Получить список k по счету n объектов класса short
    public List<PatientSummary> getKNShortList(int k, int n) {
        return patients.stream()
                .skip((long) (n - 1) * k)
                .limit(k)
                .map(PatientSummary::of)
                .collect(Collectors.toList());
    }

    // e. Сортировать элементы по выбранному полю (Фамилия)
    public void sortByLastName() {
        patients.sort(Comparator.comparing(Patient::getLastName));
    }

    // f. Добавить объект в список (с формированием нового ID)
    public void add(Patient p) {
        Patient newPatient = new Patient(
                UUID.randomUUID(),
                p.getLastName(),
                p.getFirstName(),
                p.getMiddleName(),
                p.getBirthYear(),
                p.getPhone(),
                p.getEmail(),
                p.getAddress()
        );
        patients.add(newPatient);
        writeToFile();
    }

    // g. Заменить элемент списка по ID
    public void replaceById(UUID id, Patient p) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) {
                patients.set(i, p);
                writeToFile();
                return;
            }
        }
    }

    // h. Удалить элемент списка по ID
    public void deleteById(UUID id) {
        patients.removeIf(p -> p.getId().equals(id));
        writeToFile();
    }

    // i. Получить количество элементов
    public int getCount() {
        return patients.size();
    }
}