package com.clinic.repository;

import com.clinic.domain.Patient;
import com.clinic.domain.PatientSummary;

import java.util.List;
import java.util.UUID;

public interface IPatientRepository {
    Patient getById(UUID id);

    List<PatientSummary> getKNShortList(int k, int n);

    List<Patient> findByFullIdentity(String lastName, String firstName, String middleName);

    void add(Patient p);

    void replaceById(UUID id, Patient p);

    void deleteById(UUID id);

    int getCount();
}