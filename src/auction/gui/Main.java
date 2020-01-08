package auction.gui;

import auction.catalog.Auction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("auctionView2.fxml"));
        new AuctionController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Auction");
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
