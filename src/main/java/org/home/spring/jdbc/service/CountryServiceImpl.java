package org.home.spring.jdbc.service;

import org.home.spring.jdbc.dao.CountryDao;
import org.home.spring.jdbc.model.Country;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;
import static org.springframework.transaction.annotation.Propagation.NEVER;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Repository
@Transactional
public class CountryServiceImpl implements CountryService {
    private final CountryDao countryDao;
    private final Map<Propagation, Supplier<List<Country>>> transactionPropagationToFunction;

    @Inject
    public CountryServiceImpl(CountryDao countryDao) {
        this.countryDao = countryDao;

        transactionPropagationToFunction = new EnumMap<>(Propagation.class);
        transactionPropagationToFunction.put(REQUIRED, this::getAllCountriesRequired);
        transactionPropagationToFunction.put(REQUIRES_NEW, this::getAllCountriesRequiresNew);
        transactionPropagationToFunction.put(SUPPORTS, this::getAllCountriesSupports);
        transactionPropagationToFunction.put(NEVER, this::getAllCountriesNever);
        transactionPropagationToFunction.put(MANDATORY, this::getAllCountriesMandatory);
        transactionPropagationToFunction.put(NOT_SUPPORTED, this::getAllCountriesNotSupported);
    }

    @Nonnull
    @Override
    public List<Country> getAllCountriesInsideTransaction(@Nonnull Propagation propagation) {
        return transactionPropagationToFunction.getOrDefault(propagation, this::getAllCountries)
                                               .get();
    }

    @Transactional(readOnly = false, propagation = REQUIRED)
    @Nonnull
    @Override
    public List<Country> getAllCountriesRequired() {
        return countryDao.getCountryList();
    }

    @Transactional(readOnly = false, propagation = REQUIRES_NEW)
    @Nonnull
    @Override
    public List<Country> getAllCountriesRequiresNew() {
        return countryDao.getCountryList();
    }

    @Transactional(readOnly = false, propagation = SUPPORTS)
    @Nonnull
    @Override
    public List<Country> getAllCountriesSupports() {
        return countryDao.getCountryList();
    }

    @Transactional(readOnly = false, propagation = NEVER)
    @Nonnull
    @Override
    public List<Country> getAllCountriesNever() {
        return countryDao.getCountryList();
    }

    @Transactional(readOnly = false, propagation = MANDATORY)
    @Nonnull
    @Override
    public List<Country> getAllCountriesMandatory() {
        return countryDao.getCountryList();
    }

    @Transactional(readOnly = false, propagation = NOT_SUPPORTED)
    @Nonnull
    @Override
    public List<Country> getAllCountriesNotSupported() {
        return countryDao.getCountryList();
    }

    @Nonnull
    @Override
    public List<Country> getAllCountries() {
        return countryDao.getCountryList();
    }
}