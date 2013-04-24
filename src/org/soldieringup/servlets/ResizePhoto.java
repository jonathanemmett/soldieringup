package org.soldieringup.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.soldieringup.SoldierUpAccount;
import org.soldieringup.User;
import org.soldieringup.service.MongoEngine;

/**
 * Servlet implementation class ResizePhoto
 */
@WebServlet("/ResizePhoto")
public class ResizePhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Exact widths and heights that the uploaded images will
	// be for when they are used for various profiles
	private static final int COVER_IMAGE_WIDTH = 880;
	private static final int COVER_IMAGE_HEIGHT = 300;
	private static final int PROFILE_IMAGE_WIDTH = 100;
	private static final int PROFILE_IMAGE_HEIGHT = 100;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResizePhoto()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession currentSession = request.getSession();

		MongoEngine engine = new MongoEngine();
		SoldierUpAccount account = getLoggedInAccount( currentSession );

		if( request.getParameter( "cover_photo_y" ) != null )
		{
			int coverPhotoYPosition = Integer.parseInt( request.getParameter( "cover_photo_y" ) );
			if( coverPhotoYPosition <= 0 )
			{

				readjustCoverPhoto(
						request.getSession().getAttribute( "temp_cover_src").toString(),
						coverPhotoYPosition );

				// Once the cover photo is repositioned, set the cover photo src for the current account.
				account.setCoverSrc( request.getSession().getAttribute( "temp_cover_src" ).toString() );
				if( currentSession.getAttribute( "editing_account_type" ).equals( "veteran" ) )
				{
					engine.updateUser( (User)account );
				}
				else
				{
					engine.updateAccount( account );
				}

			}
		}
		if( request.getParameter( "upload_profile_pic_x" ) != null && request.getParameter( "upload_profile_pic_y" ) != null &&
				request.getParameter( "upload_profile_pic_width" ) != null && request.getParameter( "upload_profile_pic_height" ) != null)
		{
			int profilePhotoXPosition = Integer.parseInt( request.getParameter( "upload_profile_pic_x" ) );
			int profilePhotoYPosition = Integer.parseInt( request.getParameter( "upload_profile_pic_y" ) );
			int profilePhotoWidth = Integer.parseInt( request.getParameter( "upload_profile_pic_width" ) );
			int profilePhotoHeight = Integer.parseInt( request.getParameter( "upload_profile_pic_height" ) );

			readjustProfilePhoto(
					request.getSession().getAttribute( "temp_profile_src" ).toString(),
					profilePhotoXPosition,
					profilePhotoYPosition,
					profilePhotoWidth,
					profilePhotoHeight );

			// Once we've adjusted the profile photo, set the profile photo src for the current account.
			account.setProfileSrc( request.getSession().getAttribute( "temp_profile_src").toString() );

			if( currentSession.getAttribute( "editing_account_type" ).equals( "veteran" ) )
			{
				engine.updateUser( (User)account );
			}
			else
			{
				engine.updateAccount( account );
			}
		}
	}

	/**
	 * Repositions the given cover photo for the given y positions.
	 *
	 * Since the y positions is coming from a javascript application, its value will be negative
	 * @param aPhotoSrc SRC of the cover photo to reposition
	 * @param aPhotoYPosition Y position of the cover Photo
	 * @throws SQLException
	 */
	protected void readjustCoverPhoto( String aPhotoSrc, int aPhotoYPosition )
	{
		try
		{
			// Upload the image from the temp directory.
			File tempCoverImageFile = new File("C:\\tomcat\\webapps\\soldierupmongo\\WebContent\\TempUploads" + File.separator + aPhotoSrc);
			BufferedImage tempCoverImage = ImageIO.read( tempCoverImageFile );

			// Create the new image with the image type from the cover image.
			BufferedImage repositionedCoverImage = new BufferedImage( COVER_IMAGE_WIDTH, COVER_IMAGE_HEIGHT, tempCoverImage.getType() );
			Graphics2D repositionedGraphics = repositionedCoverImage.createGraphics();

			// Reposition the Y position of the cover. The X position for all cover photos should
			// be 0, so we don't need to worry about the X values.
			if( aPhotoYPosition > 0 )
			{
				aPhotoYPosition = 0;
			}
			else if( tempCoverImage.getHeight() - aPhotoYPosition *-1 < COVER_IMAGE_HEIGHT )
			{
				aPhotoYPosition = COVER_IMAGE_HEIGHT - tempCoverImage.getHeight();
			}

			repositionedGraphics.drawImage(
					tempCoverImage,
					0,
					0,
					COVER_IMAGE_WIDTH,
					COVER_IMAGE_HEIGHT,
					0,
					aPhotoYPosition * -1,
					COVER_IMAGE_WIDTH,
					COVER_IMAGE_HEIGHT + aPhotoYPosition * -1,
					null );

			ImageIO.write(
					repositionedCoverImage,
					"png",
					new File("C:\\tomcat\\webapps\\soldierupmongo\\WebContent\\Images" + File.separator + aPhotoSrc ) );

			tempCoverImageFile.delete();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Readjusts the profile photo depending on the position and size of the cropped square
	 * @param aPhotoSrc SRC of the photo to adjust
	 * @param profilePhotoXPosition X position of the cropping square
	 * @param profilePhotoYPosition Y position of the cropping square
	 * @param profilePhotoWidth Width of the cropping square
	 * @param profilePhotoHeight Height of the cropping square
	 * @return
	 */
	public void readjustProfilePhoto(
			String aPhotoSrc,
			int profilePhotoXPosition,
			int profilePhotoYPosition,
			int profilePhotoWidth,
			int profilePhotoHeight )
	{
		// Upload the image from the temp directory.
		try
		{
			BufferedImage coverImage = ImageIO.read( new File("C:\\tomcat\\webapps\\soldierupmongo\\WebContent\\TempUploads" + File.separator + aPhotoSrc ) );

			// Create the new image with the image type from the cover image.
			BufferedImage repositionedCoverImage = new BufferedImage( PROFILE_IMAGE_WIDTH, PROFILE_IMAGE_HEIGHT, coverImage.getType() );
			Graphics2D repositionedGraphics = repositionedCoverImage.createGraphics();

			repositionedGraphics.drawImage( coverImage, 0, 0, PROFILE_IMAGE_WIDTH, PROFILE_IMAGE_HEIGHT, profilePhotoXPosition, profilePhotoYPosition, profilePhotoXPosition + profilePhotoWidth, profilePhotoYPosition + profilePhotoHeight, null );
			ImageIO.write( repositionedCoverImage, "png", new File("C:\\tomcat\\webapps\\soldierupmongo\\WebContent\\Images" + File.separator + aPhotoSrc ) );
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the account ID associated with the account being edited
	 * for a given session
	 * @return The AID of the account for the given section
	 */
	public SoldierUpAccount getLoggedInAccount( HttpSession aSession )
	{
		MongoEngine engine = new MongoEngine();
		if( aSession.getAttribute( "editing_account_type" ).equals( "veteran" ) )
		{
			return engine.findUsers( "id", aSession.getAttribute( "uid" ) ).get( 0 );
		}
		else
		{
			return engine.findAccounts( "id", aSession.getAttribute( "aid" ) ).get( 0 );
		}
	}
}
