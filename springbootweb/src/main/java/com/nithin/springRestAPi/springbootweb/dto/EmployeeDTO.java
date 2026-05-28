package com.nithin.springRestAPi.springbootweb.dto;

import java.time.LocalDate;

public class EmployeeDTO {

    private Long id;

    private String name;

    private String email;

    private Integer age;

    private LocalDate joiningDate;

    private Boolean active;

    public EmployeeDTO(){

    }

    public EmployeeDTO(Long id, String name, String email, int age, LocalDate joiningDate, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.joiningDate = joiningDate;
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}
