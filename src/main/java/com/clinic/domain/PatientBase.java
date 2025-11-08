package com.clinic.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class PatientBase {
    private final UUID id;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final int birthYear;
    private final String phone;
    private final String email;

    protected PatientBase(UUID id, String lastName, String firstName, String middleName, int birthYear, String phone, String email) {
        this.id = PatientValidators.requireValidId(id);
        this.lastName = PatientValidators.requireHumanName(lastName, "lastName");
        this.firstName = PatientValidators.requireHumanName(firstName, "firstName");
        this.middleName = PatientValidators.optionalHumanName(middleName, "middleName");
        this.birthYear = PatientValidators.requireBirthYear(birthYear);
        this.phone = PatientValidators.optionalPhone(phone);
        this.email = PatientValidators.optionalEmail(email);
    }

    public UUID getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public int getBirthYear() { return birthYear; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public String toShortString() {
        String initials = firstName.charAt(0) + "." + (middleName != null && !middleName.isBlank() ? middleName.charAt(0) + "." : "");
        String contact = email != null && !email.isBlank() ? email : (phone != null ? phone : "—");
        return String.format("%s %s (%d) | %s", lastName, initials, birthYear, contact);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientBase)) return false;
        PatientBase that = (PatientBase) o;
        return Objects.equals(id, that.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }

}