package dev.astamur.geekbrains.lessons.lesson7.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @FXML
    TextField msgField, loginField, nickField;

    @FXML
    HBox msgPanel, authPanel;

    @FXML
    PasswordField passField;

    @FXML
    ListView<String> clientsList;

    private Network network;

    private boolean authenticated;
    private String nickname;
    private String login;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        authPanel.setVisible(!authenticated);
        authPanel.setManaged(!authenticated);
        msgPanel.setVisible(authenticated);
        msgPanel.setManaged(authenticated);
        clientsList.setVisible(authenticated);
        clientsList.setManaged(authenticated);
        if (!authenticated) {
            nickname = "";
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthenticated(false);
        clientsList.setOnMouseClicked(this::clientClickHandler);
        createNetwork();
        network.connect();


    }

    public void sendAuth() {
        network.sendAuth(loginField.getText(), passField.getText(), nickField.getText());
        login = loginField.getText();
        loginField.clear();
        passField.clear();
        nickField.clear();
    }

    public void sendMsg() {
        if (network.sendMsg(msgField.getText())) {
            msgField.clear();
            msgField.requestFocus();
        }
    }

    public void sendExit() {
        network.sendMsg("/end");
    }

    public void showAlert(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
            alert.showAndWait();
        });
    }

    public void createNetwork() {
        network = new Network();
        network.setCallOnException(args -> showAlert(args[0].toString()));

        network.setCallOnCloseConnection(args -> setAuthenticated(false));

        network.setCallOnAuthenticated(args -> {
            setAuthenticated(true);
            nickname = args[0].toString();
            textArea.clear();
            try{
                readFromFile(login);
            }catch (IOException ea){
                ea.printStackTrace();
            }
        });

        network.setCallOnMsgReceived(args -> {
            String msg = args[0].toString();
            if (msg.startsWith("/clients ")) {
                String[] tokens = msg.split("\\s");
                Platform.runLater(() -> {
                    clientsList.getItems().clear();
                    for (int i = 1; i < tokens.length; i++) {
                        if (!nickname.equals(tokens[i])) {
                            clientsList.getItems().add(tokens[i]);
                        }
                    }
                });
//
            } else {
                textArea.appendText(msg + "\n");
                try{
                    writeToFile(msg + "\n");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void writeToFile(String str) throws IOException{
        File f = new File("history_" + login + ".txt");
        try(FileWriter fw = new FileWriter(f, true)){
            fw.write(str);
        }
    }
    private void readFromFile(String login) throws IOException{
        textArea.clear();
        File f = new File("history_" + login + ".txt");
        if(f.exists()){
            int a = 0;
            List<String> listMessage = new ArrayList<>();
            RandomAccessFile raf = new RandomAccessFile(f,"r");
            String l;
            while ((l = raf.readLine()) != null){
                listMessage.add(l);
                a++;
            }
            if (a < 100){
                a = listMessage.size();
            }
            for(int i = listMessage.size()-1; i >= (listMessage.size() - a); i--){
                String str = (listMessage.get(i) + "\n");
                String utf8 = new String(str.getBytes("ISO-8859-1"), "UTF-8");
                textArea.appendText(utf8);
            }
            raf.close();
        }

    }


    private void clientClickHandler(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String nickname = clientsList.getSelectionModel().getSelectedItem();
            msgField.setText("/w " + nickname + " ");
            msgField.requestFocus();
            msgField.selectEnd();
        }
    }
}