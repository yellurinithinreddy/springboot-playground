CREATE TABLE department(
    id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255),
    CONSTRAINT pk_department PRIMARY KEY (id)
);

ALTER TABLE employee ADD COLUMN department_id BIGINT;

ALTER TABLE employee ADD CONSTRAINT fk_employee_on_department FOREIGN KEY (department_id) REFERENCES department (id);

