package examples.showcase.swing.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.commons.lang3.StringUtils;

import examples.showcase.swing.LoginFrame;

public class ResetActionListener implements ActionListener {

	private LoginFrame loginFrame;

	public ResetActionListener(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		loginFrame.getTxtUserName().setText(StringUtils.EMPTY);
		loginFrame.getTxtUserPassword().setText(StringUtils.EMPTY);
		loginFrame.getTxtUserName().grabFocus();
	}
}