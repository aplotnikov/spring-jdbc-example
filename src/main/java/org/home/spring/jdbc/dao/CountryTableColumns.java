package org.home.spring.jdbc.dao;

import javax.annotation.Nonnull;

public enum CountryTableColumns {
    ID("id"),
    NAME("name"),
    CODE_NAME("code_name");

    private final String name;

    CountryTableColumns(String name) {
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name;
    }
}
