package main.java.ua.nure.kn.komanova.usermanagement.gui;

mport java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.main.ua.nure.kn.komanova.usermanagement.User;
import java.main.ua.nure.kn.komanova.usermanagement.db.DatabaseExeption;
import java.main.ua.nure.kn.komanova.usermanagement.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {

private static final long serialVersionUID = 1449895692872086369L;
	
	private static final String ERROR_MESSAGE = "Error"; 
	private static final String EDIT_COMMAND = "edit"; 
	private static final String ADD_COMMAND = "add"; 
	private static final String DELETE_COMMAND = "delete"; 
	private static final String DETAIL_COMMAND = "detail";
	private static final String USER_TABLE_COMPONENT_NAME = "userTable"; 
	private static final String DETAIL_BUTTON_COMPONENT_NAME = "detailButton"; 
	private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton"; 
	private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton"; 
	private static final String ADD_BUTTON_COMPONENT_NAME = "addButton"; 
	private static final String BROWSE_PANEL_COMPONENT_NAME = "browsePanel"; 
	
	private MainFrame parent;
	private JScrollPane tablePanel;
	private JTable userTable;
	private JPanel buttonPanel;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton detailButton;

	public BrowsePanel(MainFrame mainFrame) {
		parent = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setName(BROWSE_PANEL_COMPONENT_NAME);	
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(), BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel getButtonsPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getAddButton(), null);
			buttonPanel.add(getEditButton(), null);
			buttonPanel.add(getDeleteButton(), null);
			buttonPanel.add(getDetailsButton(), null);
		}
		return buttonPanel;
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText(Messages.getString("BrowsePanel.add")); 
			addButton.setName(ADD_BUTTON_COMPONENT_NAME);
			addButton.setActionCommand(ADD_COMMAND);
			addButton.addActionListener(this);
		}
		return addButton;
	}
	
	private JButton getEditButton() {
		if (editButton == null) {
			editButton = new JButton();
			editButton.setText(Messages.getString("BrowsePanel.edit")); 
			editButton.setName(EDIT_BUTTON_COMPONENT_NAME);
			editButton.setActionCommand(EDIT_COMMAND);
			editButton.addActionListener(this);
		}
		return editButton;
	}
	
	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText(Messages.getString("BrowsePanel.delete")); 
			deleteButton.setName(DELETE_BUTTON_COMPONENT_NAME);
			deleteButton.setActionCommand(DELETE_COMMAND);
			deleteButton.addActionListener(this);
		}
		return deleteButton;
	}
	
	private JButton getDetailsButton() {
		if (detailButton == null) {
			detailButton = new JButton();
			detailButton.setText(Messages.getString("BrowsePanel.details")); 
			detailButton.setName(DETAIL_BUTTON_COMPONENT_NAME);
			detailButton.setActionCommand(DETAIL_COMMAND);
			detailButton.addActionListener(this);
		}
		return detailButton;
	}

	private JScrollPane getTablePanel() {
		if (tablePanel == null) {
			tablePanel = new JScrollPane(getUserTable());
		}
		return tablePanel;
	}
	
	private JTable getUserTable() {
		if (userTable == null) {
			userTable = new JTable();
			userTable.setName(USER_TABLE_COMPONENT_NAME);
		}
		return userTable;
	}
	
	public void initTable() {
		UserTableModel model;
		try {
			model = new UserTableModel(parent.getUserDao().findAll());
		} catch (DatabaseExeption e) {
			model = new UserTableModel(new ArrayList<>());
			JOptionPane.showMessageDialog(this, e.getMessage(), ERROR_MESSAGE, 
					JOptionPane.ERROR_MESSAGE);
		}
		getUserTable().setModel(model);
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (ADD_COMMAND.equalsIgnoreCase(actionCommand)) { 
			this.setVisible(false);
			parent.showAddPanel();
		}
		else if (EDIT_COMMAND.equalsIgnoreCase(actionCommand)) { 
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, Messages.getString("BrowsePanel.select_user"), 
                        Messages.getString("BrowsePanel.editing"), JOptionPane.INFORMATION_MESSAGE); 
                return;
            }
            User user = ((UserTableModel) userTable.getModel())
                    .getUser(selectedRow);
            this.setVisible(false);
            parent.showEditPanel(user);
		}
		else if (DETAIL_COMMAND.equalsIgnoreCase(actionCommand)) { 
			int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, Messages.getString("BrowsePanel.select_user2"), 
                        Messages.getString("BrowsePanel.detail"), JOptionPane.INFORMATION_MESSAGE); 
                return;
            }
			User user = ((UserTableModel) userTable.getModel())
                    .getUser(selectedRow);
			this.setVisible(false);
			parent.showDetailsPanel(user);
		}
		else if (DELETE_COMMAND.equalsIgnoreCase(actionCommand)) { 
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, Messages.getString("BrowsePanel.select_user3"), 
                        Messages.getString("BrowsePanel.delet"), JOptionPane.INFORMATION_MESSAGE); 
                return;
            }
            try {
            	if(JOptionPane.showConfirmDialog(this, Messages.getString("BrowsePanel.are_you_sure"), Messages.getString("BrowsePanel.confirm"),  
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            		parent.getUserDao().delete(
                            ((UserTableModel) userTable.getModel())
                            		.getUser(selectedRow));	
            	};
            } catch (DatabaseExeption e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), ERROR_MESSAGE,
                        JOptionPane.ERROR_MESSAGE);
            }
            initTable();
            return; 
        }	
	}

}
