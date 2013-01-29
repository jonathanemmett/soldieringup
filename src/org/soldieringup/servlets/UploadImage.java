package org.soldieringup.servlets;

import java.awt.BufferCapabilities;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.soldieringup.Business;
import org.soldieringup.database.MySQL;

import sun.awt.image.ToolkitImage;

import com.sun.media.sound.Toolkit;

/**
 * Servlet that takes in a Cover Photo, and adjusts it to an image of 880 by 300.
 * @author Jake LaCombe
 */

@WebServlet("/UploadImage")
public class UploadImage extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static final int IMAGE_WIDTH = 880;
	private static final int IMAGE_HEIGHT = 300;
       
    /**
     * Constructor
     */
    public UploadImage() 
    {
        super();
    }

	/**
	 * Get method of the photo servlet. It currently does nothing
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// No-op. Only POST requests are used
	}

	/**
	 * Takes in a photo from a form, scales it to have at least the IMAGE_WIDTH and IMAGE_HEIGHT, and outputs it to the
	 * browser
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @return Outputs the URL stripped of it's path of the photo that is at least IMAGE_WIDTH by IMAGE_HEIGHT.   
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		MySQL databaseConnection = MySQL.getInstance();

		if( request.getSession().getAttribute("bid") == null && request.getSession().getAttribute( "bid" ) == "" )
		{
			out.println("You must be logged in to process a photo");
			return;
		}

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	
		// Only process the image for multipart forms
		if( isMultipart )
		{   
			// Get the disk file item factory from the Apache server
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(10000000);
			factory.setRepository( new File( System.getProperty( "java.io.tmpdir" ) ) );
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(10000000);
			upload.setSizeMax(10000000);	
			
			// Actual path to the server
			String uploadPath = getServletContext().getRealPath("") + File.separator + "files";
			
			// X and Y positions of the left hand corner of the photo. Only non-positive numbers are allowed
			// for this value.
			int coverPhotoXPosition = 1;
			int coverPhotoYPosition = 1;
			
			try 
			{
				System.out.println("Business id: " + request.getSession().getAttribute( "bid" ) );

				long bid = (int) request.getSession().getAttribute( "bid" );
				System.out.println("Business id: " + bid );
				Business processingBusiness = databaseConnection.getBusiness( bid );
				System.out.println("Business id: " + processingBusiness.getBid() );

				List formInputs = upload.parseRequest( request );
				Iterator formInputIt = formInputs.iterator();
				
				// Iterate through all of the form inputs. If we have two valid coordinates, that means we are finding the
				// position of the scaled photo. Otherwise, the user uploaded a photo, and we need to scale it.
				while( formInputIt.hasNext() )
				{
					FileItem formItem = (FileItem) formInputIt.next();
					
					// Position of the photo
					if( formItem.isFormField() )
					{
						if( formItem.getFieldName().equals( "cover_photo_x" ) && formItem.getString() != null )
						{
							coverPhotoXPosition = Integer.parseInt( formItem.getString() );
						}
						
						if( formItem.getFieldName().equals( "cover_photo_y" ) && formItem.getString() != null)
						{
							coverPhotoYPosition = Integer.parseInt( formItem.getString() );
						}	
					}
					// Photo scaling logic
					else
					{
						BufferedImage sourceImage = ImageIO.read( formItem.getInputStream() );
						
						int thumbNailImageWidth = IMAGE_WIDTH;
						int thumbNailImageHeight = sourceImage.getHeight();
						
						// Image must be IMAGE_WIDTH pixels long.
						if( sourceImage.getWidth() != IMAGE_WIDTH )
						{
							double thumbNailScaleWidth = (double)( (double)IMAGE_WIDTH / sourceImage.getWidth() );
							thumbNailImageHeight *= thumbNailScaleWidth;
						}

						// Image must be at least IMAGE_HEIGHT pixels wide; 
						if( thumbNailImageHeight < IMAGE_HEIGHT )
						{
							double thumbNailScaleHeight = (double)( (double)IMAGE_HEIGHT / thumbNailImageHeight );
							thumbNailImageWidth *= thumbNailScaleHeight;
							thumbNailImageHeight *= thumbNailScaleHeight;
						}						

						BufferedImage resizeImage = new BufferedImage( thumbNailImageWidth, thumbNailImageHeight, sourceImage.getType() );
						
						// Create the newly resized image, and save it to the permanent location.
						Graphics2D g = resizeImage.createGraphics();
						g.drawImage(sourceImage, 0, 0, thumbNailImageWidth, thumbNailImageHeight, null);
						g.dispose();
						File newImage = new File("C:\\tomcat\\webapps\\soldieringup\\WebContent\\TempUploads" + File.separator+"JakeUpload.png");
						ImageIO.write( resizeImage, "png", newImage );
						
						// Insert the cover photo into the database
						PreparedStatement insertPhoto =
								databaseConnection.getPreparedStatement("INSERT INTO Photos( bid, title, src) VALUES( ?, 'Cover Photo', ? )");						
						insertPhoto.setLong( 1,  processingBusiness.getBid() );
						insertPhoto.setString( 2, "JakeUpload.png");
						insertPhoto.executeUpdate();
						
						ResultSet photoId = insertPhoto.getGeneratedKeys();
						
						// Now we update the cover_photo id associated with the business
						if( photoId.first() )
						{
							PreparedStatement setCoverId =
								databaseConnection.getPreparedStatement( "UPDATE Business set cover_photo_id = ? WHERE bid = ?" );
						
							setCoverId.setInt( 1, Integer.parseInt( photoId.getString(1) ));
							setCoverId.setLong( 2, processingBusiness.getBid() );
							setCoverId.executeUpdate();
						}
						
						out.println("TempUploads/JakeUpload.png");
						
					}
				}
			}
			catch (FileUploadException e) 
			{
				System.out.println("Errored on upload");
			} 
			catch (Exception e) 
			{
				System.out.println("Unknown error");
			}
			
			if( coverPhotoXPosition <=0  && coverPhotoYPosition <= 0 )
			{
				try 
				{
					readjustCoverPhoto( (int)request.getSession().getAttribute( "bid" ), coverPhotoXPosition, coverPhotoYPosition, response.getWriter() );
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
	}
	
	protected void readjustCoverPhoto( int id, int photoXPosition, int photoYPosition, PrintWriter out ) throws SQLException
	{
		MySQL databaseConnection = MySQL.getInstance();
		
		// Find the cover id from the business to ensure security
		PreparedStatement coverPhotoLookup = databaseConnection.getPreparedStatement(" SELECT cover_photo_id FROM Business WHERE bid = ? ");
		coverPhotoLookup.setInt(1, id);
		
		ResultSet coverIDResult = coverPhotoLookup.executeQuery();
		
		if( coverIDResult.first() )
		{
			int coverID = coverIDResult.getInt(1);
			PreparedStatement coverSrcQuery = databaseConnection.getPreparedStatement( "SELECT src FROM Photos WHERE pid = ?" );
			coverSrcQuery.setInt( 1, coverID );
			
			ResultSet coverSrcResult = coverSrcQuery.executeQuery();
			if( coverSrcResult.first() )
			{
				String src = coverSrcResult.getString(1);
				
				try {
					// Upload the image from the temp directory.
					System.out.println("C:\\tomcat\\webapps\\soldieringup\\WebContent\\TempUploads" + File.separator + src);
					BufferedImage coverImage = ImageIO.read( new File("C:\\tomcat\\webapps\\soldieringup\\WebContent\\TempUploads" + File.separator + src) );
					
					// Create the new image with the image type from the cover image.
					BufferedImage repositionedCoverImage = new BufferedImage( IMAGE_WIDTH, IMAGE_HEIGHT, coverImage.getType() );
					Graphics2D repositionedGraphics = repositionedCoverImage.createGraphics();

					// Reposition the photo properly
					if( photoXPosition > 0)
					{
						photoXPosition = 0;
					}
					
					if( photoYPosition > 0 )
					{
						photoYPosition = 0;
					}
					else if( coverImage.getHeight() - photoYPosition *-1 < IMAGE_HEIGHT )
					{
						photoYPosition = IMAGE_HEIGHT - coverImage.getHeight();
					}
					
					
					repositionedGraphics.drawImage( coverImage, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, photoXPosition * - 1, photoYPosition * -1, photoXPosition * - 1 + IMAGE_WIDTH, IMAGE_HEIGHT + photoYPosition * -1, null );
					ImageIO.write( repositionedCoverImage, "png", new File("C:\\tomcat\\webapps\\soldieringup\\WebContent\\Images" + File.separator + src) );
					
					out.println("TempUploads" + File.separator + src);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
