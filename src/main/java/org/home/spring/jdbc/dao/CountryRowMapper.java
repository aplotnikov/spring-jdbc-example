package org.home.spring.jdbc.dao;

import org.home.spring.jdbc.model.Country;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.home.spring.jdbc.dao.CountryTableColumns.CODE_NAME;
import static org.home.spring.jdbc.dao.CountryTableColumns.ID;
import static org.home.spring.jdbc.dao.CountryTableColumns.NAME;
import static org.home.spring.jdbc.model.Country.Builder.aCountry;

@Component
public class CountryRowMapper implements RowMapper<Country> {
    public Country mapRow(@Nonnull ResultSet resultSet, int i) throws SQLException {
        return aCountry()
                .withId(
                        resultSet.getInt(ID.getName())
                )
                .withName(
                        resultSet.getString(NAME.getName())
                )
                .withCodeName(
                        resultSet.getString(CODE_NAME.getName())
                )
                .create();
    }
}
