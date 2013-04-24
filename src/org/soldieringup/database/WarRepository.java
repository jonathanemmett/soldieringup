package org.soldieringup.database;

import org.soldieringup.War;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarRepository extends MongoRepository<War, String>
{
}
