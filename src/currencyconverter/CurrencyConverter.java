/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currencyconverter;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 * @author doug_na
 */
public class CurrencyConverter extends Application {
    static OkHttpClient client;
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        FlowPane pane = new FlowPane();
        //creates spacing between fields horizontally
        pane.setHgap(2);
        
        ComboBox<Currency> currencies = new ComboBox<>();
        currencies.getItems().addAll(loadData().currencies);
        
        //creating text fields for currencyTo, amount, and result
        TextField tfAmount = new TextField();
        Label tfTotal = new Label();
       
        VBox labelBoxes = new VBox(6); 
        labelBoxes.setSpacing(5);
        labelBoxes.getChildren().addAll(new Label("Convert USD to: "), currencies, new Label("Amount to convert: "), tfAmount, new Label("Conversion total: "), tfTotal);
        
        
        pane.getChildren().addAll(labelBoxes);     
        Button btResult = new Button("Result");
        btResult.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Currency selectedItem = currencies.getValue();
                double amount = Double.parseDouble(tfAmount.getText());
                double result = 0.0;
                try {
                    result = createConversion(amount,selectedItem.getId());
                } catch (IOException ex) {
                    Logger.getLogger(CurrencyConverter.class.getName()).log(Level.SEVERE, null, ex);
                }
                tfTotal.setText(String.valueOf(result));
            }
        });
        
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(btResult);
        
        Scene scene = new Scene(borderPane, 350, 200);
        
        primaryStage.setTitle("USD Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
       
    private static Root loadData() throws IOException{
        Gson gson = new Gson();
        String content = new String(Files.readAllBytes(Paths.get("/Users/doug_na/Downloads/data.json")));           
        Root root = gson.fromJson(content, Root.class);
        return root;
    }
    
    private static double createConversion(double value, String currencyTo) throws IOException{
        client = new OkHttpClient();
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url("http://api.currencylayer.com/live?access_key=d34401544a5278321d27498427a30bb5" + "&currencies=" + currencyTo + "&format=1")
                .get()
                .build();
        String response = client.newCall(request).execute().body().string();
        ResponseRoot root = gson.fromJson(response, ResponseRoot.class);
        for(String s: root.quotes.keySet()){
            System.out.println(s.toString());
        }
        double conversionVal = (double) root.quotes.get("USD"+currencyTo);
        double result = value * conversionVal;
        return result;
    }
}
