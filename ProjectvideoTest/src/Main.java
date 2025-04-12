/**import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
package Video;
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        // ✅ Ton chemin vers la vidéo locale
        String chemin = "C:/Users/super/Documents/ECE/ING3/international/Embedded System/projet/Video FInal.mp4";

        File fichier = new File(chemin);
        if (!fichier.exists()) {
            System.out.println("❌ Fichier introuvable !");
            return;
        }

        // 🎥 Initialisation
        Media media = new Media(fichier.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);

        // 📏 Forcer taille du MediaView à s'adapter à la scène
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(800);
        mediaView.setFitHeight(600);

        // 🎮 Boutons de contrôle
        Button playPauseBtn = new Button("⏯ Play");
        Button stopBtn = new Button("⏹ Stop");
        Button rewindBtn = new Button("⏪ -10s");
        Button forwardBtn = new Button("⏩ +10s");

        // ▶️ Play / Pause
        playPauseBtn.setOnAction(e -> {
            MediaPlayer.Status status = player.getStatus();
            if (status == MediaPlayer.Status.PLAYING) {
                player.pause();
                playPauseBtn.setText("▶ Play");
            } else {
                player.play();
                playPauseBtn.setText("⏸ Pause");
            }
        });

        // ⏹ Stop
        stopBtn.setOnAction(e -> {
            player.stop();
            playPauseBtn.setText("▶ Play");
        });

        // ⏪ Reculer de 10 sec
        rewindBtn.setOnAction(e -> {
            player.seek(player.getCurrentTime().subtract(javafx.util.Duration.seconds(10)));
        });

        // ⏩ Avancer de 10 sec
        forwardBtn.setOnAction(e -> {
            player.seek(player.getCurrentTime().add(javafx.util.Duration.seconds(10)));
        });

        // 🎛 Layout des boutons
        HBox controls = new HBox(10, rewindBtn, playPauseBtn, forwardBtn, stopBtn);
        controls.setPadding(new Insets(15));
        controls.setAlignment(Pos.CENTER);
        controls.setStyle("-fx-background-color: #333333;");

        // 🧱 Layout général
        BorderPane root = new BorderPane();
        root.setCenter(mediaView);
        root.setBottom(controls);
        root.setStyle("-fx-background-color: black;");

        // 🎬 Scène
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("🎞 JavaFX Lecteur vidéo local");
        stage.setScene(scene);
        stage.show();

        // ▶️ Lecture auto au lancement
        player.play();
        playPauseBtn.setText("⏸ Pause");
    }

    public static void main(String[] args) {
        launch();
    }
}
*/