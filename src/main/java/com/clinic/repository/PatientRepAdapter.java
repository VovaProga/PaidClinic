package com.clinic.repository;

import com.clinic.domain.Patient;
import com.clinic.domain.PatientSummary;

import java.util.List;
import java.util.UUID;

public class PatientRepAdapter implements IPatientRepository {
    private final PatientRepDB db;

    public PatientRepAdapter() {
        this.db = new PatientRepDB();
    }

    @Override
    public Patient getById(UUID id) {
        return db.getById(id);
    }

    @Override
    public List<Patient> findByFullIdentity(String ln, String fn, String mn) {
        return db.findByFullIdentity(ln, fn, mn);
    }

    @Override
    public List<PatientSummary> getKNShortList(int k, int n) {
        return db.getKNShortList(k, n);
    }

    @Override
    public void add(Patient p) {
        db.add(p);
    }

    @Override
    public void replaceById(UUID id, Patient p) {
        db.replaceById(id, p);
    }

    @Override
    public void deleteById(UUID id) {
        db.deleteById(id);
    }

    @Override
    public int getCount() {
        return db.getCount();
    }
}