package pl.pas.parcellocker.repositories.dao.pack;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import pl.pas.parcellocker.model.List;

@Dao
public interface ListDao extends PackageDao<List> {}
