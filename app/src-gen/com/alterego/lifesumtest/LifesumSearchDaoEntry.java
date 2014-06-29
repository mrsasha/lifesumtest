package com.alterego.lifesumtest;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table LIFESUM_SEARCH_DAO_ENTRY.
 */
public class LifesumSearchDaoEntry {

    private Long id;
    private String lifesum_response_string;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public LifesumSearchDaoEntry() {
    }

    public LifesumSearchDaoEntry(Long id) {
        this.id = id;
    }

    public LifesumSearchDaoEntry(Long id, String lifesum_response_string) {
        this.id = id;
        this.lifesum_response_string = lifesum_response_string;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLifesum_response_string() {
        return lifesum_response_string;
    }

    public void setLifesum_response_string(String lifesum_response_string) {
        this.lifesum_response_string = lifesum_response_string;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
