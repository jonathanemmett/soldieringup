package org.soldieringup.database;

import java.util.List;

import org.soldieringup.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository
{
	@Autowired
	MongoOperations op;

	public List<Role> findAll ()
	{
		return op.findAll (Role.class);
	}

	public void insert (Role role)
	{
		op.insert (role);
	}

	public void save (Role role)
	{
		op.save (role);
	}

	public void delete (String role_name)
	{
		op.remove (new Query(Criteria.where("role").is(role_name)), Role.class);
	}

	public void delete (Role arg0)
	{
		op.remove (arg0);
	}

	public Role findOne (final String role_name)
	{
		Role r = op.findOne (new Query(Criteria.where("role").is(role_name)), Role.class);
		return r;
	}
}
