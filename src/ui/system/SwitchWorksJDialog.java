package ui.system;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SwitchWorksJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField workspaceTextField;
	private String workspacePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SwitchWorksJDialog dialog = new SwitchWorksJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SwitchWorksJDialog() {
		setTitle("指定工作集");
		setBounds(100, 100, 732, 239);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("请指定您的一个工作集");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 10, 154, 15);
		contentPanel.add(lblNewLabel);
		{
			JLabel label = new JLabel("系统将您的格文档存储在一个特定的目录中，这便是您的工作集，请指定您工作集所对应的目录。");
			label.setBounds(20, 35, 658, 39);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("工作集：");
			label.setBounds(10, 101, 54, 15);
			contentPanel.add(label);
		}
		
		workspaceTextField = new JTextField();
		workspaceTextField.setBounds(64, 96, 551, 27);
		contentPanel.add(workspaceTextField);
		workspaceTextField.setColumns(10);
		
		JButton btnBrowserDir = new JButton("浏览...");
		btnBrowserDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
			    if( jfc.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION ) {
			      System.out.println(jfc.getSelectedFile().getAbsolutePath());
			    }
			}
		});
		btnBrowserDir.setBounds(623, 97, 83, 23);
		contentPanel.add(btnBrowserDir);
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
