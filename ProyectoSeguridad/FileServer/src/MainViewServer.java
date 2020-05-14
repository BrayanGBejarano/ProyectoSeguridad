import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainViewServer extends JFrame {

    private PanelViewServer panelViewServer;

    @SuppressWarnings("unused")
	private JFileChooser jFileChooser;

    private MainClass server;

    public MainViewServer() {
        initialize();
        center();
        server = new MainClass();
        panelViewServer.setLb_ipserver(new JLabel(server.getHost()));
        panelViewServer.setLb_port(new JLabel(server.getPuerto()+""));
    }

    private void initialize() {

        setTitle("Encryted File Transfer :: SERVER");
        setSize(400, 400);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        panelViewServer = new PanelViewServer(this);
        add(panelViewServer, BorderLayout.CENTER);

    }

    private void center() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dimension.width - getWidth()) / 2;
        int y = (dimension.height - getHeight()) / 2;
        setLocation(x, y);
    }

    public void accept(){
        panelViewServer.getTxt_ipserver().setEnabled(false);
        server.setHost(panelViewServer.getTxt_ipserver().toString());
        panelViewServer.getTxt_port().setEnabled(false);
        server.setPuerto(Integer.parseInt(panelViewServer.getTxt_port().toString()));
    }

    public void edit(){
        panelViewServer.getTxt_ipserver().setEnabled(true);
        panelViewServer.getTxt_port().setEnabled(true);
    }

    public static void main(String[] args) {
        MainViewServer mainViewServer = new MainViewServer();
        mainViewServer.setVisible(true);
    }
}
