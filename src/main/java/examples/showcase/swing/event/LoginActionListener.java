package examples.showcase.swing.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import examples.showcase.swing.LoginFrame;

public class LoginActionListener implements ActionListener, KeyListener {

	private LoginFrame loginFrame;

	public LoginActionListener(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	private void action() {
		String txtUserName = loginFrame.getTxtUserName().getText();
		String txtUserPassword = new String(loginFrame.getTxtUserPassword().getPassword());
		if (txtUserName.equals("admin") && txtUserPassword.equals("admin")) {
			JOptionPane.showMessageDialog(loginFrame, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(loginFrame, "登录失败", "提示", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			action();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}