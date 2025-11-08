package com.clinic.domain;

import java.util.Map;
import java.util.UUID;

public final class Patient extends PatientBase {
    private final String address;

    public Patient(UUID id, String lastName, String firstName, String middleName, int birthYear, String phone, String email, String address) {
        super(id, lastName, firstName, middleName, birthYear, phone, email);
        this.address = PatientValidators.optionalAddress(address);
    }

//    public Patient(String csvLine) {
//        this(parseCsv(csvLine));
//    }
public Patient(String csvLine) {
    Patient p = parseCsv(csvLine);

    this(
            p.getId(),
            p.getLastName(),
            p.getFirstName(),
            p.getMiddleName(),
            p.getBirthYear(),
            p.getPhone(),
            p.getEmail(),
            p.getAddress()
    );
}


    private static Patient parseCsv(String line) {
        String[] a = line.split(";", -1);
        if (a.length != 8)
            throw new IllegalArgumentException("CSV должен содержать 8 полей");
        return new Patient(
            UUID.fromString(a[0].trim()),
            a[1].trim(),
            a[2].trim(),
            a[3].isBlank() ? null : a[3].trim(),
            Integer.parseInt(a[4].trim()),
            a[5].isBlank() ? null : a[5].trim(),
            a[6].isBlank() ? null : a[6].trim(),
            a[7].isBlank() ? null : a[7].trim()
        );
    }

    public Patient(Map<String, Object> json) {
        this(
            UUID.fromString(String.valueOf(json.get("id"))),
            String.valueOf(json.get("lastName")),
            String.valueOf(json.get("firstName")),
            json.get("middleName") == null ? null : String.valueOf(json.get("middleName")),
            Integer.parseInt(String.valueOf(json.get("birthYear"))),
            json.get("phone") == null ? null : String.valueOf(json.get("phone")),
            json.get("email") == null ? null : String.valueOf(json.get("email")),
            json.get("address") == null ? null : String.valueOf(json.get("address"))
        );
    }

    public static Patient fromJson(String json) {
        Map<String, Object> map = PatientValidators.simpleJsonToMap(json);
        return new Patient(map);
    }

    public String getAddress() { return address; }


    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", lastName='" + getLastName() + "'" +
                ", firstName='" + getFirstName() + "'" +
                ", middleName='" + getMiddleName() + "'" +
                ", birthYear=" + getBirthYear() +
                ", phone='" + getPhone() + "'" +
                ", email='" + getEmail() + "'" +
                ", address='" + address + "'" +
                '}';
    }


    public static PatientBuilder builder() { return new PatientBuilder(); }
}