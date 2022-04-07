package controler;

import java.net.URL;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundCtrl {

	private MediaPlayer mediaPlayer;

	public SoundCtrl(String pathSound) {
		
		URL musicURL = getClass().getResource(pathSound);
		this.mediaPlayer = new MediaPlayer(new Media(musicURL.toExternalForm()));
	}

	public void play() {	
		mediaPlayer.play();
	}

	public void stop() {
		mediaPlayer.stop();
	}

	public void pause() {
		mediaPlayer.pause();
	}

	public void fadeStop() {
		new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(mediaPlayer.volumeProperty(), 0))).play();
	}
	
}
