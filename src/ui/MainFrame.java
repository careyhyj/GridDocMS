package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import ui.griddoc.NewGridDocJDialog;
import ui.paragraph.MergeParagraphJDialog;
import ui.paragraph.NewParagraphJDialog;
import ui.paragraph.ParagraphPropertyJDialog;
import ui.section.MergeSectionJDialog;
import ui.section.NewSectionJDialog;
import ui.section.SectionPropertyJDialog;
import ui.system.NewConnectionJDialog;
import ui.system.SwitchWorksJDialog;

import com.csasc.gdmodel.GridocSystem.GriDocFileSystem;

public class MainFrame {

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JFrame frmGriddoc;
	private JTextField textField;
	private JTextField searchTextField;
	private JPopupMenu popupMenuGridDoc;
	private JTable table;
	private GriDocFileSystem gridocFileSystem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
					MainFrame window = new MainFrame();
					window.frmGriddoc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 初始化系统界面控件
	 */
	private void initialize() {
		frmGriddoc = new JFrame();
		frmGriddoc.setTitle("GridDoc文档管理器");
		frmGriddoc.setBounds(100, 100, 1024, 768);
		frmGriddoc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		/***** 初始化菜单栏 *****/
		JMenuBar mainMenuBar = new JMenuBar();
		frmGriddoc.setJMenuBar(mainMenuBar);

		/** 1.“文件”菜单项 **/
		JMenu menuFile = new JMenu("文件");
		mainMenuBar.add(menuFile);

		JMenu childMenuConnection = new JMenu("连接");
		menuFile.add(childMenuConnection);

		JMenuItem menuItem = new JMenuItem("新建连接...");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewConnectionJDialog().setVisible(true);
			}
		});
		childMenuConnection.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("打开连接...");
		childMenuConnection.add(menuItem_1);

		JMenuItem menuItem_2 = new JMenuItem("关闭连接...");
		childMenuConnection.add(menuItem_2);

		JSeparator separator = new JSeparator();
		menuFile.add(separator);

		JMenu childMenuNewFile = new JMenu("新建...");
		menuFile.add(childMenuNewFile);

		JMenuItem jMenuItemNewGridDoc = new JMenuItem("新建格文档...");
		jMenuItemNewGridDoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewGridDocJDialog().setVisible(true);
			}
		});
		JMenuItem mntmNewMenuItem = new JMenuItem("新建节...");
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("新建段...");
		
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewParagraphJDialog().setVisible(true);
			}
		});
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewSectionJDialog().setVisible(true);
			}
		});
		
		childMenuNewFile.add(jMenuItemNewGridDoc);
		childMenuNewFile.add(mntmNewMenuItem);
		childMenuNewFile.add(mntmNewMenuItem_1);

		JMenuItem menuItemOpenFile = new JMenuItem("打开...");
		menuItemOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
			    if( jfc.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION ) {
			      System.out.println(jfc.getSelectedFile().getAbsolutePath());
			    }
			}
		});
		menuFile.add(menuItemOpenFile);

		JSeparator separator_1 = new JSeparator();
		menuFile.add(separator_1);

		JMenuItem menuItemSaveAs = new JMenuItem("另存为...");
		menuItemSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
			    if( jfc.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION ) {
			      System.out.println(jfc.getSelectedFile().getAbsolutePath());
			    }
			}
		});
		menuFile.add(menuItemSaveAs);

		JSeparator separator_2 = new JSeparator();
		menuFile.add(separator_2);

		JMenuItem menuItemPrint = new JMenuItem("打印...");
		menuFile.add(menuItemPrint);

		JSeparator separator_3 = new JSeparator();
		menuFile.add(separator_3);
		
		JMenuItem menuItem_3 = new JMenuItem("指定工作集...");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwitchWorksJDialog().setVisible(true);
			}
		});
		menuFile.add(menuItem_3);
		
		JSeparator separator_11 = new JSeparator();
		menuFile.add(separator_11);

		JMenuItem menuItemImport = new JMenuItem("导入...");
		menuFile.add(menuItemImport);

		JSeparator separator_4 = new JSeparator();
		menuFile.add(separator_4);

		JMenuItem menuItemProperties = new JMenuItem("属性");
		menuFile.add(menuItemProperties);

		JSeparator separator_5 = new JSeparator();
		menuFile.add(separator_5);

		JMenuItem menuItemExit = new JMenuItem("退出");
		menuFile.add(menuItemExit);

		/** 2.“编辑”菜单项 **/
		JMenu menuEdit = new JMenu("编辑");
		mainMenuBar.add(menuEdit);

		JMenuItem menuItemCopy = new JMenuItem("复制");
		menuEdit.add(menuItemCopy);

		JMenuItem menuItemPaste = new JMenuItem("粘贴");
		menuEdit.add(menuItemPaste);

		JSeparator separator_6 = new JSeparator();
		menuEdit.add(separator_6);

		JMenuItem menuItemDelete = new JMenuItem("删除");
		menuEdit.add(menuItemDelete);

		JSeparator separator_7 = new JSeparator();
		menuEdit.add(separator_7);

		JMenuItem menuItemMergePara = new JMenuItem("合并段...");
		menuItemMergePara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MergeParagraphJDialog().setVisible(true);
			}
		});
		menuEdit.add(menuItemMergePara);

		JMenuItem menuItemSplitPara = new JMenuItem("分割段...");
		menuItemSplitPara.setEnabled(false);
		menuEdit.add(menuItemSplitPara);

		JSeparator separator_8 = new JSeparator();
		menuEdit.add(separator_8);

		JMenuItem menuItemFindReplace = new JMenuItem("查找/替换");
		menuEdit.add(menuItemFindReplace);

		/** 3.“视图”菜单项 **/
		JMenu menuView = new JMenu("视图");
		mainMenuBar.add(menuView);

		JMenuItem menuItemGridDocView = new JMenuItem("GridDoc视图");
		menuView.add(menuItemGridDocView);

		JMenuItem menuItemUserView = new JMenuItem("用户视图");
		menuView.add(menuItemUserView);

		JSeparator separator_9 = new JSeparator();
		menuView.add(separator_9);

		JMenuItem menuItemEditUserView = new JMenuItem("编辑用户视图...");
		menuView.add(menuItemEditUserView);

		/** 4.“查询”菜单项 **/
		JMenu menuSearch = new JMenu("查询");
		mainMenuBar.add(menuSearch);

		JMenuItem menuItemContentSearch = new JMenuItem("内容查询...");
		menuSearch.add(menuItemContentSearch);

		JMenuItem menuItemFileSearch = new JMenuItem("文件查询...");
		menuSearch.add(menuItemFileSearch);

		JSeparator separator_10 = new JSeparator();
		menuSearch.add(separator_10);

		JMenuItem menuItemReferenceSearch = new JMenuItem("引用查询...");
		menuSearch.add(menuItemReferenceSearch);

		/** 5.“工具”菜单项 **/
		JMenu menuTools = new JMenu("工具");
		mainMenuBar.add(menuTools);

		JMenuItem menuItemConvertDataFormat = new JMenuItem("数据格式转换...");
		menuTools.add(menuItemConvertDataFormat);

		JMenuItem menuItemSynchronizeData = new JMenuItem("数据同步...");
		menuTools.add(menuItemSynchronizeData);

		JMenuItem menuItemWeeding = new JMenuItem("文档去重");
		menuTools.add(menuItemWeeding);

		/** 6.“运行”菜单项 **/
		JMenu menuItemRunGridDoc = new JMenu("运行");
		mainMenuBar.add(menuItemRunGridDoc);

		JMenuItem mntmgriddoc = new JMenuItem("打开/运行GridDoc");
		menuItemRunGridDoc.add(mntmgriddoc);

		/** 7.“帮助”菜单项 **/
		JMenu menuHelp = new JMenu("帮助");
		mainMenuBar.add(menuHelp);

		JMenuItem menuItemHelp = new JMenuItem("帮助主题");
		menuHelp.add(menuItemHelp);

		JMenuItem menuItemAbout = new JMenuItem("关于...");
		menuHelp.add(menuItemAbout);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frmGriddoc.getContentPane().setLayout(gridBagLayout);

		
		/***** 初始化工具栏 *****/
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.anchor = GridBagConstraints.WEST;
		gbc_toolBar.gridwidth = 10;
		gbc_toolBar.insets = new Insets(5, 0, 5, 5);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		frmGriddoc.getContentPane().add(toolBar, gbc_toolBar);

		JButton buttonAdd = new JButton("");
		buttonAdd.setIcon(new ImageIcon("G:\\Workspace\\NewGriDocMS\\icons\\Button-Add-01-small.png"));
		toolBar.add(buttonAdd);

		JButton buttonOpen = new JButton("");
		buttonOpen.setIcon(new ImageIcon("G:\\Workspace\\NewGriDocMS\\icons\\Folder-01-small.png"));
		toolBar.add(buttonOpen);

		JButton buttonCopy = new JButton("");
		buttonCopy.setIcon(new ImageIcon("G:\\Workspace\\NewGriDocMS\\icons\\copy_file-small.jpg"));
		toolBar.add(buttonCopy);

		JButton buttonEdit = new JButton("");
		buttonEdit.setIcon(new ImageIcon("G:\\Workspace\\NewGriDocMS\\icons\\Document-01-small.png"));
		toolBar.add(buttonEdit);

		JButton buttonCut = new JButton("");
		buttonCut.setIcon(new ImageIcon("G:\\Workspace\\NewGriDocMS\\icons\\cut-small.png"));
		toolBar.add(buttonCut);

		JButton buttonList = new JButton("");
		buttonList.setIcon(new ImageIcon("G:\\Workspace\\NewGriDocMS\\icons\\list-small.png"));
		toolBar.add(buttonList);

		/***** 初始化右上角的搜索文本框 *****/
		searchTextField = new JTextField();
		searchTextField.setForeground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_searchTextField = new GridBagConstraints();
		gbc_searchTextField.insets = new Insets(5, 0, 5, 5);
		gbc_searchTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchTextField.gridx = 16;
		gbc_searchTextField.gridy = 0;
		frmGriddoc.getContentPane().add(searchTextField, gbc_searchTextField);
		searchTextField.setColumns(10);

		
		/***** 初始化主面板 *****/
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.4);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.gridwidth = 17;
		gbc_splitPane.anchor = GridBagConstraints.WEST;
		gbc_splitPane.insets = new Insets(0, 5, 0, 5);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		splitPane.setDividerLocation(0.5);
		frmGriddoc.getContentPane().add(splitPane, gbc_splitPane);

		
		/***** 初始化左侧树形结构菜单 *****/
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);

		JScrollPane scrollPaneGridDoc = new JScrollPane();
		tabbedPane.addTab("GridDoc", null, scrollPaneGridDoc, null);

		final JTree treeGridDoc = new JTree();
		treeGridDoc.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Global View") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					DefaultMutableTreeNode node_3;
					node_1 = new DefaultMutableTreeNode("GridDoc1");
						node_2 = new DefaultMutableTreeNode("section1");
							node_2.add(new DefaultMutableTreeNode("paragraph2"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("section2");
							node_3 = new DefaultMutableTreeNode("section1");
								node_3.add(new DefaultMutableTreeNode("paragraph2"));
							node_2.add(node_3);
							node_2.add(new DefaultMutableTreeNode("paragraph3"));
						node_1.add(node_2);
						node_1.add(new DefaultMutableTreeNode("section3"));
						node_1.add(new DefaultMutableTreeNode("paragraph1"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("GridDoc2");
						node_2 = new DefaultMutableTreeNode("section1");
							node_2.add(new DefaultMutableTreeNode("paragraph2"));
						node_1.add(node_2);
						node_1.add(new DefaultMutableTreeNode("section3"));
						node_1.add(new DefaultMutableTreeNode("paragraph2"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("GridDoc3");
						node_1.add(new DefaultMutableTreeNode("section3"));
						node_1.add(new DefaultMutableTreeNode("paragraph3"));
					add(node_1);
				}
			}
		));
		scrollPaneGridDoc.setViewportView(treeGridDoc);
		
		final JPopupMenu popupMenuRoot = getRootPopupMenu();
		final JPopupMenu popupMenuGridDoc = getGridDocPopupMenu();
		final JPopupMenu popupMenuSection = getSectionPopupMenu();
		final JPopupMenu popupMenuParagraph = getParagraphPopupMenu();
		
		
		treeGridDoc.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					TreePath treePath = treeGridDoc.getPathForLocation(e.getX(), e.getY());
					if (treePath != null)
					{
						treeGridDoc.setSelectionPath(treePath);
					}
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeGridDoc.getLastSelectedPathComponent();
//					System.out.println(selectedNode.toString());
					if (selectedNode.toString().contains("Global View")) {
						popupMenuRoot.show(e.getComponent(), e.getX(), e.getY());
					} else if (selectedNode.toString().contains("GridDoc")) {
						popupMenuGridDoc.show(e.getComponent(), e.getX(), e.getY());
					} else if (selectedNode.toString().contains("section")) {
						popupMenuSection.show(e.getComponent(), e.getX(), e.getY());
					} else if (selectedNode.toString().contains("paragraph")) {
						popupMenuParagraph.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		});
		
//		addPopup(treeGridDoc, popupMenuGridDoc);

		JScrollPane scrollPaneUserView = new JScrollPane();
		tabbedPane.addTab("用户视图", null, scrollPaneUserView, null);

		JTree treeUserView = new JTree();
		scrollPaneUserView.setViewportView(treeUserView);

		JScrollPane scrollPaneSearchResult = new JScrollPane();
		tabbedPane.addTab("查询结果", null, scrollPaneSearchResult, null);

		JTree treeSearchResult = new JTree();
		scrollPaneSearchResult.setViewportView(treeSearchResult);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("文件系统", null, scrollPane, null);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_1.setRightComponent(scrollPane_1);
		splitPane_1.setVisible(true);
		splitPane_1.setDividerLocation(500);
		
		
		JPanel panel = new JPanel();
		splitPane_1.setLeftComponent(panel);
		
		
		
	
		
		/***** 初始化右上角表格 *****/
		Object[][] cellData = {{"paragraph1", "2014-12-26", "Admin"}}; 
		String[] columnNames = {"名称", "最后修改时间", "拥有者"};
		table = new JTable(cellData, columnNames);
		scrollPane_1.setViewportView(table);
		
		/***** 定义菜单的点击事件 *****/
		
		/** 3.“视图”菜单点击 **/
		
		// 切换到用户视图
		menuItemUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		
		// 切换到GridDoc全局视图
		menuItemGridDocView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0);
			}
		});
	}
	
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
//				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeGridDoc.getLastSelectedPathComponent();
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private JPopupMenu getRootPopupMenu() {
		JPopupMenu popupMenuRoot = new JPopupMenu();
		
		JMenuItem jMenuItemNewGridDoc = new JMenuItem("新建格文档...");
		jMenuItemNewGridDoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewGridDocJDialog().setVisible(true);
			}
		});
		popupMenuRoot.add(jMenuItemNewGridDoc);
		popupMenuRoot.add(new JMenuItem("添加格文档..."));
		
		JMenuItem jMenuItem_4 = new JMenuItem("指定工作集...");
		jMenuItem_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SwitchWorksJDialog().setVisible(true);
			}
		});
		popupMenuRoot.add(jMenuItem_4);
		
		popupMenuRoot.add(new JMenuItem("查找"));
		popupMenuRoot.add(new JMenuItem("导入..."));
		popupMenuRoot.add(new JMenuItem("导出..."));
		popupMenuRoot.add(new JMenuItem("属性"));
		
		return popupMenuRoot;
	}
	
	
	private JPopupMenu getGridDocPopupMenu() {
		JPopupMenu popupMenuGridDoc = new JPopupMenu();
		
		JMenu jMenuAdd = new JMenu("新建");
		JMenuItem jMenuItemNewPara = new JMenuItem("新建段...");
		jMenuItemNewPara.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewParagraphJDialog().setVisible(true);
			}
		});
		JMenuItem jMenuItemNewSection = new JMenuItem("新建节...");
		jMenuItemNewSection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewSectionJDialog().setVisible(true);
			}
		});
		
		jMenuAdd.add(jMenuItemNewPara);
		jMenuAdd.add(jMenuItemNewSection);
		
		popupMenuGridDoc.add(jMenuAdd);
		popupMenuGridDoc.add(new JMenuItem("打开格文档"));
		popupMenuGridDoc.add(new JMenuItem("关闭格文档"));
		popupMenuGridDoc.add(new JMenuItem("复制"));
		popupMenuGridDoc.add(new JMenuItem("粘贴"));
		popupMenuGridDoc.add(new JMenuItem("同步删除"));
		popupMenuGridDoc.add(new JMenuItem("导入..."));
		popupMenuGridDoc.add(new JMenuItem("导出..."));
		popupMenuGridDoc.add(new JMenuItem("配置..."));
		popupMenuGridDoc.add(new JMenuItem("另存为..."));
		popupMenuGridDoc.add(new JMenuItem("查找"));
		popupMenuGridDoc.add(new JMenuItem("打印"));
		popupMenuGridDoc.add(new JMenuItem("重命名"));
		popupMenuGridDoc.add(new JMenuItem("属性"));
		
		return popupMenuGridDoc;
	}
	
	private JPopupMenu getSectionPopupMenu() {
		JPopupMenu popupMenuSection = new JPopupMenu();
		
		JMenu jMenuAdd = new JMenu("新建");
		JMenuItem jMenuItemNewPara = new JMenuItem("新建段...");
		jMenuItemNewPara.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewParagraphJDialog().setVisible(true);
			}
		});
		JMenuItem jMenuItemNewSection = new JMenuItem("新建节...");
		jMenuItemNewSection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewSectionJDialog().setVisible(true);
			}
		});
		jMenuAdd.add(jMenuItemNewPara);
		jMenuAdd.add(jMenuItemNewSection);
		
		popupMenuSection.add(jMenuAdd);
		popupMenuSection.add(new JMenuItem("复制"));
		popupMenuSection.add(new JMenuItem("粘贴"));
		
		JMenu jMenuDelete = new JMenu("删除");
		jMenuDelete.add(new JMenuItem("同步删除"));
		jMenuDelete.add(new JMenuItem("去同步删除"));
		
		popupMenuSection.add(jMenuDelete);
		popupMenuSection.add(new JMenuItem("移动"));
		
		JMenuItem jMenuItem_6 = new JMenuItem("合并...");
		jMenuItem_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MergeSectionJDialog().setVisible(true);
			}
		});
		popupMenuSection.add(jMenuItem_6);
		
		popupMenuSection.add(new JMenuItem("导入..."));
		popupMenuSection.add(new JMenuItem("导出..."));
		popupMenuSection.add(new JMenuItem("配置..."));
		popupMenuSection.add(new JMenuItem("替换为..."));
		popupMenuSection.add(new JMenuItem("另存为..."));
		popupMenuSection.add(new JMenuItem("查找"));
		popupMenuSection.add(new JMenuItem("打印"));
		popupMenuSection.add(new JMenuItem("重命名"));
		
		JMenuItem sectionProperty = new JMenuItem("属性");
		sectionProperty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SectionPropertyJDialog().setVisible(true);
			}
		});
		popupMenuSection.add(sectionProperty);
		
		return popupMenuSection;
	}
	
	private JPopupMenu getParagraphPopupMenu() {
		JPopupMenu popupMenuParagraph = new JPopupMenu();
		JMenuItem jMenuItemNewPara = new JMenuItem("新建段...");
		jMenuItemNewPara.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NewParagraphJDialog().setVisible(true);
			}
		});
		popupMenuParagraph.add(jMenuItemNewPara);
		popupMenuParagraph.add(new JMenuItem("复制"));
		popupMenuParagraph.add(new JMenuItem("粘贴"));
		
		JMenu jMenuDelete = new JMenu("删除");
		jMenuDelete.add(new JMenuItem("同步删除"));
		jMenuDelete.add(new JMenuItem("去同步删除"));
		
		popupMenuParagraph.add(jMenuDelete);
		popupMenuParagraph.add(new JMenuItem("移动"));
		
		JMenuItem jMenuItem_6 = new JMenuItem("合并...");
		jMenuItem_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MergeParagraphJDialog().setVisible(true);
			}
		});
		popupMenuParagraph.add(jMenuItem_6);
		
		popupMenuParagraph.add(new JMenuItem("追加数据"));
		popupMenuParagraph.add(new JMenuItem("读取数据"));
		popupMenuParagraph.add(new JMenuItem("导入..."));
		popupMenuParagraph.add(new JMenuItem("导出..."));
		popupMenuParagraph.add(new JMenuItem("配置..."));
		popupMenuParagraph.add(new JMenuItem("替换为..."));
		popupMenuParagraph.add(new JMenuItem("另存为..."));
		popupMenuParagraph.add(new JMenuItem("查找"));
		popupMenuParagraph.add(new JMenuItem("打印"));
		popupMenuParagraph.add(new JMenuItem("重命名"));
		JMenuItem paragraphProperty = new JMenuItem("属性");
		paragraphProperty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ParagraphPropertyJDialog().setVisible(true);
			}
		});
		popupMenuParagraph.add(paragraphProperty);
		
		return popupMenuParagraph;
	}
}
