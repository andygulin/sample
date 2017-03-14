package examples.showcase.swing.event;

import examples.showcase.swing.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginActionListener implements ActionListener, KeyListener {

    private LoginFrame loginFrame;

    public LoginActionListener(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    private void loginAction() {
        String txtUserName = loginFrame.getTxtUserName().getText();
        String txtUserPassword = new String(loginFrame.getTxtUserPassword().getPassword());
        final String userName = "admin";
        final String userPass = "admin";
        if (txtUserName.equals(userName) && txtUserPassword.equals(userPass)) {
            JOptionPane.showMessageDialog(loginFrame, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(loginFrame, "登录失败", "提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loginAction();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            loginAction();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}