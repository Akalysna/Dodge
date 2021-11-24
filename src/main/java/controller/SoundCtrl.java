package controller;

import java.net.URL;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundCtrl {

	public static final String HOVER_BTN = "/sounds/Hover_btn.wav";
	public static final String HOME = "/sounds/Dance.mp3";

	private MediaPlayer musique;
	private MediaPlayer soundHoverBtn;

	public SoundCtrl() {

		URL musicURL = getClass().getResource(HOME);
		Media media = new Media(musicURL.toExternalForm());
		this.musique = new MediaPlayer(media);

		musicURL = getClass().getResource(HOVER_BTN);
		media = new Media(musicURL.toExternalForm());
		this.soundHoverBtn = new MediaPlayer(media);
		this.soundHoverBtn.setVolume(5);
	}

	public void play(String s) {

		URL musicURL = getClass().getResource(s);
		musique = new MediaPlayer(new Media(musicURL.toExternalForm()));
		musique.play();
		this.musique.setVolume(0.6);
	}

	public void stop() {
		musique.stop();
	}

	public void pause() {
		musique.pause();
	}

	public void playSound(String s) {
		URL musicURL = getClass().getResource(s);
		this.soundHoverBtn = new MediaPlayer(new Media(musicURL.toExternalForm()));
		this.soundHoverBtn.play();
		this.soundHoverBtn.setVolume(0.3);
	}

	public void stopSound() {
		soundHoverBtn.stop();
	}

	public void pauseSound() {
		soundHoverBtn.pause();
	}

	public void fadeStop() {
		new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(musique.volumeProperty(), 0))).play();
	}
	
	public void fadeStopSound() {
		new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(soundHoverBtn.volumeProperty(), 0))).play();
	}
}
