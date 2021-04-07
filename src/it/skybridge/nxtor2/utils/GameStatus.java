package it.skybridge.nxtor2.utils;

public enum GameStatus {

	WAITING,
	PLAYING,
	FINISHING;
	
	public static GameStatus getStatusFromString(String s) {
		switch(s) {
		case "waiting":
			return WAITING;
		case "playing":
			return PLAYING;
		case "finishing":
			return FINISHING;
		default:
			return WAITING;
		}
	}
}
