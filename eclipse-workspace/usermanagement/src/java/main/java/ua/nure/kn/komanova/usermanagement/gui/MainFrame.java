package main.java.ua.nure.kn.komanova.usermanagement.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.ua.nure.kn.komanova.usermanagement.User;
import main.java.ua.nure.kn.komanova.usermanagement.db.UserDao;
import main.java.ua.nure.kn.komanova.usermanagement.db.DaoFactory;
import main.java.ua.nure.kn.komanova.usermanagement.util.Messages;

public class MainFrame extends JFrame {
private static final long serialVersionUID = 4039449182616164656L;
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private JPanel contentPanel;
	private BrowsePanel browsePanel;
	private AddPanel addPanel;
	private EditPanel editPanel;
	private DetailsPanel detailsPanel;
	private Dao dao;
	
	public MainFrame() {
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}
	
	public UserDao getUserDao() {
		return dao;
	}

	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
		this.setContentPane(getContentPanel());
	}		

	private AddPanel getAddPanel() {
		if (addPanel == null) {
			addPanel = new AddPanel(this);
		}		
		return addPanel;
	}
	
	private EditPanel getEditPanel() {
		if (editPanel == null) {
			editPanel = new EditPanel(this);
		}
		return editPanel;
	}
	
	private DetailsPanel getDetailsPanel() {
		if (detailsPanel == null) {
			detailsPanel = new DetailsPanel(this);
		}
		return detailsPanel;
	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	public void showAddPanel() {
		showPanel(getAddPanel());
	}
	
	public void showEditPanel(User user) {
		getEditPanel().setUser(user);
        showPanel(getEditPanel());
	}
	
	public void showDetailsPanel(User user) {
        JPanel detailsPanel = getDetailsPanel();
        ((DetailsPanel) detailsPanel).showUserDetails(user);
        showPanel(detailsPanel);
    }
	
	public void showBrowsePanel() { 
		showPanel(getBrowsePanel());
	}

	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);			
		}
		return contentPanel;
	}
	
	private JPanel getBrowsePanel() {
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);		
		}
		((BrowsePanel) browsePanel).initTable();
		return browsePanel;
	}
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

}
