package ftw.game.graphics.screens;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;
import ftw.game.assets.Assets;
import ftw.game.graphics.base.DataButton;
import ftw.game.graphics.base.DataImage;
import ftw.game.graphics.base.GameScreen;
import ftw.game.graphics.base.Screen;
import ftw.game.quest.Quest;
import ftw.game.ship.Ship;
import ftw.game.world.Location;
import ftw.game.world.Seaway;

public class WorldScreen extends GameScreen
{
    private Label m_location;
    private Label m_score;

    private DataButton<Location> m_current_location;
    private DataImage<Ship> m_ship;

    private ArrayList<DataButton<Location>> m_locations;
    private ArrayList<DataImage<Seaway>> m_seaways;

    {
        m_locations = new ArrayList<>();
        m_seaways = new ArrayList<>();
        m_seaways = new ArrayList<>();
    }

    public WorldScreen(FTWGame game, Batch batch, Viewport hud_viewport, Viewport game_viewport)
    {
        super(game, batch, hud_viewport, game_viewport);

        Skin skin = game().assets().get(Assets.SKIN_DEFAULT);
        setColor(skin.getColor("background-reversed"));
        TextButtonStyle style = skin.get("default-reversed", TextButtonStyle.class);

        for (Location location : game().world().locations()) {
            final DataButton<Location> button = new DataButton<>(location.name(), style, location);
            button.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    m_current_location.setVisible(true);
                    m_current_location = button;
                    m_current_location.setVisible(false);
                    game().player().move(m_current_location.data());
                    m_location.setText(m_current_location.data().name());
                    push(Screen.Event.BATTLE);
                }
            });

            m_locations.add(button);
            hud().addActor(button);

            if (location == game().player().location()) {
                m_location = new Label(location.name(), skin);
                m_current_location = button;
                m_current_location.setVisible(false);
            }
        }

        for (Seaway seaway : game().world().seaways()) {
            DataImage<Seaway> image = new DataImage<Seaway>(skin.getDrawable("square"), seaway);
            image.setOriginY(image.getHeight() / 2);
            image.setPosition(seaway.start().position().x, seaway.start().position().y);
            image.setRotation(MathUtils.atan2(seaway.end().position().y - seaway.start().position().y, seaway.end().position().x - seaway.start().position().x) * 180 / MathUtils.PI);

            m_seaways.add(image);
            stage().addActor(image);
        }

        m_ship = new DataImage<Ship>(game().assets().get(Assets.TEXTURE_SHIP_SIDE), game().player().ship());
        m_ship.setSize(80, 80);
        stage().addActor(m_ship);

        TextButton back_button = new TextButton("< Back", style);
        back_button.getLabelCell().pad(10);
        back_button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                pop();
            }
        });

        TextButton quest_button = new TextButton("Select quests", style);
        quest_button.getLabelCell().pad(10);
        quest_button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                push(Screen.Event.QUEST);
            }
        });

        m_score = new Label("", skin);

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        table.add(back_button);
        table.add(m_location).pad(10).padLeft(30).padRight(30);
        table.add(quest_button);
        table.add(m_score).pad(10).padLeft(30).padRight(30);

        hud().addActor(table);
    }

    @Override
    public void init()
    {
        update();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);

        Camera camera = stage().getCamera();

        for (DataButton<Location> button : m_locations) {
            Vector3 v = camera.project(new Vector3(button.data().position(), 0));
            button.setPosition(v.x, v.y, Align.center);
        }

        for (DataImage<Seaway> seaway_image : m_seaways) {
            seaway_image.update((image, seaway) -> image.setScaleX(seaway.end().position().cpy().sub(seaway.start().position()).len() / image.getWidth()));
        }
    }

    private void update()
    {
        game().world().update();

        ArrayList<Location> locations = game().world().range(m_current_location.data());
        for (DataButton<Location> button : m_locations) {
            for (Location location : locations) {
                if (button.data() == location) {
                    button.setTouchable(Touchable.enabled);
                    break;
                } else {
                    button.setTouchable(Touchable.disabled);
                }
            }
        }

        m_ship.update((image, ship) -> image.setPosition(ship.position().x - m_ship.getWidth() / 2, ship.position().y - m_ship.getHeight() / 2));

        for (Quest quest : game().player().quests()) {
            if (quest.destination() == game().player().location()) {
                game().player().complete(quest);
            }
        }

        m_score.setText(String.format("Score : %s", game().player().money()));
    }
}
