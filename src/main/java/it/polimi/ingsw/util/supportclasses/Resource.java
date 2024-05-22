package it.polimi.ingsw.util.supportclasses;

public enum Resource {
    fungi, animal, plant, insect, scroll, inkPot, feather, none;

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

    public String toSymbol() {
        return switch (this) {
            case fungi -> "F";
            case animal -> "A";
            case plant -> "P";
            case insect -> "I";
            case scroll -> "s";
            case inkPot -> "i";
            case feather -> "f";
            case none -> " ";
            default -> "";
        };
    }

//    public String toEmoji() {
//        return switch (this) {
//            case fungi -> Character.toString(0x1F344);
//            case animal -> Character.toString(0x1F43A);
//            case plant -> Character.toString(0x1F43A);
//            case insect -> Character.toString(0x1F43A);
//            case scroll -> Character.toString(0x1F43A);
//            case inkPot -> Character.toString(0x1F43A);
//            case feather -> Character.toString(0x1F43A);
//            case none -> " ";
//            default -> "";
//        };
//    }

}
