package com.project.gre.enamuration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProjectStatus {

    IN_PROGRESS("In progress"), COMPLETE("Complete");

    private String label;


    ProjectStatus(String label) {
        this.label = label;
    }

    public static ProjectStatus fromName(final String name) {
        switch (name.toUpperCase()) {
            case "In progress":
                return IN_PROGRESS;
            case "Complete":
                return COMPLETE;
            default:
                return null;
        }
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
