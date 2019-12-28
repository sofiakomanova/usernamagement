package main.java.ua.nure.kn.komanova.usermanagement.agent;

import jade.core.behaviours.Behaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour {
	private static final long serialVersionUID = -737157927210121653L;
	
	private AID[] aids;
	private String firstName;
	private String lastName;

	public SearchRequestBehaviour(AID[] aids, String firstName, String lastName) {
		this.aids = aids;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.setContent(firstName + ", " + lastName);
		if (aids != null) {
			for (int i = 0; i < aids.length; i++) {
				message.addReceiver(aids[i]);
			}
			myAgent.send(message);
		}
	}

	public boolean done() {
		return true;
	}
}