package org.soldieringup.database;

import java.util.List;

import org.soldieringup.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

	/**
	 * Returns Pageable requests from the database
	 * Parameter defines what starting page you want, 0 or next page = 1.
	 * @param i (page n)
	 * @return List<Question>
	 */
	@Override
	public Page<Question> findAll(Pageable pageable);

	@Query("{ 'aFieldName' : ?0, 'aFieldValue' : ?1 }")
	public List<Question> findByField (String aFieldName, Object aFieldValue);
}
