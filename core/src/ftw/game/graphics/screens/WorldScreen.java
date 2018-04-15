package ftw.game.graphics.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import ftw.game.graphics.base.GameScreen;
import ftw.game.graphics.base.Screen;
import ftw.game.world.Location;
import ftw.game.world.Seaway;

public class WorldScreen extends GameScreen
{
    Color m_background_color;

    private Label m_location;
    private DataButton<Location> m_current_location;
    private ArrayList<DataButton<Location>> m_locations;
    private ArrayList<SeawaySprite> m_seaways;
    private Image m_ship;

    {
        m_locations = new ArrayList<>();
        m_seaways = new ArrayList<>();
    }

    public WorldScreen(FTWGame game, Batch batch, Viewport hud_viewport, Viewport game_viewport)
    {
        super(game, batch, hud_viewport, game_viewport);

        Skin skin = game().assets().get(Assets.SKIN_DEFAULT);
        m_background_color = skin.getColor("background-reversed");
        TextButtonStyle style = skin.get("default-reversed", TextButtonStyle.class);
        Sprite sprite = skin.getSprite("square");

        m_ship = new Image(game().assets().get(Assets.TEXTURE_SHIP_SIDE));
        m_ship.setSize(80, 80);
        stage().addActor(m_ship);

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
                    update();
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
            m_seaways.add(new SeawaySprite(sprite, seaway));
        }

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        hud().addActor(table);

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

        table.add(back_button);
        table.add(m_location).pad(10).padLeft(30).padRight(30);

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

        table.add(quest_button);

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(m_background_color.r, m_background_color.g, m_background_color.b, m_background_color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Batch batch = stage().getBatch();
        Camera camera = stage().getCamera();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (SeawaySprite sprite : m_seaways) {
            sprite.draw(batch);
        }

        batch.end();

        super.render(delta);
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

        for (SeawaySprite sprite : m_seaways) {
            Vector3 vs = new Vector3(sprite.m_seaway.start().position(), 0);
            Vector3 ve = new Vector3(sprite.m_seaway.end().position(), 0);
            Vector3 v = ve.cpy().sub(vs);

            sprite.setScale(v.len() / sprite.getWidth(), 3);
        }
    }

    @Override
    public void resume()
    {
        super.resume();
        update();
    }

    private void update()
    {
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

        m_ship.setPosition(m_current_location.data().position().x - m_ship.getWidth() / 2, m_current_location.data().position().y - m_ship.getHeight() / 2);
    }

    private class SeawaySprite extends Sprite
    {
        private Seaway m_seaway;

        public SeawaySprite(Sprite sprite, Seaway seaway)
        {
            super(sprite);
            m_seaway = seaway;

            Vector2 pos = seaway.start().position().cpy().add(seaway.end().position()).scl(0.5f);
            setPosition(pos.x, pos.y);

            Vector2 dir = seaway.end().position().cpy().sub(seaway.start().position());
            setRotation(MathUtils.atan2(dir.y, dir.x) * 180 / MathUtils.PI);
        }
    }
}
