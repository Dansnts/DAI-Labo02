package ch.heig.dai;

/**
 * Represents a move that a character can use in battle.
 * Includes properties such as power, precision, type, and more.
 */
public class Move {
    private final String name;        // Name of the move
    private final int pp;             // Maximum number of uses (Power Points)
    private final int precision;      // Precision of the move (0-100)
    private final Type type;          // Type of the move
    private final int power;          // Power of the move
    private final boolean spe;        // Whether the move is special or physical
    private int actualPp;             // Remaining Power Points

    /**
     * Constructor for the Move class.
     *
     * @param name       Name of the move.
     * @param pp         Maximum Power Points (uses).
     * @param precision  Precision of the move (0-100).
     * @param type       Type of the move.
     * @param power      Power of the move.
     * @param spe        Indicates if the move is special (true) or physical (false).
     */
    public Move(String name, int pp, int precision, Type type, int power, boolean spe) {
        this.name = name;
        this.pp = pp;
        this.precision = precision;
        this.type = type;
        this.actualPp = pp; // Initialize actual PP to the maximum PP
        this.power = power;
        this.spe = spe;
    }

    /**
     * Parses a move from a string data format.
     * This is a placeholder method and needs implementation based on the data format.
     *
     * @param data The string containing the move data.
     * @return A parsed Move object.
     */
    public Move parseMove(String data) {
        System.out.println("MOVES: " + data);
        // Parsing logic can be added here based on the input format.
        return null;
    }

    /**
     * Prints the move details in a formatted string.
     *
     * @return A string representation of the move details.
     */
    public String printMove() {
        return String.format(
                "%s - %s (%dPP - Precision: %d/100 - Power: %d)%n",
                this.name, this.type, this.actualPp, this.precision, this.power
        );
    }

    // Getters

    /**
     * @return The name of the move.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The maximum Power Points of the move.
     */
    public int getPp() {
        return pp;
    }

    /**
     * @return The precision of the move.
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * @return The type of the move.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The remaining Power Points of the move.
     */
    public int getActualPp() {
        return actualPp;
    }

    /**
     * @return The power of the move.
     */
    public int getPower() {
        return power;
    }

    /**
     * @return True if the move is special, false if it is physical.
     */
    public boolean isSpe() {
        return spe;
    }

    // Setters

    /**
     * Decreases the remaining Power Points by 1.
     * Ensures that actual PP does not go below 0.
     */
    public void setActualPp() {
        if (this.actualPp > 0) {
            this.actualPp--;
        }
    }
}
