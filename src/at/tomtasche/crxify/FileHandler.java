package at.tomtasche.crxify;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.util.resource.Resource;

public class FileHandler extends DefaultHandler {

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Resource resource = Resource.newResource(request.getRequestURI().substring(1));
		if (!request.getMethod().equals("GET") || !resource.exists() || resource.isDirectory()) return;
		
		response.setHeader("Content-Disposition", "attachment");
		response.setContentType("application/force-download");
		
		resource.writeTo(response.getOutputStream(), 0, resource.length());
		
		response.flushBuffer();
		
		resource.getFile().delete();
	}
}
