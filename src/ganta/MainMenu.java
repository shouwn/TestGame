package ganta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainMenu implements ActionListener {
	public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd == "모름") {
            System.exit(0);
        }
    }
}


