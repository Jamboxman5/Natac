package me.jamboxman5.natac.cards;

import com.badlogic.gdx.utils.Array;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.PlayerClass;

public interface Card {

    void playCard(Player playing, Map currentMap);

    static Card pullCard(PlayerClass pClass) {

        //This static method can be called from anywhere to generate a deck of cards as specified below
        //The only current required parameter is the class of the drawing player to restrict class based cards

        //Create the deck
        Array<Card> cards = new Array<>();

        //Cards that can be pulled by anyone
        cards.add(new AcademiaCard(), new AcademiaCard());
        cards.add(new AdoredbyAllCard(), new AdoredbyAllCard(), new AdoredbyAllCard());
        cards.add(new BountifulHarvestCard(), new BountifulHarvestCard(), new BountifulHarvestCard(), new BountifulHarvestCard());
        cards.add(new CityGuardCard(), new CityGuardCard(), new CityGuardCard(), new CityGuardCard());
        cards.add(new FoolsGoldCard(), new FoolsGoldCard());
        cards.add(new GoldRushCard(), new GoldRushCard(), new GoldRushCard());
        cards.add(new LibraryOfAlexandriaCard(), new LibraryOfAlexandriaCard());
        cards.add(new PacifismCard(), new PacifismCard());
        cards.add(new PeaceTalksCard(), new PeaceTalksCard());
        cards.add(new PlagueCard(), new PlagueCard());
        cards.add(new SuperiorSoldiersCard(), new SuperiorSoldiersCard(), new SuperiorSoldiersCard());
        cards.add(new ViciousRumorsCard(), new ViciousRumorsCard());

        //Example of random chance for card inclusion
        //Math.random() generates a random decimal between 0, 1 - this would be a 50% chance of inclusion in the deck
        //Increase chances of certain cards by restricting others in this way or by adding duplicates to the deck
        //Commented this out, loading the deck with multiple copies instead
        // if (Math.random() > 0.5) cards.add(new AcademiaCard());

        //Cards which are class dependent
        switch(pClass) {
            case BARBARIANS:
                //Barbarians cards

                break;
            case NECROPOLIS:
                //Necropolis cards

                break;
            case STEEL_CITY:
                //Steel City cards
                cards.add(new AllSeeingEyeCard(), new AllSeeingEyeCard(), new AllSeeingEyeCard());
                break;
            case GOLDEN_KEEP:
                //Golden Keep cards
                cards.add(new CorruptPoliticsCard(), new CorruptPoliticsCard(), new CorruptPoliticsCard());
                break;
            case HOLY_EMPIRE:
                //Holy Empire cards
                cards.add(new DivineFavorCard(), new CorruptPoliticsCard(), new CorruptPoliticsCard());
                cards.add(new OfferingPlateCard(), new OfferingPlateCard());
                break;
            case MOLE_PEOPLE:
                //Mole People cards
                cards.add(new UndergroundTunnelsCard(), new UndergroundTunnelsCard(), new UndergroundTunnelsCard());
                cards.add(new MechaMolesCard());
                break;
        }

        //Return a random card from the deck
        return cards.random();

    }

}
