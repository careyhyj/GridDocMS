package ui.section;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class NewSectionJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldSectionName;
	private JTextField textFieldSectionPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewSectionJDialog dialog = new NewSectionJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewSectionJDialog() {
		setTitle("新建节");
		setBounds(100, 100, 450, 312);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("创建一个新的节");
			label.setFont(new Font("宋体", Font.BOLD, 12));
			label.setBounds(10, 10, 184, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("请在文本框内输入创建的节的名称，并指定其路径。");
			label.setBounds(22, 35, 381, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("节名称：");
			label.setBounds(22, 72, 48, 15);
			contentPanel.add(label);
		}
		{
			textFieldSectionName = new JTextField();
			textFieldSectionName.setColumns(10);
			textFieldSectionName.setBounds(76, 69, 137, 21);
			contentPanel.add(textFieldSectionName);
		}
		{
			JLabel label = new JLabel("节路径：");
			label.setBounds(22, 100, 48, 15);
			contentPanel.add(label);
		}
		{
			textFieldSectionPath = new JTextField();
			textFieldSectionPath.setColumns(10);
			textFieldSectionPath.setBounds(76, 97, 282, 21);
			contentPanel.add(textFieldSectionPath);
		}
		{
			JButton buttonBrowserDir = new JButton("...");
			buttonBrowserDir.setBounds(368, 96, 38, 23);
			contentPanel.add(buttonBrowserDir);
		}
		{
			JLabel label = new JLabel("节位置：");
			label.setBounds(22, 128, 54, 15);
			contentPanel.add(label);
		}
		
		JComboBox comboBoxSectionIndex = new JComboBox();
		comboBoxSectionIndex.setBounds(76, 125, 66, 21);
		contentPanel.add(comboBoxSectionIndex);
		
		JCheckBox chckbxIsSynCreateSection = new JCheckBox("是否同步创建");
		chckbxIsSynCreateSection.setBounds(224, 68, 103, 23);
		contentPanel.add(chckbxIsSynCreateSection);
		
		JLabel lblNewLabel = new JLabel("节描述：");
		lblNewLabel.setBounds(22, 176, 54, 15);
		contentPanel.add(lblNewLabel);
		
		JTextArea textAreaSectionDesc = new JTextArea();
		textAreaSectionDesc.setLineWrap(true);
		textAreaSectionDesc.setBounds(76, 156, 329, 63);
		contentPanel.add(textAreaSectionDesc);
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
