/*import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class YoutubeViewer extends Application {
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();

        // URL de la vidéo YouTube (en embed)
        String videoHTML = "<iframe width='560' height='315' src='https://www.youtube.com/watch?v=Aym2Tb3w5h4' " +
                "frameborder='0' allowfullscreen></iframe>";

        webView.getEngine().loadContent(videoHTML, "text/html");

        primaryStage.setScene(new Scene(webView, 600, 400));
        primaryStage.setTitle("Vidéo YouTube dans JavaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/