package it.skybridge.nxtor2.utils;

public enum Type {

	SOLO,
	DUO,
	TRIPLO,
	QUADRUPLO;
	
	public static Type getTypeFromString(String string) {
		if(string.equalsIgnoreCase("solo")) {
			return Type.SOLO;
		} else if(string.equalsIgnoreCase("duo")) {
			return Type.DUO;
		} else if(string.equalsIgnoreCase("triplo")) {
			return Type.TRIPLO;
		} else if(string.equalsIgnoreCase("quadruplo")) {
			return Type.QUADRUPLO;
		}
		return Type.SOLO;
	}
}
