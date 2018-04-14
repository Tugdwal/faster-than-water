package ftw.game.graphics.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;
import ftw.game.Player;
import ftw.game.assets.Assets;
import ftw.game.graphics.base.GameScreen;
import ftw.game.world.Location;
import ftw.game.world.Seaway;
import ftw.game.world.World;

public class WorldScreen extends GameScreen
{
    Color m_background_color;

    private World m_world;
    private ArrayList<LocationButton> m_locations;
    private ArrayList<SeawaySprite> m_seaways;
    private ShipImage m_ship;

    {
        m_locations = new ArrayList<>();
        m_seaways = new ArrayList<>();
    }

    public WorldScreen(FTWGame game, Batch batch, Viewport hud_viewport, Viewport game_viewport, World world)
    {
        super(game, batch, hud_viewport, game_viewport);

        m_world = world;

        Skin skin = game.assets().get(Assets.SKIN_DEFAULT);
        m_background_color = skin.getColor("background-reversed");
        TextButtonStyle style = skin.get("default-reversed", TextButtonStyle.class);
        Sprite sprite = skin.getSprite("square");

        Table table = new Table();
        table.setFillParent(true);
        table.left().top();
        hud().addActor(table);

        for (Location location : world.locations()) {
            final LocationButton button = new LocationButton(location, style);
            button.addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    m_ship.move(button);
                    update();
                }
            });

            m_locations.add(button);
            hud().addActor(button);

            if (location == game.player().location()) {
                m_ship = new ShipImage(game.assets().get(Assets.TEXTURE_SHIP_SIDE), game.player(), button);
            }
        }

        for (Seaway seaway : world.seaways()) {
            m_seaways.add(new SeawaySprite(sprite, seaway));
        }

        stage().addActor(m_ship);

        final TextButton back_button = new TextButton("< Back", style);
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

        update();
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

        for (LocationButton button : m_locations) {
            Vector3 v = camera.project(new Vector3(button.m_location.position(), 0));
            button.setPosition(v.x, v.y, Align.center);
        }

        for (SeawaySprite sprite : m_seaways) {
            Vector3 vs = new Vector3(sprite.m_seaway.start().position(), 0);
            Vector3 ve = new Vector3(sprite.m_seaway.end().position(), 0);
            Vector3 v = ve.cpy().sub(vs);

            sprite.setScale(v.len() / sprite.getWidth() * 0.8f, 3);
            sprite.setRotation(MathUtils.atan2(v.y, v.x) * 180 / MathUtils.PI);
        }
    }

    private void update()
    {
        ArrayList<Location> locations = m_world.range(m_ship.m_player.location());

        for (LocationButton button : m_locations) {
            for (Location location : locations) {
                if (button.m_location == location) {
                    button.setTouchable(Touchable.enabled);
                    break;
                } else {
                    button.setTouchable(Touchable.disabled);
                }
            }
        }
    }

    private class LocationButton extends TextButton
    {
        private Location m_location;

        public LocationButton(Location location, TextButtonStyle style)
        {
            super(location.name(), style);
            m_location = location;
        }

        public LocationButton(Location location, Skin skin)
        {
            super(location.name(), skin);
            m_location = location;
        }
    }

    private class SeawaySprite extends Sprite
    {
        private Seaway m_seaway;

        public SeawaySprite(Sprite sprite, Seaway seaway)
        {
            super(sprite);
            m_seaway = seaway;

            Vector2 v = seaway.start().position().cpy().add(seaway.end().position()).scl(0.5f);
            setPosition(v.x, v.y);
        }
    }

    private class ShipImage extends Image
    {
        private Player m_player;
        private LocationButton m_button;

        public ShipImage(Texture texture, Player player, LocationButton button)
        {
            super(texture);
            m_player = player;
            m_button = button;
            m_button.setVisible(false);

            setScale(0.05f);
            update();
        }

        public void move(LocationButton button)
        {
            m_button.setVisible(true);
            m_button = button;
            m_button.setVisible(false);

            m_player.move(button.m_location);

            update();
        }

        public void update()
        {
            setPosition(m_player.location().position().x - getWidth() * getScaleX() / 2, m_player.location().position().y - getHeight() * getScaleY() / 2);
        }
    }
}
