
package com.simplecrm.contacts.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.Map;

@Entity
@Table(name = "Contact")
@Access(AccessType.FIELD)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Version
    private Long version;

    public Contact() {
    }

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}