package org.home.spring.jdbc.dao;

import javax.annotation.Nonnull;

public enum Queries {
    INSERT_COUNTRY(
            "insert into country (name, code_name) values ('%s', '%s')", true
    ),
    REMOVE_COUNTRIES(
            "delete from country", false
    ),
    FIND_ALL_COUNTRIES(
            "select * from country", false
    ),
    FIND_COUNTRIES_WHERE_NAME_STARTS_WITH(
            "select * from country where name like :name", false
    ),
    UPDATE_COUNTRY_NAME_BY_CODE_NAME(
            "update country set name='%s' where code_name='%s'", true
    ),
    FIND_COUNTRY_BY_CODE_NAME(
            "select * from country where code_name = '%s'", true
    ),
    FIND_COUNTRY_BY_NAME(
            "select * from country where name = '%s'", true
    );

    private final String query;
    private final boolean isRequiredCompletion;

    Queries(@Nonnull String query, boolean isRequiredCompletion) {
        this.query = query;
        this.isRequiredCompletion = isRequiredCompletion;
    }

    @Nonnull
    public String getQuery() {
        if (isRequiredCompletion) {
            throw new UnsupportedOperationException("This query requires completion.");
        }

        return query;
    }

    @Nonnull
    public String completeQuery(String... parameters) {
        if (!isRequiredCompletion) {
            throw new UnsupportedOperationException("This query doesn't require completion.");
        }

        return String.format(query, parameters);
    }
}
