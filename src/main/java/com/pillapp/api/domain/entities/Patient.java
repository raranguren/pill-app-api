package com.pillapp.api.domain.entities;

import com.pillapp.api.domain.parents.FeedItem;
import com.pillapp.api.dto.responses.data.PatientDTO;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends FeedItem {

    @ManyToMany
    public List<User> users;

    public String fullName;
    public String notes;
    public Integer kg;

    public Patient(){}
    public Patient(String fullName, String notes, Integer kg, List<User> users) {
        this.fullName = fullName;
        this.notes = notes;
        this.kg = kg;
        this.users = users;
    }

    @Override
    public String getType() {
        return "patient";
    }

    @Override
    public PatientDTO toDTO() {
        return new PatientDTO(this);
    }
}
