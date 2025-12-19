package com.clinic.repository;

import com.clinic.domain.Patient;
import com.clinic.domain.PatientSummary;
import java.util.List;
import java.util.UUID;

public abstract class PatientRepositoryDecorator implements IPatientRepository {
    protected IPatientRepository wrapped;

    public PatientRepositoryDecorator(IPatientRepository wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Patient getById(UUID id) { return wrapped.getById(id); }

    @Override
    public List<PatientSummary> getKNShortList(int k, int n) { return wrapped.getKNShortList(k, n); }

    @Override
    public void add(Patient p) { wrapped.add(p); }

    @Override
    public void replaceById(UUID id, Patient p) { wrapped.replaceById(id, p); }

    @Override
    public void deleteById(UUID id) { wrapped.deleteById(id); }

    @Override
    public int getCount() { return wrapped.getCount(); }
}