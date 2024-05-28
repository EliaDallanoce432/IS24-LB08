package it.polimi.ingsw.util.supportclasses;

/**
 * This class contains the representation of the resources.
 */
public enum Resource {
    fungi, animal, plant, insect, scroll, inkPot, feather, none;

    /**
     * Converts a string to the corresponding resource.
     * @param str The string to convert.
     * @return The resource.
     */
    public static Resource StringToResource(String str)
    {
        return switch (str) {
            case "fungi" -> Resource.fungi;
            case "animal" -> Resource.animal;
            case "plant" -> Resource.plant;
            case "insect" -> Resource.insect;
            case "scroll" -> Resource.scroll;
            case "inkpot" -> Resource.inkPot;
            case "feather" -> Resource.feather;
            default -> Resource.none;
        };
    }

    /**
     * Converts the resource in the relative colored symbol representation for the CLI.
     * @return the symbol to be printed
     */
    public String toSymbol() {
        return switch (this) {
            case fungi -> ConsoleColor.RED +  "F" + ConsoleColor.RESET;
            case animal -> ConsoleColor.CYAN + "A" + ConsoleColor.RESET;
            case plant -> ConsoleColor.GREEN + "P" + ConsoleColor.RESET;
            case insect -> ConsoleColor.PURPLE +  "I" + ConsoleColor.RESET;
            case scroll -> ConsoleColor.YELLOW + "s" + ConsoleColor.RESET;
            case inkPot -> ConsoleColor.YELLOW  + "i" + ConsoleColor.RESET;
            case feather -> ConsoleColor.YELLOW  + "f" + ConsoleColor.RESET;
            case none -> " ";
        };
    }

    /**
     * Converts the resource in the relative color.
     * @return The ANSI code of the corresponding color of the resource.
     */
    public String toColor(){
        return switch (this) {
            case fungi -> ConsoleColor.RED;
            case animal -> ConsoleColor.CYAN;
            case plant -> ConsoleColor.GREEN;
            case insect -> ConsoleColor.PURPLE;
            case scroll, inkPot, feather -> ConsoleColor.YELLOW;
            case none -> ConsoleColor.RESET;
        };
    }

}
