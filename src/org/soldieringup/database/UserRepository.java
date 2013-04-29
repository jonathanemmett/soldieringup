package org.soldieringup.database;

import org.soldieringup.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String>
{
	public boolean emailExists (String email);

	@Query("{ 'email' : ?0, 'password' : ?1 }")
	public User findByUsernameAndPassword(final String email, final String password);
}
