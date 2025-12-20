package com.clinic.repository;

import com.clinic.domain.PatientSummary;
import com.clinic.domain.*;
import java.util.List;
import java.util.stream.Collectors;

public class PatientFilterDecorator extends PatientRepositoryDecorator {
    private String lastNameFilter;

    public PatientFilterDecorator(IPatientRepository wrapped, String lastNameFilter) {
        super(wrapped);
        this.lastNameFilter = lastNameFilter;
    }

    @Override
    public List<Patient> findByFullIdentity(String ln, String fn, String mn) {
        return wrapped.findByFullIdentity(ln, fn, mn);
    }

    @Override
    public List<PatientSummary> getKNShortList(int k, int n) {
        // Получаем все данные от обернутого объекта
        // В реальном проекте для БД это лучше делать через SQL, но декоратор часто работает поверх интерфейса
        List<PatientSummary> all = wrapped.getKNShortList(Integer.MAX_VALUE, 1);

        return all.stream()
                .filter(p -> lastNameFilter == null || p.getLastName().contains(lastNameFilter))
                .skip((long) (n - 1) * k)
                .limit(k)
                .collect(Collectors.toList());
    }

    @Override
    public int getCount() {
        List<PatientSummary> all = wrapped.getKNShortList(Integer.MAX_VALUE, 1);
        return (int) all.stream()
                .filter(p -> lastNameFilter == null || p.getLastName().contains(lastNameFilter))
                .count();
    }
}