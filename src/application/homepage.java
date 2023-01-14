package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class homepage implements Initializable{

    @FXML
    private PieChart expenditure;
    
    @FXML
    private Button profile;
    
    @FXML
    private LineChart<String,Number> daily;
    
    @FXML
    private LineChart<String,Number> monthly;
    
    @FXML
    private Label category;
    
    @FXML
    private Label username;
    
    @FXML
    private Label currbal;
    
    @FXML
    private Label expenses;
    
    @FXML
    private Button manage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	regex_applications.readmessage();
    	DBUtils.expenses();
    	DBUtils.balandexp();
    	DBUtils.daily_graph();
    	String te = Double.toString(DBUtils.total_expense);
    	String bal = Double.toString(DBUtils.total_balance);
    	username.setText(DBUtils.username);
    	expenses.setText(te);
    	currbal.setText(bal);
    	double food = DBUtils.food/DBUtils.total_expense;
    	double entertainment = DBUtils.ent/DBUtils.total_expense;
    	double transport = DBUtils.tran/DBUtils.total_expense;
    	double shopping = DBUtils.shop/DBUtils.total_expense;
    	double miscellaneous = DBUtils.misc/DBUtils.total_expense;
    	ObservableList<PieChart.Data> piechartdata = FXCollections.observableArrayList(
    			new PieChart.Data("Food", food),
    			new PieChart.Data("Entertainment", entertainment),
    			new PieChart.Data("Transport", transport),
    			new PieChart.Data("Shopping", shopping),
    			new PieChart.Data("Miscellaneous", miscellaneous)
    			);
    	expenditure.setData(piechartdata);
    	
    	for(final PieChart.Data data : expenditure.getData()) {
    		data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, 
    				new EventHandler<MouseEvent>() {
    					public void handle(MouseEvent e) {
    						category.setText(String.valueOf(data.getName()) + " : " + String.valueOf(data.getPieValue()*DBUtils.total_expense)+"Rs");
    					}
    			});
    		}
    	XYChart.Series<String,Number> series1 =  new XYChart.Series<>();
    	series1.setName("Expenses daily");
    	try {
			while(DBUtils.daily_details.next()) {
				series1.getData().add(new XYChart.Data<String,Number>(DBUtils.daily_details.getString(1),DBUtils.daily_details.getDouble(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	daily.getData().add(series1);
    	
    	XYChart.Series<String,Number> series2 =  new XYChart.Series<>();
    	series2.setName("Expenses monthly");
    	
    	series2.getData().add(new XYChart.Data<String,Number>("January",2000));
    	series2.getData().add(new XYChart.Data<String,Number>("February",3000));
    	series2.getData().add(new XYChart.Data<String,Number>("March",900));
    	series2.getData().add(new XYChart.Data<String,Number>("April",5000));
    	
    	monthly.getData().add(series2);
    	
    	
    	
    			
    }

    @FXML
    void userProfile(ActionEvent event) throws Exception {
    	Main m = new Main();
    	m.changeScene("profile.fxml");
    }
    
    @FXML
    void manageExpenses(ActionEvent event) throws Exception {
    	Main m =new Main();
    	m.changeScene("Expenditures.fxml");
    }
    

}

