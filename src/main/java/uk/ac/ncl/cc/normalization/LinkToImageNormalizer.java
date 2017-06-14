package uk.ac.ncl.cc.normalization;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by b4060825 
 */


public class LinkToImageNormalizer implements Normalizer {
	/**
     *  Replaces all the links to image files  within the token
     * with the Keyword "image"
     * 
     * @param token
     * @return token with the image links  replaced with keyword
     */	
	
	private String[] imagelinks = new String[] {
	          ".tif",
	          ".tiff",
	          ".gif",
	          ".jpeg",
	          ".jpg",".png",
	          ".jif",
	          ".jfif",".jp2",".jpx",".j2k",".j2c",".fpx",".pcd",".pdf"
	          
	         };
	 public LinkToImageNormalizer() {
	        // default constructor
	    }

	    public LinkToImageNormalizer(String[] more) {
	    	imagelinks = ArrayUtils.addAll(imagelinks, more);
	    }
	    @Override
	    public String normalize(String token)
	    {
	    	
	    	if (StringUtils.endsWithAny(token.toLowerCase(),imagelinks))
	  		{
	      token="image";}
			return token;
	    	
	    }

}
