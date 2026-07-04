CREATE TABLE employee(
    id BIGINT AUTO_INCREMENT NOT NULL,
    email VARCHAR(255) NULL,
    full_name VARCHAR(255) NULL,
    salary BIGINT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)

);

ALTER TABLE employee ADD CONSTRAINT uc_employee_email UNIQUE (email);