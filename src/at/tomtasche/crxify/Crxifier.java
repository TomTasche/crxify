package at.tomtasche.crxify;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;

public class Crxifier {

	public static void main(String[] args) {
		int port = System.getenv("PORT") != null ? Integer.valueOf(System.getenv("PORT")) : 8080;
		
		Server server = new Server(port);
		
		Handler fileHandler = new FileHandler();
        Handler userHandler = new UserHandler();
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { fileHandler, userHandler });
        server.setHandler(handlers);
        
        try {
			server.start();
			server.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
