package org.home.spring.jdbc.model;

import javax.annotation.Nonnull;
import java.util.Objects;

import static java.util.Objects.hash;

public class Country {
    private final int id;
    private final String name;
    private final String codeName;

    private Country(int id, @Nonnull String name, @Nonnull String codeName) {
        this.id = id;
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

    @Override
    public String toString() {
        return id + ". " + name + " (" + codeName + ')';
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        Country otherCountry = (Country) otherObject;

        return Objects.equals(codeName, otherCountry.codeName) && Objects.equals(name, otherCountry.name);
    }

    @Override
    public int hashCode() {
        return hash(name, codeName);
    }

    public static class Builder {
        private int id;
        private String name;
        private String codeName;

        @Nonnull
        public static Builder aCountry() {
            return new Builder();
        }

        @Nonnull
        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        @Nonnull
        public Builder withName(@Nonnull String name) {
            this.name = name;
            return this;
        }

        @Nonnull
        public Builder withCodeName(@Nonnull String codeName) {
            this.codeName = codeName;
            return this;
        }

        @Nonnull
        public Country create() {
            return new Country(id, name, codeName);
        }
    }
}
