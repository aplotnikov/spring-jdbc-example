package org.home.spring.jdbc.service;

import org.home.spring.jdbc.model.Country;
import org.springframework.transaction.annotation.Propagation;

import javax.annotation.Nonnull;
import java.util.List;

public interface CountryService {
    @Nonnull List<Country> getAllCountriesInsideTransaction(@Nonnull Propagation propagation);

    @Nonnull List<Country> getAllCountriesRequired();

    @Nonnull List<Country> getAllCountriesRequiresNew();

    @Nonnull List<Country> getAllCountriesSupports();

    @Nonnull List<Country> getAllCountriesNever();

    @Nonnull List<Country> getAllCountriesMandatory();

    @Nonnull List<Country> getAllCountriesNotSupported();

    @Nonnull List<Country> getAllCountries();
}
