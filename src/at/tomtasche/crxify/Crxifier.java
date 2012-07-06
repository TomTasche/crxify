package at.tomtasche.crxify;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;

public class Crxifier {

	public static void main(String[] args) {
		Server server = new Server(8080);
		
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
