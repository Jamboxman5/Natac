package me.jamboxman5.natac.screen.ui.elements.scroll;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.cards.Card;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.ui.elements.select.CardPlayerSelector;
import me.jamboxman5.natac.util.Settings;

public class CardScroller extends Scroller {

    private static int margin = 40;

    public CardScroller() {
        super(Align.bottom, Align.bottom, false, new Rectangle(margin, margin, Settings.screenWidth - (margin * 2), Settings.screenHeight / 5f));

        TextButton pullButton = new TextButton("Pull Card", skin);
        pullButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player p = Natac.instance.player;
                if (p.getResearch() < 1) return;
                PacketUtil.createStatChange(p, -1, 0, 0, 0, 0, 0);
                Card card = Card.pullCard(p.getPlayerClass());
                p.giveCard(card);
                addButton(buildCardButton(card), getWidth()/5, getHeight(), 5);
            }
        });
        addButton(pullButton, getWidth() / 5, getHeight(), 5);

        for (Card card : Natac.instance.player.getCards()) {
            addButton(buildCardButton(card), getWidth() / 5, getHeight(), 5);
        }

    }

    private Button buildCardButton(Card card) {
        TextButton button = new TextButton(card.toString(), skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.getGame().addUIActor(new CardPlayerSelector(card));
                animateExit();
            }
        });
        return button;
    }


}
