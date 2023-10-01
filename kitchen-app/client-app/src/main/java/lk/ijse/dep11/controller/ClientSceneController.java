package lk.ijse.dep11.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.Customers;
import lk.ijse.dep11.Orders;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSceneController {
    public AnchorPane root;
    public TableView<Customers> tblCustomerDetail;
    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public Button btnNew;
    public Spinner<Integer> spnBurger;
    public Spinner<Integer> spnSubmarine;
    public Spinner<Integer> spnHotDog;
    public Spinner<Integer> spnCoke;
    public Button btnPlaceOrder;
    private ObjectOutputStream oos;

    public ArrayList<Customers> newArrayList = new ArrayList<>();
    public int count = 0;

    public void initialize() {
        spnBurger.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0));
        spnSubmarine.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spnHotDog.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spnCoke.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));

        tblCustomerDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomerDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomerDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCustomerDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            Socket remoteSocket = new Socket("localhost", 5050);
            OutputStream os = remoteSocket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            oos = new ObjectOutputStream(bos);
            System.out.println("connected!...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Customers> observableList = FXCollections.observableList(newArrayList);
        tblCustomerDetail.setItems(observableList);

    }
    public void btnNewOnAction(ActionEvent actionEvent) {
        txtId.setText(getCustomerId());
    }

    private String getCustomerId() {
        if(newArrayList.isEmpty()) return "C-001";
        else {
            int number = Integer.parseInt(getCustomers().get(getCustomers().size() - 1).getId().substring(2)) + 1;
            return String.format("C-%03d", number);
        }
    }

    public void btnPlaceOrder(ActionEvent actionEvent) {

        List<Customers> customersList = getCustomers();
        Customers customer = new Customers(txtId.getText().strip(), txtName.getText().strip(), txtContact.getText().strip(), "Pending");
        customersList.add(customer);
        count++;


        var orders = new Orders(txtId.getText().strip(), spnBurger.getValue(), spnSubmarine.getValue(), spnHotDog.getValue(), spnCoke.getValue());
        try {
            oos.writeObject(orders);
            oos.flush();
            spnBurger.getValueFactory().setValue(0);
            spnSubmarine.getValueFactory().setValue(0);
            spnHotDog.getValueFactory().setValue(0);
            spnCoke.getValueFactory().setValue(0);
            txtName.clear();
            txtContact.clear();
            btnNew.fire();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Customers> getCustomers() {
        return tblCustomerDetail.getItems();
    }
}
