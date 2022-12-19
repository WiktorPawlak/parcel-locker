package pl.pas.parcellocker.repositories.dao.pack;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import pl.pas.parcellocker.model.Parcel;

@Dao
public interface ParcelDao extends PackageDao<Parcel> {}

