package lk.ijse.dep11.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.Customers;


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

    public void initialize() {
        spnBurger.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0));
        spnSubmarine.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spnHotDog.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spnCoke.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));

        tblCustomerDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomerDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomerDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCustomerDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    public void btnNewOnAction(ActionEvent actionEvent) {
        txtId.setText(getCustomerId());
    }

    private String getCustomerId() {
        if(getCustomers().isEmpty()) return "C-001";
        else {
            int number = Integer.parseInt(getCustomers().get(getCustomers().size() - 1).getId().substring(2)) + 1;
            return String.format("C-%03d", number);
        }
    }

    public void btnPlaceOrder(ActionEvent actionEvent) {

        List<Customers> customersList = getCustomers();
        Customers customer = new Customers(txtId.getText().strip(), txtName.getText().strip(), txtContact.getText().strip(), "Pending");
        customersList.add(customer);

        txtName.clear();
        txtContact.clear();
        btnNew.fire();

    }

    private List<Customers> getCustomers() {
        return tblCustomerDetail.getItems();
    }
}
