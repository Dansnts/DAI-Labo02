package ch.heig.dai;

/**
 * Represents different types of elements and their associated weaknesses.
 * Provides methods to manage element types and their relationships.
 */
enum Type {
    GRASS,
    FIRE,
    WATER,
    NORMAL,
    NONE
}

/**
 * Handles element types, their weaknesses, and utility methods related to elements.
 */
public class Elements {
    private final Type type;        // The type of this element
    private final Type weakness;    // The weakness associated with this element

    /**
     * A constant array of all types for reference and display.
     */
    public static final String[] TYPES = {"GRASS", "FIRE", "WATER", "NORMAL", "NONE"};

    /**
     * Constructs an Elements object based on the provided type.
     *
     * @param type The type of the element as a string.
     */
    public Elements(String type) {
        this.type = getType(type);
        this.weakness = getWeakness(this.type);
    }

    /**
     * Retrieves the weakness for a given type.
     *
     * @param type The type for which the weakness is determined.
     * @return The weakness type, or NONE if no specific weakness exists.
     */
    public Type getWeakness(Type type) {
        return switch (type) {
            case GRASS -> Type.FIRE;
            case FIRE -> Type.WATER;
            case WATER -> Type.GRASS;
            case NORMAL -> Type.NONE;
            default -> Type.NONE;
        };
    }

    /**
     * Converts the type of this element to a string representation.
     *
     * @return The string representation of the type.
     */
    public String typeToString() {
        return TYPES[this.type.ordinal()];
    }

    /**
     * Converts a string to its corresponding Type enum value.
     * If the string does not match any type, returns NONE.
     *
     * @param type The string representation of the type.
     * @return The corresponding Type enum value.
     */
    public Type getType(String type) {
        return switch (type.toUpperCase()) {
            case "GRASS" -> Type.GRASS;
            case "WATER" -> Type.WATER;
            case "FIRE" -> Type.FIRE;
            case "NORMAL" -> Type.NORMAL;
            default -> Type.NONE;
        };
    }

    /**
     * Prints all available move types to the console.
     */
    public void printMoves() {
        for (int i = 0; i < TYPES.length; i++) {
            System.out.print(TYPES[i]);
            if (i != TYPES.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(); // Move to a new line after printing all types
    }
}
