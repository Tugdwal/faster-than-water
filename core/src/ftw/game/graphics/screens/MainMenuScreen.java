package ftw.game.graphics.screens;

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
    public MainMenuScreen(FTWGame game, Batch batch, Viewport viewport)
    {
        super(game, batch, viewport);

        Skin skin = game.assets().get(Assets.SKIN_DEFAULT);
        setColor(skin.getColor("background"));
        TextButtonStyle style = skin.get("default-large", TextButtonStyle.class);

        final TextButton play_button = new TextButton("Play", style);
        play_button.getLabelCell().pad(10);
        play_button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                push(Screen.Event.WORLD);
            }
        });

        final TextButton exit_button = new TextButton("Exit", style);
        exit_button.getLabelCell().pad(10);
        exit_button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(play_button);
        table.row().pad(20);
        table.add(exit_button);

        hud().addActor(table);
    }

    @Override
    public void render(float delta)
    {
        clear();

        super.render(delta);
    }
}
