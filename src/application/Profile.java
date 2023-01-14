package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Profile implements Initializable {


    @FXML
    private Label email;

    @FXML
    private Label f_name;

    @FXML
    private Label l_name;

    @FXML
    private Button logout;

    @FXML
    private Label username;
    
    @FXML
    private Button requestreport;
    
    @FXML
    private Button home;
    
    @FXML
    void userhome(ActionEvent event) throws Exception{
    	Main m = new Main();
    	m.changeScene("Homepage.fxml");
    }

    @FXML
    void userLogout(ActionEvent event) throws Exception{
    	Main m = new Main();
    	m.changeScene("loginpage.fxml");
    }
    
    @FXML
    void sendReport(ActionEvent event) throws Exception{
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setContentText("Monthly Report Sent Successfully!");
    	alert.show();
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		String fname = DBUtils.f_name;
		String lname = DBUtils.l_name;
		String e_mail = DBUtils.email;
		String user_name = DBUtils.username;
		f_name.setText(fname);
		l_name.setText(lname);
		email.setText(e_mail);
		username.setText(user_name);
	}

}
