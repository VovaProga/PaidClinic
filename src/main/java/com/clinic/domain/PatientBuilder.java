package com.clinic.domain;

import java.util.UUID;

public final class PatientBuilder {
    private UUID id = UUID.randomUUID();
    private String lastName;
    private String firstName;
    private String middleName;
    private int birthYear;
    private String phone;
    private String email;
    private String address;

    public PatientBuilder id(UUID id) { this.id = id; return this; }
    public PatientBuilder lastName(String v) { this.lastName = v; return this; }
    public PatientBuilder firstName(String v) { this.firstName = v; return this; }
    public PatientBuilder middleName(String v) { this.middleName = v; return this; }
    public PatientBuilder birthYear(int v) { this.birthYear = v; return this; }
    public PatientBuilder phone(String v) { this.phone = v; return this; }
    public PatientBuilder email(String v) { this.email = v; return this; }
    public PatientBuilder address(String v) { this.address = v; return this; }

    public Patient build() {
        return new Patient(id, lastName, firstName, middleName, birthYear, phone, email, address);
    }
}