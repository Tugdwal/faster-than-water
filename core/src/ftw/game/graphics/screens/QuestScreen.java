package ftw.game.graphics.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;
import ftw.game.assets.Assets;
import ftw.game.graphics.base.DataButton;
import ftw.game.graphics.base.HUDScreen;
import ftw.game.quest.Quest;

public class QuestScreen extends HUDScreen
{
    private Label m_location;
    private Label m_cargo;
    private VerticalGroup m_available;
    private VerticalGroup m_selected;

    {
        m_available = new VerticalGroup();
        m_selected = new VerticalGroup();
    }

    public QuestScreen(FTWGame game, Batch batch, Viewport viewport)
    {
        super(game, batch, viewport);

        Skin skin = game().assets().get(Assets.SKIN_DEFAULT);
        setColor(skin.getColor("background"));

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        table.top().left();
        hud().addActor(table);

        Table top = new Table();
        table.add(top).colspan(2);
        table.row();

        TextButton back_button = new TextButton("< Back", skin);
        back_button.getLabelCell().pad(10);
        back_button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                pop();
            }
        });

        top.add(back_button);

        m_location = new Label("", skin);
        top.add(m_location).pad(10).padLeft(30);

        m_cargo = new Label("", skin);
        top.add(m_cargo).pad(10).padLeft(30);

        table.add(new Label("Available quests", skin));
        table.add(new Label("Your quests", skin));
        table.row();

        table.add(m_available);
        table.add(m_selected);
    }

    @Override
    public void render(float delta)
    {
        clear();

        super.render(delta);
    }

    @Override
    public void init()
    {
        m_location.setText(game().player().location().name());

        Skin skin = game().assets().get(Assets.SKIN_DEFAULT);
        TextButtonStyle button_style = skin.get("default", TextButtonStyle.class);
        LabelStyle label_style = skin.get("default-small", LabelStyle.class);

        m_available.clear();
        for (Quest quest : game().player().location().quests()) {
            final DataButton<Quest> button = createQuestButton(quest, button_style, label_style);

            button.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    if (game().player().selectable(button.data())) {
                        m_available.removeActor(button);
                        m_selected.addActor(button);
                        button.setDisabled(true);
                        game().player().location().unassign(button.data());
                        game().player().select(button.data());
                    }

                    update();
                }
            });

            m_available.addActor(button);
        }

        m_selected.clear();
        for (Quest quest : game().player().quests()) {
            final DataButton<Quest> button = createQuestButton(quest, button_style, label_style);
            button.setDisabled(true);

            button.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {

                }
            });

            m_selected.addActor(button);
        }

        update();
    }

    private void update()
    {
        m_cargo.setText(String.format("Available space : %s", game().player().ship().availableCargoSpace()));
    }

    private DataButton<Quest> createQuestButton(Quest quest, TextButtonStyle button_style, LabelStyle label_style)
    {
        DataButton<Quest> button = new DataButton<>("Quest", button_style, quest);
        button.row();
        button.add(new Label(String.format("Destination : %s", quest.destination().name()), label_style));
        button.row();
        button.add(new Label(String.format("Cargo size : %s", quest.cargo().size()), label_style));
        button.row();
        button.add(new Label(String.format("Cargo value : %s", quest.cargo().value()), label_style));
        button.row();
        button.add(new Label(String.format("Reward : %s$ and %s item(s)", quest.reward().money(), quest.reward().items().length), label_style));

        return button;
    }
}
