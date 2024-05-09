package org.example.common;

public enum Color {
	BLACK("\u001B[0m"),
	ORANGE("\u001B[38;5;208m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[38;5;11m"),
	MAGENTA("\u001B[38;5;201m");

	private final String encodedColor;

	Color(String encodedColor) {
		this.encodedColor = encodedColor;
	}

	public String encodedColor() {
		return encodedColor;
	}
}
