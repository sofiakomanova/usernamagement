package main.java.ua.nure.kn.komanova.usermanagement.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import java.main.ua.nure.kn.komanova.usermanagement.User;
import java.main.ua.nure.kn.komanova.usermanagement.util.Messages;

public class UserTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 6474493935877622369L;
	
	private static final String[] COLUMN_NAMES = {"ID", Messages.getString("UserTableModel.first_name"), Messages.getString("UserTableModel.last_name")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static final Class<?>[] COLUMN_CLASSES = {Long.class, String.class, String.class} ;
	private List<User> users = null;
	
	public UserTableModel(Collection<User> users) {
		this.users = new ArrayList<>(users);
	}

	public int getColumnCount() {
		
		return COLUMN_NAMES.length;
	}

	public int getRowCount() {
		
		return users.size();
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		
		return COLUMN_CLASSES[columnIndex];
	}

	public String getColumnName(int column) {
		
		return COLUMN_NAMES[column];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = (User) users.get(rowIndex);
		if (columnIndex == 0) {
			return user.getId();
		}
		else if (columnIndex == 1) {
			return user.getFirstName();
		}
		else if (columnIndex == 2) {
			return user.getLastName();
		}
		else {
			return null;
		}
	}
	
	public User getUser(int index) {
        return (User) users.get(index);
    }

    public void addUsers(Collection<User> users) {
        this.users.addAll(users);        
    }

    public void clearUsers() {
        this.users = new ArrayList<>();
    }
}
