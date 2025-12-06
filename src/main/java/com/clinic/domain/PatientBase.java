package com.clinic.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class PatientBase {

    private final UUID id;


    private String lastName;
    private String firstName;
    private String middleName;
    private int birthYear;
    private String phone;
    private String email;

    protected PatientBase(UUID id, String lastName, String firstName, String middleName, int birthYear, String phone, String email) {
        this.id = PatientValidators.requireValidId(id);


        setLastName(lastName);
        setFirstName(firstName);
        setMiddleName(middleName);
        setBirthYear(birthYear);
        setPhone(phone);
        setEmail(email);
    }


    public UUID getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public int getBirthYear() { return birthYear; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }



    public void setLastName(String lastName) {
        this.lastName = PatientValidators.requireHumanName(lastName, "lastName");
    }

    public void setFirstName(String firstName) {
        this.firstName = PatientValidators.requireHumanName(firstName, "firstName");
    }

    public void setMiddleName(String middleName) {
        this.middleName = PatientValidators.optionalHumanName(middleName, "middleName");
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = PatientValidators.requireBirthYear(birthYear);
    }

    public void setPhone(String phone) {
        this.phone = PatientValidators.optionalPhone(phone);
    }

    public void setEmail(String email) {
        this.email = PatientValidators.optionalEmail(email);
    }



    public String toShortString() {
        String initials = firstName.charAt(0) + "." + (middleName != null && !middleName.isBlank() ? middleName.charAt(0) + "." : "");
        String contact;
        if (email != null && !email.isBlank()) {
            contact = email;
        } else {
            if (phone != null) {
                contact = phone;
            } else {
                contact = "—";
            }
        }

        return String.format("%s %s (%d) | %s", lastName, initials, birthYear, contact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientBase)) return false;
        PatientBase that = (PatientBase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}