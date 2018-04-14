package ftw.game.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;
import ftw.game.assets.Assets;
import ftw.game.graphics.base.HUDScreen;
import ftw.game.graphics.base.Screen;

public class MainMenuScreen extends HUDScreen
{
    private Color m_background_color;

    public MainMenuScreen(FTWGame game, Batch batch, Viewport viewport)
    {
        super(game, batch, viewport);

        Skin skin = game.assets().get(Assets.SKIN_DEFAULT);
        m_background_color = skin.getColor("background");
        TextButtonStyle style = skin.get("default-large", TextButtonStyle.class);

        Table table = new Table();
        table.setFillParent(true);
        hud().addActor(table);

        final TextButton play_button = new TextButton("Play", style);
        final TextButton exit_button = new TextButton("Exit", style);

        play_button.getLabelCell().pad(10);
        exit_button.getLabelCell().pad(10);

        table.add(play_button);
        table.row().pad(20);
        table.add(exit_button);

        play_button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                push(Screen.Event.WORLD);
            }
        });

        exit_button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(m_background_color.r, m_background_color.g, m_background_color.b, m_background_color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }
}
