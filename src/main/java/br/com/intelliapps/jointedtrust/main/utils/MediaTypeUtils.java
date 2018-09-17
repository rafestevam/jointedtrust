package br.com.intelliapps.jointedtrust.main.utils;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;

public class MediaTypeUtils {

	public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
		String mimeType = servletContext.getMimeType(fileName);
		try {
			MediaType mediaType = MediaType.parseMediaType(mimeType);
			return mediaType;
		}catch(Exception e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

}
