package org.soldieringup.constants;

public class ProfilePhotoTypes
{
	public static enum ProfilePhotoType
	{
		PROFILE_PHOTO_TYPE_COVER (0),
		PROFILE_PHOTO_TYPE_PROFILE (1),
		
		PROFILE_PHOTO_TYPE_COUNT (2);
		
		private final int index;
		
		ProfilePhotoType(int index){
			this.index = index;
		}
		
		public int index(){
			return index;
		}
	}
	
	public static String PROFILE_PHOTO_TYPE_STRINGS[] =
	{
		"cover",
		"profile"
	};
	
	public static String PROFILE_PHOTO_TEMP_TYPE_STRINGS[] =
	{
		"temp_cover_src",
		"temp_profile_src"
	};
}
