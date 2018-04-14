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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;
import ftw.game.Player;
import ftw.game.assets.Assets;
import ftw.game.graphics.base.GameScreen;
import ftw.game.ship.CrewMember;
import ftw.game.ship.Ship;

public class BattleScreen extends GameScreen {
	Color m_background_color;
	
	private ArrayList<ActionButton> m_HUDButtons;

    private ShipImage m_ship;
    private Ship m_PlayerShip;

    private Label m_LifeAttributs;
    private Label m_DefenseAttributs;
    private Label m_SpeedAttributs;
    private Label m_SteeringAttributs;
    
    {
    	m_HUDButtons = new ArrayList<>();
    }

    public BattleScreen(FTWGame game, Batch batch, Viewport hud_viewport, Viewport game_viewport)
    {
        super(game, batch, hud_viewport, game_viewport);
        
        m_PlayerShip = new Ship ("Mon_Ship", new CrewMember("Captain"), 100);

        Skin skin = game.assets().get(Assets.SKIN_DEFAULT);
        m_background_color = skin.getColor("background-reversed");
        TextButtonStyle style = skin.get("default-reversed", TextButtonStyle.class);
        Sprite sprite = skin.getSprite("square");

        Table table = new Table();
        table.setFillParent(true);
        table.center().bottom();
        hud().addActor(table);

        HUDInit (table, skin, style);
        
        //stage().addActor(m_ship);

        update();
    }
    
    private void HUDInit (Table table, Skin skin, TextButtonStyle style) {
        ActionButton button = new ActionButton ("Shoot", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Shoot");
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        table.add(button).padRight(150);
        
        Table tableAttributs = new Table();
        tableAttributs.setFillParent(true);
        tableAttributs.left().bottom();

        Label lifeLabel = new Label ("Life: ", skin);
        m_LifeAttributs = new Label (String.valueOf(m_PlayerShip.health().value()), skin);
        tableAttributs.add(lifeLabel);
        tableAttributs.add(m_LifeAttributs);
        tableAttributs.row();
        Label defenseLabel = new Label ("Defense: ", skin);
        m_DefenseAttributs = new Label (String.valueOf(m_PlayerShip.defense().value()), skin);
        tableAttributs.add(defenseLabel);
        tableAttributs.add(m_DefenseAttributs);
        tableAttributs.row();
        Label speedLabel = new Label ("Speed: ", skin);
        m_SpeedAttributs = new Label (String.valueOf(m_PlayerShip.speed().value()), skin);
        tableAttributs.add(speedLabel);
        tableAttributs.add(m_SpeedAttributs);
        tableAttributs.row();
        Label steeringLabel = new Label ("Steering: ", skin);
        m_SteeringAttributs = new Label (String.valueOf(m_PlayerShip.steering().value()), skin);
        tableAttributs.add(steeringLabel);
        tableAttributs.add(m_SteeringAttributs);
        
        table.add(tableAttributs).padRight(150);
        
		button = new ActionButton ("Left", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
            	System.out.println("Left");
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        table.add(button).padRight(20);
        
        Table tableMove = new Table();
        tableMove.setFillParent(true);
        tableMove.left().bottom();
		button = new ActionButton ("Forward", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Forward");
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        tableMove.add(button).padBottom(20).row();
        
		button = new ActionButton ("Backward", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
            	System.out.println("Backward");
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        tableMove.add(button);

        table.add(tableMove).padRight(20);
        
		button = new ActionButton ("Right", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
            	System.out.println("Right");
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        table.add(button).padRight(100);;
        
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
        table.add(back_button).padRight(20);;
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

        batch.end();

        super.render(delta);
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);

        Camera camera = stage().getCamera();

        /*for (ActionButton button : m_HUDButtons) {
            Vector3 v = camera.project(new Vector3(button.m_location.position(), 0));
            button.setPosition(v.x, v.y, Align.center);
        }*/

        /*for (SeawaySprite sprite : m_seaways) {
            Vector3 vs = new Vector3(sprite.m_seaway.start().position(), 0);
            Vector3 ve = new Vector3(sprite.m_seaway.end().position(), 0);
            Vector3 v = ve.cpy().sub(vs);

            sprite.setScale(v.len() / sprite.getWidth() * 0.8f, 3);
            sprite.setRotation(MathUtils.atan2(v.y, v.x) * 180 / MathUtils.PI);
        }*/
    }

    private void update()
    {
        m_LifeAttributs.setText(String.valueOf(m_PlayerShip.health().value()));
        m_DefenseAttributs.setText(String.valueOf(m_PlayerShip.defense().value()));
        m_SpeedAttributs.setText(String.valueOf(m_PlayerShip.speed().value()));
        m_SteeringAttributs.setText(String.valueOf(m_PlayerShip.steering().value()));
    }

    private class ActionButton extends TextButton
    {
        public ActionButton(String name, TextButtonStyle style)
        {
            super(name, style);
        }

        public ActionButton(String name, Skin skin)
        {
            super(name, skin);
        }
    }

    private class ShipImage extends Image
    {
        private Player m_player;

        public ShipImage(Texture texture, Player player)
        {
            super(texture);
            m_player = player;

            setScale(0.10f);
            update();
        }

        public void move()
        {
            update();
        }

        public void update()
        {
            setPosition(m_player.location().position().x - getWidth() * getScaleX() / 2, m_player.location().position().y - getHeight() * getScaleY() / 2);
        }
    }
}
