package com.site.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by zhangjie on 2014/12/8.
 */
public class PersonDTO {

    private Long id;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String firstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
