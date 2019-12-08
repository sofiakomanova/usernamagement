package main.java.ua.nure.kn.komanova.usermanagement.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.main.ua.nure.kn.komanova.usermanagement.User;
import java.main.ua.nure.kn.komanova.usermanagement.db.DatabaseExeption;
import java.main.ua.nure.kn.komanova.usermanagement.util.Messages;

public class AddPanel extends JPanel implements ActionListener {

private static final long serialVersionUID = -9065973084980412698L;
	
	private static final String ERROR_MESSAGE = "Error";
	private static final String ADD_PANEL_COMPONENT_NAME = "addPanel"; //$NON-NLS-1$
	private static final String OK_BUTTON_COMPONENT_NAME = "okButton"; //$NON-NLS-1$
	private static final String OK_COMMAND = "ok"; //$NON-NLS-1$
	private static final String CANCEL_BUTTON_COMPONENT_NAME = "cancelButton"; //$NON-NLS-1$
	private static final String CANCEL_COMMAND = "cancel"; //$NON-NLS-1$
	
	private static final String DATE_OF_BIRTH_FIELD_COMPONENT_NAME = "dateOfBirthField"; //$NON-NLS-1$
	private static final String LAST_NAME_FIELD_COMPONENT_NAME = "lastNameField"; //$NON-NLS-1$
	private static final String FIRST_NAME_FIELD_COMPONENT_NAME = "firstNameField"; //$NON-NLS-1$
	
	protected MainFrame parent;
	private JPanel buttonPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel fieldPanel;
	private JTextField firstNameField;
	private JTextField dayOfBirthField;
	private JTextField lastNameField;
	private Color bgColor;

	public AddPanel(MainFrame mainFrame) {
		this.parent = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setName(ADD_PANEL_COMPONENT_NAME);	
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);		
	}

	private JPanel getFieldPanel() {
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthField()); //$NON-NLS-1$
		}
		return fieldPanel;
	}

	private void addLabeledField(JPanel panel, String name, JTextField field) {
		JLabel label = new JLabel(name);
		label.setLabelFor(field);
		panel.add(label);
		panel.add(field);
	}

	protected JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName(FIRST_NAME_FIELD_COMPONENT_NAME);
		}
		return firstNameField;
	}
	
	protected JTextField getDateOfBirthField() {
		if(dayOfBirthField == null) {
			dayOfBirthField = new JTextField();
			dayOfBirthField.setName(DATE_OF_BIRTH_FIELD_COMPONENT_NAME);
		}
		return dayOfBirthField;
	}

	protected JTextField getLastNameField() {
		if(lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName(LAST_NAME_FIELD_COMPONENT_NAME);
		}
		return lastNameField;
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
		}
		return buttonPanel;
	}

	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
			cancelButton.setName(CANCEL_BUTTON_COMPONENT_NAME);
			cancelButton.setActionCommand(CANCEL_COMMAND);
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(Messages.getString("AddPanel.ok")); //$NON-NLS-1$
			okButton.setName(OK_BUTTON_COMPONENT_NAME);
			okButton.setActionCommand(OK_COMMAND);
			okButton.addActionListener(this);
		}
		return okButton;
	}
	
	private void clearFields() {
		getFirstNameField().setText(""); //$NON-NLS-1$
		getFirstNameField().setBackground(bgColor);
		
		getLastNameField().setText(""); //$NON-NLS-1$
		getLastNameField().setBackground(bgColor);
		
		getDateOfBirthField().setText(""); //$NON-NLS-1$
		getDateOfBirthField().setBackground(bgColor);
	}
	
    protected void doAction(ActionEvent e) throws ParseException {
        if (OK_COMMAND.equalsIgnoreCase(e.getActionCommand())) {
            User user = new User();
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();
            try {
                Date date = format.parse(getDateOfBirthField().getText());
                user.setDateOfBirth(date);
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getUserDao().create(user);
            } catch (DatabaseExeption e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), ERROR_MESSAGE,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        try {
            doAction(e);
        } catch (ParseException e1) {
            return;
        }
        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();        
    }

}
