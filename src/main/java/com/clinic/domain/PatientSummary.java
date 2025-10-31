package com.clinic.domain;

import java.util.UUID;

public final class PatientSummary extends PatientBase {
    public PatientSummary(UUID id, String lastName, String firstName, String middleName, int birthYear, String phoneOrNull, String emailOrNull) {
        super(id, lastName, firstName, middleName, birthYear, phoneOrNull, emailOrNull);
    }

    @Override
    public String toString() {
        return "PatientSummary{" + toShortString() + "}";
    }

    public static PatientSummary of(Patient p) {
        return new PatientSummary(
                p.getId(),
                p.getLastName(),
                p.getFirstName(),
                p.getMiddleName(),
                p.getBirthYear(),
                p.getPhone(),
                p.getEmail()
        );
    }
}