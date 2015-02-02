import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.Handler;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.platform.Verticle;

// Simple chat server, connect via tcp and send lines of text that will be sent to all connected parties
public class ChatServer extends Verticle {
  public void start() {
    vertx.createNetServer().connectHandler(new Handler<NetSocket>() {    	
      // Handle incoming connection
      public void handle(final NetSocket socket) {

      	// Register data handler (publish to event bus on incoming data)
        socket.dataHandler(new Handler<Buffer> () {
      		public void handle(final Buffer buffer) {
      			vertx.eventBus().publish("message", buffer);
        	}	
        });

        // Register event bus handler (write all published messages to socket)
        vertx.eventBus().registerHandler("message", new Handler<Message<Buffer>> () {
			public void handle(final Message<Buffer> message) {
      			socket.write(message.body());
        	}
        });	
      }
    }).listen(1234);
  }
}