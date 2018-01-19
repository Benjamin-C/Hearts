package pile;

import java.awt.Color;

public enum Suit {
    HEART("Hearts", 0, Color.RED, "red"),
	SPADE("Spades", 1, Color.BLACK, "black"),
	DIAMOND("Diamonds", 2, Color.RED, "red"),
	CLUB("Clubs", 3, Color.BLACK, "black");

    private String name;
    private int id;
    private Color color;
    private String colorName;

    Suit(String name, int id, Color color, String colorName) {
        this.name = name;
        this.color = color;
        this.id = id;
        this.colorName = colorName;
    }

    public String getName() {
        return name;
    }
        
    public int getID() {
        return id;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String getColorName() {
    	return colorName;
    }
}
