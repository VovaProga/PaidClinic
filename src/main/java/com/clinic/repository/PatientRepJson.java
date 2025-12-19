package com.clinic.repository;

import com.clinic.domain.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PatientRepJson extends PatientRepFileBase {
    private final String filePath;
    private final ObjectMapper mapper = new ObjectMapper();

    public PatientRepJson(String filePath) {
        this.filePath = filePath;
        readFromFile();
    }

    @Override
    public void readFromFile() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                CollectionType listType = mapper.getTypeFactory()
                        .constructCollectionType(ArrayList.class, Patient.class);
                this.patients = mapper.readValue(file, listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), patients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}