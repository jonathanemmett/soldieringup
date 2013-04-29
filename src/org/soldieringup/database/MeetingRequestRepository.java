package org.soldieringup.database;

import org.soldieringup.MeetingRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MeetingRequestRepository extends MongoRepository<MeetingRequest, String>
{

}
