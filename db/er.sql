-- ER-модель базы данных платной поликлиники
CREATE TABLE specialties (
  id SERIAL PRIMARY KEY,
  code VARCHAR(50) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL
);
CREATE TABLE doctors (
  id SERIAL PRIMARY KEY,
  last_name VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  middle_name VARCHAR(100),
  qualification VARCHAR(100) NOT NULL,
  specialty_id INTEGER NOT NULL REFERENCES specialties(id)
);
CREATE TABLE patients (
  id UUID PRIMARY KEY,
  last_name VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  middle_name VARCHAR(100),
  birth_year SMALLINT NOT NULL,
  phone VARCHAR(32),
  email VARCHAR(255),
  address VARCHAR(500),
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT chk_birth_year CHECK (birth_year BETWEEN 1900 AND EXTRACT(YEAR FROM NOW()))
);
CREATE TABLE visits (
  id BIGSERIAL PRIMARY KEY,
  patient_id UUID NOT NULL REFERENCES patients(id),
  doctor_id INTEGER NOT NULL REFERENCES doctors(id),
  diagnosis VARCHAR(500) NOT NULL,
  visit_date DATE NOT NULL,
  cost_amount NUMERIC(12,2) NOT NULL CHECK (cost_amount >= 0)
);