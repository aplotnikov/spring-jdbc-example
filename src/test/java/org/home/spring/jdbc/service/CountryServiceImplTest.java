package org.home.spring.jdbc.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.IllegalTransactionStateException;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = org.home.spring.jdbc.context.ApplicationContext.class)
@ActiveProfiles("dev")
public class CountryServiceImplTest {
    @Rule
    public ExpectedException exceptionChecker = ExpectedException.none();
    @Inject
    private CountryService countryService;

    @Test
    @DirtiesContext
    public void shouldSomeValueBeReturnedWhenMethodIsExecutedWithRequiredPropagationAndInsideTransaction() {
        assertThat(countryService.getAllCountriesInsideTransaction(REQUIRED)).isNotEmpty();
    }

    @Test
    @DirtiesContext
    public void shouldSomeValueBeReturnedWhenMethodIsExecutedWithRequiredPropagationAndWithoutTransaction() {
        assertThat(countryService.getAllCountriesRequired()).isNotEmpty();
    }

    @Test
    @DirtiesContext
    public void shouldExceptionBeThrownWhenMethodIsExecutedWithMandatoryPropagationAndWithoutTransaction() {
        exceptionChecker.expect(IllegalTransactionStateException.class);

        countryService.getAllCountriesMandatory();
    }

    @Test
    @DirtiesContext
    public void shouldSomeValueBeReturnedWhenMethodIsExecutedWithMandatoryPropagationAndInsideTransaction() {
        assertThat(countryService.getAllCountriesInsideTransaction(MANDATORY)).isNotEmpty();
    }
}