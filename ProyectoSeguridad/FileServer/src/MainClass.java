import java.net.*;

public class MainClass {

	private String host = "127.0.0.1";
	private int puerto = 30000;

	@SuppressWarnings("resource")
	public MainClass() {

		try {
			ServerSocket soc = new ServerSocket(puerto);
			while (true) {
				Socket socket = soc.accept();
				new ThreadFileTransfer(socket).start();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
}
