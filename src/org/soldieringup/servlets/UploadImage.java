package org.soldieringup.servlets;

import hideftvads.proto.MimeType;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.soldieringup.Utilities;
import org.soldieringup.database.MySQL;

/**
 * Servlet implementation class UploadImageRework
 */
@WebServlet("/UploadImage")
public class UploadImage extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	// Target widths and heights for the different types
	// of images that can be uploaded to the database.
	private static final int TEMP_COVER_IMAGE_WIDTH = 880;
	private static final int TEMP_COVER_IMAGE_HEIGHT = 300;
	private static final int TEMP_PROFILE_IMAGE_WIDTH = 500;
	private static final int TEMP_PROFILE_IMAGE_HEIGHT = 100;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImage()
    {
        super();
    }

	/**
	 * Get method of the photo servlet. It returns the src's of any temporary profile they
	 * user may have uploaded.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// No-op. Only POST requests are used
		PrintWriter out = response.getWriter();
		JSONObject jsonOutput = new JSONObject();
		Enumeration<String> keys = request.getSession().getAttributeNames();
		System.out.println("Doing the get request");
		while( keys.hasMoreElements() )
		{
			String key = keys.nextElement();
			System.out.println("The keys is " + key + " " + request.getSession().getAttribute( key ).toString() );
		}

		if( request.getSession().getAttribute( "temp_cover_src" ) != null )
		{
			jsonOutput.put( "temp_cover_src", request.getSession().getAttribute( "temp_cover_src" ).toString() );
		}

		if( request.getSession().getAttribute( "temp_profile_src" ) != null )
		{
			jsonOutput.put( "temp_profile_src", request.getSession().getAttribute( "temp_profile_src" ).toString() );
		}

		out.println( jsonOutput );
	}

	/**
	 * Takes in a photo from a form, scales it to have at least the IMAGE_WIDTH and IMAGE_HEIGHT, and outputs it to the
	 * browser
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @return Outputs the URL stripped of it's path of the photo that is at least IMAGE_WIDTH by IMAGE_HEIGHT.
	 */
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);

		// Only process the servlet if the form contains an image
		if( isMultiPart )
		{
			uploadPhoto( request, response );
		}
	}

	/**
	 * Extracts a photo from the request and uploads it to the server based on the type of
	 * image being uploaded.
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void uploadPhoto( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		PrintWriter out = response.getWriter();

		boolean accountLoggedIn =  request.getSession().getAttribute("aid") != null && request.getSession().getAttribute( "aid" ) != "";

		if( !( accountLoggedIn ) )
		{
			out.println("You must be logged in to process a photo");
			return;
		}

		// Get the disk file item factory from the Apache server
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold (10000000 );
		factory.setRepository( new File( System.getProperty( "java.io.tmpdir" ) ) );

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(10000000);
		upload.setSizeMax(10000000);

		// Actual path to the server
		String uploadPath = getServletContext().getRealPath("") + File.separator + "files";

		try
		{
			List formInputs = upload.parseRequest( request );
			Iterator formInputIt = formInputs.iterator();
			String uploadType = "";
			String extension = "";
			BufferedImage sourceImage = null;

			// Iterate through all of the form inputs. We should get a image type
			// and an actual image in order to properly add the image to the
			// database and server
			while( formInputIt.hasNext() )
			{
				FileItem formItem = (FileItem) formInputIt.next();

				// Get the type of photo we are uploading
				if( formItem.isFormField() && formItem.getFieldName().equals( "type" ) )
				{
					uploadType = formItem.getString();
				}
				// Get the photo from the uploaded form
				else if( formItem.getFieldName().equals ( "photo" ) )
				{
					sourceImage = ImageIO.read( formItem.getInputStream() );
					extension = generateImageExtension( formItem.getContentType() );
				}
			}

			if( sourceImage != null )
			{
				String fileName = null;

				long time = new Date().getTime();

				// Generate a random string for the src using the sha1 algorithm and the
				// string of 100 times the user. This helps to keep uniqueness for profiles.
				fileName = Utilities.sha1Output( time + "profile" ) + ( 100 * Long.valueOf( request.getSession().getAttribute( "aid" ).toString() ) );

				int targetImageWidth =
						uploadType.equals( MySQL.TEMP_UPLOAD_IMAGE_COVER ) ? TEMP_COVER_IMAGE_WIDTH : TEMP_PROFILE_IMAGE_WIDTH;

				int targetImageHeight =
						uploadType.equals( MySQL.TEMP_UPLOAD_IMAGE_COVER ) ? TEMP_COVER_IMAGE_HEIGHT : TEMP_PROFILE_IMAGE_HEIGHT;

				uploadTemporaryPhoto( sourceImage, fileName, extension, targetImageWidth, targetImageHeight );
				request.getSession().setAttribute( "temp_"+uploadType+"_src", fileName+extension );
			}
		}
		catch (FileUploadException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Uploads a temporary photo for accounts to use as their profile and cover photo.
	 *
	 * If the photo uploaded is not IMAGE_WIDTH pixels in width, we scale the photo appropriately.
	 * If the photo after the initial scaling is not at least IMAGE_HEIGHT pixels in height,
	 * We rescale the photo again. Images resized with this method should not be used as an account's
	 * photo. They need to be either cropped or reposition by the user before they can be used. Another
	 * servlet ResizePhoto takes care of that.
	 * @param sourceImage The image we are trying to upload
	 * @param fileNameWithOutExtension The name of the file without it's extension
	 * @param extension The extension of the file
	 * @param targetImageWidth The width to make the image. The outputted image may be wider
	 * 		  if the image height is not at least minimumImageHeight pixels in height.
	 * @param minimumImageHeight The smallest possible height the image can be. This number
	 * 		  can possibly effect what the final width of the image is.
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	private void uploadTemporaryPhoto( BufferedImage sourceImage,
								   			String fileNameWithOutExtension,
								   			String extension,
								   			int targetImageWidth,
								   			int minimumImageHeight ) throws NoSuchAlgorithmException, IOException
	{
		if( extension != null )
		{
			int coverImageWidth = targetImageWidth;
			int coverImageHeight = sourceImage.getHeight();

			// Image must be targetImageWidth pixels long.
			if( sourceImage.getWidth() != targetImageWidth )
			{
				double thumbNailScaleWidth = (double)targetImageWidth / sourceImage.getWidth();
				coverImageHeight *= thumbNailScaleWidth;
			}

			// Image must be at least targetImageHeight pixels wide;
			if( coverImageHeight < minimumImageHeight )
			{
				double thumbNailScaleHeight = (double)minimumImageHeight / coverImageHeight;
				coverImageWidth *= thumbNailScaleHeight;
				coverImageHeight *= thumbNailScaleHeight;
			}

			BufferedImage resizeImage = new BufferedImage( coverImageWidth, coverImageHeight, sourceImage.getType() );

			// Create the newly resized image, and save it to the permanent location.
			Graphics2D g = resizeImage.createGraphics();
			g.drawImage(sourceImage, 0, 0, coverImageWidth, coverImageHeight, null);
			g.dispose();

			File newImage = new File("C:\\tomcat\\webapps\\soldieringup\\WebContent\\TempUploads" + File.separator + fileNameWithOutExtension + extension);
			ImageIO.write( resizeImage, extension.substring( 1 ), newImage );

			return;
		}
	}

	/**
	 * Generates the extension of the photo based on the given mime type.
	 *
	 * The only extensions valid at the moment are ones related to
	 * jpg and png files.
	 * @param mimeType The mime type to check for an extension
	 * @return The generate extensiion
	 */
	public String generateImageExtension( String mimeType )
	{
		String extension = null;

		if( MimeType.jpg.contentType.equals( mimeType ) )
		{
			extension = ".jpg";
		}
		else if( MimeType.png.contentType.equals( mimeType ) )
		{
			extension = ".png";
		}

		return extension;
	}

}
