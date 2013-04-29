package org.soldieringup.database;

import java.util.List;

import org.soldieringup.Zip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipRepository extends MongoRepository<Zip, String>
{
	List<Zip> findByState(String state);

}
