package ui.system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class NewConnectionJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldConnName;
	private JTextField textFieldConnIP;
	private JTextField textFieldConnPort;
	private JTextField textFieldConnUsername;
	private JTextField textFieldConnPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewConnectionJDialog dialog = new NewConnectionJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewConnectionJDialog() {
		setTitle("新建连接");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("创建一个新的连接");
			label.setFont(new Font("宋体", Font.BOLD, 12));
			label.setBounds(10, 10, 184, 15);
			contentPanel.add(label);
		}
		{
			JLabel lblip = new JLabel("请在文本框内输入创建的连接的名称，并指定其IP地址和端口。");
			lblip.setBounds(22, 35, 381, 15);
			contentPanel.add(lblip);
		}
		{
			JLabel label = new JLabel("连接名称：");
			label.setBounds(34, 74, 77, 15);
			contentPanel.add(label);
		}
		{
			JLabel lblIp = new JLabel("连接IP地址：");
			lblIp.setBounds(22, 102, 79, 15);
			contentPanel.add(lblIp);
		}
		{
			JLabel label = new JLabel("连接端口：");
			label.setBounds(34, 130, 66, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("用户名：");
			label.setBounds(44, 158, 54, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("密码：");
			label.setBounds(54, 186, 54, 15);
			contentPanel.add(label);
		}
		{
			textFieldConnName = new JTextField();
			textFieldConnName.setBounds(103, 71, 117, 21);
			contentPanel.add(textFieldConnName);
			textFieldConnName.setColumns(10);
		}
		{
			textFieldConnIP = new JTextField();
			textFieldConnIP.setBounds(103, 99, 189, 21);
			contentPanel.add(textFieldConnIP);
			textFieldConnIP.setColumns(10);
		}
		{
			textFieldConnPort = new JTextField();
			textFieldConnPort.setBounds(103, 127, 71, 21);
			contentPanel.add(textFieldConnPort);
			textFieldConnPort.setColumns(10);
		}
		{
			textFieldConnUsername = new JTextField();
			textFieldConnUsername.setBounds(103, 155, 158, 21);
			contentPanel.add(textFieldConnUsername);
			textFieldConnUsername.setColumns(10);
		}
		{
			textFieldConnPassword = new JTextField();
			textFieldConnPassword.setBounds(103, 183, 158, 21);
			contentPanel.add(textFieldConnPassword);
			textFieldConnPassword.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
