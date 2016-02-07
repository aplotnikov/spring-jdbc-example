package org.home.spring.jdbc.dao;

import org.home.spring.jdbc.context.ApplicationContext;
import org.home.spring.jdbc.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.home.spring.jdbc.dao.Countries.AUSTRALIA;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles("dev")
public class CountryDaoTest {
    @Inject
    private CountryDao countryDao;

    @Test
    @DirtiesContext
    public void shouldAllCountriesBeInsertedInDB() {
        List<Country> expectedCountries = Stream.of(Countries.values())
                                                .map(Countries::toCountry)
                                                .collect(Collectors.toList());
        assertThat(countryDao.getCountryList()).containsOnlyElementsOf(expectedCountries);
    }

    @Test
    @DirtiesContext
    public void shouldCountriesWhichNameStartsWithABeReturned() {
        assertThat(countryDao.getCountryListStartWith("A")).containsOnly(AUSTRALIA.toCountry());
    }

    @Test
    @DirtiesContext
    public void shouldCountryNameBeUpdated() throws CountryNotFoundException {
        Country australia = countryDao.getCountryByName(AUSTRALIA.getName());

        assertThat(australia.getName()).isEqualTo(AUSTRALIA.getName());
        assertThat(australia.getCodeName()).isEqualTo(AUSTRALIA.getCodeName());

        String newCountryName = "new country name";

        countryDao.updateCountryName(AUSTRALIA.getCodeName(), newCountryName);

        Country newCountry = countryDao.getCountryByName(newCountryName);

        assertThat(newCountry.getName()).isEqualTo(newCountryName);
        assertThat(newCountry.getCodeName()).isEqualTo(AUSTRALIA.getCodeName());
    }

    @Test
    @DirtiesContext
    public void shouldCountryBeReturnedByCodeName() {
        assertThat(countryDao.getCountryByCodeName(AUSTRALIA.getCodeName())).isEqualTo(AUSTRALIA.toCountry());
    }

    @Test
    @DirtiesContext
    public void shouldTestCountriesShouldBeInsertedThroughLoadMethod() {
        countryDao.removeCountries();

        assertThat(countryDao.getCountryList()).isEmpty();

        countryDao.loadCountries();

        List<Country> expectedCountries = Stream.of(Countries.values())
                                                .map(Countries::toCountry)
                                                .collect(Collectors.toList());
        assertThat(countryDao.getCountryList()).containsOnlyElementsOf(expectedCountries);
    }
}