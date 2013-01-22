package org.soldieringup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.soldieringup.database.MySQL;

public class Divisions {

	private ResultSet divisions;
	
	public Divisions(){
		MySQL connection = MySQL.getInstance();
		try {
			PreparedStatement divisionsQuery = connection.getPreparedStatement( "SELECT * FROM divisions" );
			divisions = divisionsQuery.executeQuery();
		} catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getDivisions()
	{
		return divisions;
	}
}
