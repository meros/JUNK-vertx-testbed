import org.vertx.java.core.Handler;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.platform.Verticle;

public class EchoServer extends Verticle {
  public void start() {
    vertx.createNetServer().connectHandler(new Handler<NetSocket>() {
      public void handle(final NetSocket socket) {
      	System.out.println("New socket connected");
        Pump.createPump(socket, socket).start();
      }
    }).listen(1234);
  }
}