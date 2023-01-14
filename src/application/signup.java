package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class signup {

    @FXML
    private Button button;
    
    @FXML
    private Label wronginfo;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private PasswordField repassword;
    
    @FXML
    private TextField f_name;
    
    @FXML
    private TextField l_name;
    
    @FXML
    private TextField email;
    
    @FXML
    private Button login;
    
    @FXML
    private TextField username;

    @FXML
    void signin(ActionEvent event) throws Exception {
    	checkinfo();
    }
    
    @FXML
    void log_in(ActionEvent event) throws Exception{
    	Main m = new Main();
    	m.changeScene("loginpage.fxml");
    }
    
    private void checkinfo() throws Exception{
		int f = 0;
		String uname;
		String pass;
		String e_mail;
		String fname;
		String lname;
		String s1 = password.getText().toString();
		String s2 = repassword.getText().toString();
		if(f_name.getText().isEmpty()&&l_name.getText().isEmpty()&&email.getText().isEmpty()&&password.getText().isEmpty()&&repassword.getText().isEmpty()&&username.getText().isEmpty()) {
			wronginfo.setText("Please enter all the fields!");
			return;
		}
    	uname=username.getText().toString();
    	pass=password.getText().toString();
    	fname=f_name.getText().toString();
    	lname=l_name.getText().toString();
    	e_mail=email.getText().toString();
		if(s1.equals(s2)) {
			f = DBUtils.Enterinfo(fname, lname, uname, pass, e_mail);
			if(f==1) {
				wronginfo.setText("Username already taken!");
				return;
			}	
    	}
    	else {
    		wronginfo.setText("Password fields don't match!");
    	}
    }
}