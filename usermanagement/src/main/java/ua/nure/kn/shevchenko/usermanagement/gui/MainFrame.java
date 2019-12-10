package ua.nure.kn.shevchenko.usermanagement.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kn.shevchenko.usermanagement.User;
import ua.nure.kn.shevchenko.usermanagement.db.DaoFactory;
import ua.nure.kn.shevchenko.usermanagement.db.UserDAO;
import ua.nure.kn.shevchenko.usermanagement.util.Messages;

public class MainFrame extends JFrame {
	
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private JPanel contentPanel;
	private BrowsePanel browsePanel;
	private AddPanel addPanel;
	private UserDAO dao;
	private DetailsPanel detailsPanel;
	private EditPanel editPanel;

	public UserDAO getDao() {
		return dao;
	}

	public MainFrame(){
		super();
		dao = (DaoFactory.getInstance().getUserDao());
		initialize();
	}
	
	 private void initialize() {
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	        this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
	        this.setContentPane(getContentPanel());
	    }
	 
	 private JPanel getContentPanel() {
	        if(contentPanel == null) {
	            contentPanel = new JPanel();
	            contentPanel.setLayout(new BorderLayout());
	            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
	        }
	        return contentPanel;
	    }
	 
	 private BrowsePanel getBrowsePanel() {
	        if(browsePanel == null) {
	            browsePanel = new BrowsePanel(this);
	        }
	        ((BrowsePanel)browsePanel).initTable();
	        return browsePanel;
	    }

	private AddPanel getAddPanel() {
        if(addPanel == null) {
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
	
	
	public void showAddPanel() {
		showPanel(getAddPanel());
	}
	
	public void showEditPanel() {
		showPanel(getEditPanel());
	}
	
	public void showDetailsPanel() {
		showPanel(getDetailsPanel());
	}
	
	public void showBrowsePanel() {
		showPanel(getBrowsePanel());
	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
        frame.setVisible(true);
	}

	public User getSelectedUser() {
		return ((BrowsePanel) browsePanel).getSelectedUser();
	}

	
}
