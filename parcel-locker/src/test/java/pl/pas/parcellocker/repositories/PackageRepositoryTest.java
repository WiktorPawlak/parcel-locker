package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.List;
import pl.pas.parcellocker.model.Package;
import pl.pas.parcellocker.model.Parcel;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackageRepositoryTest {

    PackageRepository packageRepo = new PackageRepository();
    List list;
    Parcel parcel;

    @BeforeEach
    void setup() {
        list =  new List(BigDecimal.TEN, true);
        parcel = new Parcel(BigDecimal.TEN, 1,2, 3, 4, true);
    }

    @Test
    void shouldAddList() {
        packageRepo.save(list);

        assertEquals(getPackageFromRepo(list), list);
    }

    @Test
    void shouldAddParcel() {
        packageRepo.save(parcel);

        assertEquals(getPackageFromRepo(parcel), parcel);
    }

    @Test
    void shouldUpdatePackage() {
        packageRepo.save(list);
        list.setBasePrice(BigDecimal.ONE);
        packageRepo.update(list);

        assertEquals(BigDecimal.ONE, getPackageFromRepo(list).getBasePrice());
    }

    @Test
    void shouldDeletePackage() {
        packageRepo.save(list);

        packageRepo.delete(list);

        assertNull(getPackageFromRepo(list));
    }

    @Test
    void shouldReturnAllPackages() {
        packageRepo.save(list);
        packageRepo.save(parcel);

        assertTrue(packageRepo.findAll().size() >= 2);
    }

    private Package getPackageFromRepo(Package pack) {
        return packageRepo.findById(pack.getEntityId());
    }


}
