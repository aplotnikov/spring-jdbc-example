package org.home.spring.jdbc.dao;

import org.home.spring.jdbc.model.Country;

import javax.annotation.Nonnull;

import static org.home.spring.jdbc.model.Country.Builder.aCountry;

public enum Countries {
    AUSTRALIA("Australia", "AU"),
    CANADA("Canada", "CA"),
    FRANCE("France", "FR"),
    HONG_KONG("Hong Kong", "HK"),
    ICELAND("Iceland", "IC"),
    JAPAN("Japan", "JP"),
    NEPAL("Nepal", "NP"),
    RUSSIAN_FEDERATION("Russian Federation", "RU"),
    SWEDEN("Sweden", "SE"),
    SWITZERLAND("Switzerland", "CH"),
    UNITED_KINGDOM("United Kingdom", "GB"),
    UNITED_STATES("United States", "US");

    private final String name;
    private final String codeName;

    Countries(String name, String codeName) {
        this.name = name;
        this.codeName = codeName;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getCodeName() {
        return codeName;
    }

    @Nonnull
    public Country toCountry() {
        return aCountry()
                .withName(name)
                .withCodeName(codeName)
                .create();
    }
}
