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

    public String toEmoji() {
        return switch (this) {
            case fungi -> "🍄";
            case animal -> "🐺";
            case plant -> "🌿";
            case insect -> "🦋";
            case scroll -> "📜";
            case inkPot -> "🧴";
            case feather -> "𓆰";
            case none -> " ";
            default -> "";
        };
    }
}
