package org.home.spring.jdbc.dao;

import org.home.spring.jdbc.model.Country;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Stream;

import static org.home.spring.jdbc.dao.Queries.FIND_ALL_COUNTRIES;
import static org.home.spring.jdbc.dao.Queries.FIND_COUNTRIES_WHERE_NAME_STARTS_WITH;
import static org.home.spring.jdbc.dao.Queries.FIND_COUNTRY_BY_CODE_NAME;
import static org.home.spring.jdbc.dao.Queries.FIND_COUNTRY_BY_NAME;
import static org.home.spring.jdbc.dao.Queries.INSERT_COUNTRY;
import static org.home.spring.jdbc.dao.Queries.REMOVE_COUNTRIES;
import static org.home.spring.jdbc.dao.Queries.UPDATE_COUNTRY_NAME_BY_CODE_NAME;

@Repository
public class CountryDao extends JdbcDaoSupport {
    @Inject
    private DataSource dataSource;
    @Inject
    private CountryRowMapper countryRowMapper;
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CountryDao() {
        // It is important to have the default constructor for injecting properties
    }

    @PostConstruct
    private void initialize() {
        // Workaround for injecting Data source through Java configuration
        setDataSource(dataSource);
    }

    public void loadCountries() {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        Stream.of(Countries.values())
              .forEach(
                      country -> jdbcTemplate.execute(
                              INSERT_COUNTRY.completeQuery(country.getName(), country.getCodeName())
                      )
              );
    }

    public void removeCountries() {
        getJdbcTemplate().execute(REMOVE_COUNTRIES.getQuery());
    }

    @Nonnull
    public List<Country> getCountryList() {
        return getJdbcTemplate().query(FIND_ALL_COUNTRIES.getQuery(), countryRowMapper);
    }

    @Nonnull
    public List<Country> getCountryListStartWith(@Nonnull String name) {
        // TODO need to refactor it
        return namedParameterJdbcTemplate.query(
                FIND_COUNTRIES_WHERE_NAME_STARTS_WITH.getQuery(),
                new MapSqlParameterSource("name", name + "%"),
                countryRowMapper);
    }

    public void updateCountryName(@Nonnull String codeName, @Nonnull String newCountryName) {
        getJdbcTemplate().execute(
                UPDATE_COUNTRY_NAME_BY_CODE_NAME.completeQuery(newCountryName, codeName)
        );
    }

    @Nonnull
    public Country getCountryByCodeName(@Nonnull String codeName) {
        return getJdbcTemplate()
                .query(FIND_COUNTRY_BY_CODE_NAME.completeQuery(codeName), countryRowMapper)
                .get(0);
    }

    @Nonnull
    public Country getCountryByName(@Nonnull String name) throws CountryNotFoundException {
        List<Country> countryList = getJdbcTemplate().query(
                FIND_COUNTRY_BY_NAME.completeQuery(name),
                countryRowMapper
        );

        if (countryList.isEmpty()) {
            throw new CountryNotFoundException();
        }

        return countryList.get(0);
    }
}
