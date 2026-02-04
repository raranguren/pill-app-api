package com.pillapp.api.domain.parents;

import javax.persistence.Entity;

@Entity
public abstract class DrugAction extends CareAction {

    public String doseNote;

}
