package com.clinic.repository;

import com.clinic.domain.Patient;
import com.clinic.domain.PatientSummary;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PatientRepDB {
    private final Connection connection;

    public PatientRepDB() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public int getCount() {
        String sql = "SELECT COUNT(*) FROM patients";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public Patient getById(UUID id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRowToPatient(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<PatientSummary> getKNShortList(int k, int n) {
        List<PatientSummary> results = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY last_name LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, k);
            ps.setInt(2, (n - 1) * k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(PatientSummary.of(mapRowToPatient(rs)));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    public void add(Patient p) {
        String sql = "INSERT INTO patients (id, last_name, first_name, middle_name, birth_year, phone, email, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getFirstName());
            ps.setString(4, p.getMiddleName());
            ps.setInt(5, p.getBirthYear());
            ps.setString(6, p.getPhone());
            ps.setString(7, p.getEmail());
            ps.setString(8, p.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void replaceById(UUID id, Patient p) {
        String sql = "UPDATE patients SET last_name=?, first_name=?, middle_name=?, birth_year=?, phone=?, email=?, address=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getLastName());
            ps.setString(2, p.getFirstName());
            ps.setString(3, p.getMiddleName());
            ps.setInt(4, p.getBirthYear());
            ps.setString(5, p.getPhone());
            ps.setString(6, p.getEmail());
            ps.setString(7, p.getAddress());
            ps.setObject(8, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Patient mapRowToPatient(ResultSet rs) throws SQLException {
        return new Patient(
                (UUID) rs.getObject("id"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getInt("birth_year"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address")
        );
    }
}