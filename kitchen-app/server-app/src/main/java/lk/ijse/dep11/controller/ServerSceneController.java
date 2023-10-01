package lk.ijse.dep11.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.Orders;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSceneController {
    public AnchorPane root;
    public TableView<Orders> tblOrders;
    public Button btnCloseOrder;

    public void initialize() {
        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("burgerQty"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("subQty"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("hotDohQty"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("cokeQty"));

//        new Thread(() -> startServer()).start();
        new Thread(this::startServer).start();

    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(5050);
            while (true){
                System.out.println("Waiting for client connection");
                Socket localSocket = serverSocket.accept();
                System.out.println("client connected: " + localSocket);
                new Thread(()->{
                    try {
                        InputStream is = localSocket.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        ObjectInputStream ois = new ObjectInputStream(bis);
                        while (true) {
                            Orders orders = (Orders) ois.readObject();
                            Platform.runLater(() -> {tblOrders.getItems().add(orders);});
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btnCloseOrderOnAction(ActionEvent actionEvent) {
    }
}
