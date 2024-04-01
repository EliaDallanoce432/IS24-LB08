package it.polimi.ingsw.util.supportclasses;

public enum Resource {
    fungi, animal, plant, insect, scroll, inkPot, feather, none;

    public static Resource StringToResource(String str)
    {
        if(str.equals("fungi"))
            return Resource.fungi;
        else if (str.equals("animal"))
            return Resource.animal;
        else if (str.equals("plant"))
            return Resource.plant;
        else if (str.equals("insect"))
            return Resource.insect;
        else if (str.equals("scroll"))
            return Resource.scroll;
        else if (str.equals("inkpot"))
            return Resource.inkPot;
        else if (str.equals("feather"))
            return Resource.feather;
        else
        return Resource.none;
    }
}
