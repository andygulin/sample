package examples.showcase.swing;

import examples.showcase.swing.event.LoginActionListener;
import examples.showcase.swing.event.ResetActionListener;

import javax.swing.*;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 3200307453802674024L;

    private JLabel lblUserName;
    private JLabel lblUserPassword;
    private JTextField txtUserName;
    private JPasswordField txtUserPassword;
    private JButton btnLogin;
    private JButton btnReset;

    public LoginFrame() {
        lblUserName = new JLabel("用户名");
        lblUserName.setBounds(60, 30, 50, 20);

        lblUserPassword = new JLabel("密码");
        lblUserPassword.setBounds(60, 60, 50, 20);

        txtUserName = new JTextField();
        txtUserName.setBounds(130, 30, 180, 25);
        txtUserName.addKeyListener(new LoginActionListener(this));

        txtUserPassword = new JPasswordField();
        txtUserPassword.setBounds(130, 60, 180, 25);
        txtUserPassword.addKeyListener(new LoginActionListener(this));

        btnLogin = new JButton("登录");
        btnLogin.setBounds(80, 120, 100, 25);
        btnLogin.addActionListener(new LoginActionListener(this));

        btnReset = new JButton("重置");
        btnReset.setBounds(200, 120, 100, 25);
        btnReset.addActionListener(new ResetActionListener(this));

        this.add(lblUserName);
        this.add(lblUserPassword);
        this.add(txtUserName);
        this.add(txtUserPassword);
        this.add(btnLogin);
        this.add(btnReset);

        txtUserName.grabFocus();
        this.setTitle("登录");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocation(200, 200);
        this.setVisible(true);
    }

    public JLabel getLblUserName() {
        return lblUserName;
    }

    public void setLblUserName(JLabel lblUserName) {
        this.lblUserName = lblUserName;
    }

    public JLabel getLblUserPassword() {
        return lblUserPassword;
    }

    public void setLblUserPassword(JLabel lblUserPassword) {
        this.lblUserPassword = lblUserPassword;
    }

    public JTextField getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(JTextField txtUserName) {
        this.txtUserName = txtUserName;
    }

    public JPasswordField getTxtUserPassword() {
        return txtUserPassword;
    }

    public void setTxtUserPassword(JPasswordField txtUserPassword) {
        this.txtUserPassword = txtUserPassword;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public void setBtnReset(JButton btnReset) {
        this.btnReset = btnReset;
    }

}