package java8playground.optional;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java8playground.optional.OptionalLegacyTest.Category.A;
import static java8playground.optional.OptionalLegacyTest.Category.B;
import static java8playground.optional.OptionalLegacyTest.Category.C;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionalLegacyTest {

    @Test
    public void noDrivingLicence() {
        assertFalse(canDriveSmallVehicle(new Person(null)));
    }

    @Test
    public void bCategoryDrivingLicence() {
        assertTrue(canDriveSmallVehicle(new Person(new DrivingLicence(A, B))));
    }

    @Test
    public void noBCategoryDrivingLicence() {
        assertFalse(canDriveSmallVehicle(new Person(new DrivingLicence(C))));
    }

    private Boolean canDriveSmallVehicle(Person person) {
        return Optional.ofNullable(person)
                .map(Person::getDrivingLicence)
                .map(DrivingLicence::getCategories)
                .map(categories -> categories.contains(B))
                .orElse(Boolean.FALSE);
    }

    enum Category {
        A, B, C, D
    }

    static class Person {

        private DrivingLicence drivingLicence;

        public Person(DrivingLicence drivingLicence) {
            this.drivingLicence = drivingLicence;
        }

        public DrivingLicence getDrivingLicence() {
            return drivingLicence;
        }
    }

    static class DrivingLicence {

        private Set<Category> categories;

        public DrivingLicence(Category... categories) {
            this.categories = new HashSet<>(Arrays.asList(categories));
        }

        public Set<Category> getCategories() {
            return categories;
        }
    }


}
