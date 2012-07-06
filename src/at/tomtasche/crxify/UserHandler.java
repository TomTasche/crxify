package at.tomtasche.crxify;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.util.resource.Resource;

public class UserHandler extends DefaultHandler {

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		if (!request.getMethod().equals("GET") || request.getRequestURI().endsWith(".crx")) return;

		if (!request.getRequestURI().endsWith("pack") || !request.getParameterMap().containsKey("url")) {
			Resource resource = Resource.newResource(new File("index.html"));
			
			resource.writeTo(response.getOutputStream(), 0, resource.length());
			
			response.flushBuffer();
		} else {
			String url = request.getParameter("url");
			String id = UUID.randomUUID().toString();

			try {
				Process process = Runtime.getRuntime().exec("wget -O " + id + ".zip " + url);

				File file = new File(id);
				file.mkdir();

				try {
					process.waitFor();
				} catch (InterruptedException e) {}

				process = Runtime.getRuntime().exec("unzip " + id + ".zip -d " + id);

				try {
					process.waitFor();
				} catch (InterruptedException e) {}

				file = new File(id + ".zip");
				file.delete();

				file = new File(id);

				process = Runtime.getRuntime().exec("libs/crxi " + file.listFiles()[0] + " pem.pem " + id);

				try {
					process.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				file = new File(id);
				deleteDir(file);
				
				response.getWriter().write(
						"<html><body><a href=\"" + id + ".crx" + "\">Download .crx</a> and go to chrome://extensions in order to install it</body></html>"
					);
				response.getWriter().flush();

				//			response.sendRedirect(id + ".crx");
			} catch (IOException e) {
				e.printStackTrace();

				throw new IOException("made a big boo-boo :(", e);
			}
		}
	}
	
	// taken from: http://www.exampledepot.com/egs/java.io/DeleteDir.html
	// Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns false.
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}
}
