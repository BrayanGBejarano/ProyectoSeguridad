import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class PanelViewServer extends JPanel implements ActionListener {

    private static final String ACCEPT = "Accept";
    private static final String EDIT = "Edit";
    private static final String SELECT_FILE = "Select File";

    private JLabel lb_ipserver;
    private JTextField txt_ipserver;
    private JLabel lb_port;
    private JTextField txt_port;
    private JButton btn_accept;
    private JButton btn_edit;
    private JButton btn_chooser;

    private MainViewServer mainViewServer;

    public PanelViewServer(MainViewServer mainViewServer) {

        this.mainViewServer = mainViewServer;

        setLayout(null);
        setBackground(Color.WHITE);

        lb_ipserver = new JLabel("IP:");
        lb_ipserver.setBounds(50, 30, 20, 25);
        txt_ipserver = new JTextField();
        txt_ipserver.setBounds(70, 30, 100, 25);
        txt_ipserver.setEnabled(false);

        lb_port = new JLabel("Port:");
        lb_port.setBounds(200, 30, 50, 25);
        txt_port = new JTextField();
        txt_port.setBounds(250, 30, 50, 25);
        txt_port.setEnabled(false);

        btn_accept = new JButton(ACCEPT);
        btn_accept.setActionCommand(ACCEPT);
        btn_accept.addActionListener(this);
        btn_accept.setBounds(50, 60, 80, 25);

        btn_edit = new JButton(EDIT);
        btn_edit.setActionCommand(EDIT);
        btn_edit.addActionListener(this);
        btn_edit.setBounds(150, 60, 80, 25);

        btn_chooser = new JButton(SELECT_FILE);
        btn_chooser.setActionCommand(SELECT_FILE);
        btn_chooser.addActionListener(this);
        btn_chooser.setBounds(50, 120, 100, 25);

        add(lb_ipserver);
        add(txt_ipserver);
        add(lb_port);
        add(txt_port);
        add(btn_accept);
        add(btn_edit);
        add(btn_chooser);
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals(ACCEPT)){
            mainViewServer.accept();
        } else if (command.equals(EDIT)){
            mainViewServer.edit();
        }

    }

    public JLabel getLb_ipserver() {
        return lb_ipserver;
    }

    public void setLb_ipserver(JLabel lb_ipserver) {
        this.lb_ipserver = lb_ipserver;
    }

    public JTextField getTxt_ipserver() {
        return txt_ipserver;
    }

    public void setTxt_ipserver(JTextField txt_ipserver) {
        this.txt_ipserver = txt_ipserver;
    }

    public JLabel getLb_port() {
        return lb_port;
    }

    public void setLb_port(JLabel lb_port) {
        this.lb_port = lb_port;
    }

    public JTextField getTxt_port() {
        return txt_port;
    }

    public void setTxt_port(JTextField txt_port) {
        this.txt_port = txt_port;
    }

    public JButton getBtn_accept() {
        return btn_accept;
    }

    public void setBtn_accept(JButton btn_accept) {
        this.btn_accept = btn_accept;
    }

    public JButton getBtn_edit() {
        return btn_edit;
    }

    public void setBtn_edit(JButton btn_edit) {
        this.btn_edit = btn_edit;
    }

    public JButton getBtn_chooser() {
        return btn_chooser;
    }

    public void setBtn_chooser(JButton btn_chooser) {
        this.btn_chooser = btn_chooser;
    }

    public MainViewServer getMainViewServer() {
        return mainViewServer;
    }

    public void setMainViewServer(MainViewServer mainViewServer) {
        this.mainViewServer = mainViewServer;
    }
}
