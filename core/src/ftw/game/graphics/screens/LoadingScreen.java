package ftw.game.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;
import ftw.game.graphics.base.HUDScreen;

public class LoadingScreen extends HUDScreen
{
    private Label m_progress;
    private ProgressBar m_progress_bar;

    public LoadingScreen(FTWGame game, Batch batch, Viewport viewport)
    {
        super(game, batch, viewport);

        Table table = new Table();
        table.setFillParent(true);
        hud().addActor(table);

        BitmapFont font = new BitmapFont();
        font.getData().scale(3);

        LabelStyle style = new LabelStyle(font, Color.WHITE);
        m_progress = new Label("0 %", style);

        table.add(m_progress);
    }

    public void setProgress(float progress)
    {
        int p = (int) (progress * 100.0f);
        m_progress.setText(p + " %");
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(delta);
    }
}
