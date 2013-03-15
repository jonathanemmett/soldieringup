package org.soldieringup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SoldierUpAccount
{
	public static enum AccountColumns
	{
		AID(0),
		UID(1),
		PROFILE_SRC(2);

		int mColumnIndex;

		AccountColumns(int aColumnIndex)
		{
			this.mColumnIndex = aColumnIndex;
		}

		String getDatabaseColumn()
		{
			return AccountColumnsString[mColumnIndex];
		}
	};

	public static String[] AccountColumnsString =
	{
		"aid",
		"uid",
		"profile_src"
	};

	private long mAid;
	private long mUid;
	private String mProfileSrc;

	public SoldierUpAccount()
	{

	}

	/**
	 * Initializes a soldierup account from a database result set
	 * @param aAccountResultSet The result set to initialize the account with
	 * @throws SQLException
	 */
	public void init( ResultSet aAccountResultSet ) throws SQLException
	{
		mAid = aAccountResultSet.getLong( AccountColumns.AID.getDatabaseColumn() );
		mUid = aAccountResultSet.getLong( AccountColumns.UID.getDatabaseColumn() );
		mProfileSrc = aAccountResultSet.getString( AccountColumns.PROFILE_SRC.getDatabaseColumn() );
	}

	public void setAid( long aAid )
	{
		this.mAid = aAid;
	}

	public void setUid( long aUid )
	{
		this.mUid = aUid;
	}

	public void setProfileSrc( String aProfileSrc )
	{
		this.mProfileSrc = aProfileSrc;
	}

	public long getAid()
	{
		return mAid;
	}

	public long getUid()
	{
		return mUid;
	}

	public String getProfileSrc()
	{
		return mProfileSrc;
	}
}
