package model;

import java.util.UUID;

public class Employee {
    
    private UUID id_employee;
    private String name_employee;
    private boolean isAvailable;
    private String rank;


    public Employee() {
        id_employee= UUID.randomUUID();
        isAvailable=true;
    }

    public UUID getId_employee() {
        return id_employee;
    }

    public void setId_employee(UUID id_employee) {
        this.id_employee = id_employee;
    }

    public String getName_employee() {
        return name_employee;
    }

    public void setName_employee(String name_employee) {
        this.name_employee = name_employee;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}

