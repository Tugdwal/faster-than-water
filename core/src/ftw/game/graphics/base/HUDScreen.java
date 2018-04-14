package ftw.game.graphics.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;

public class HUDScreen extends Screen
{
    private Stage m_hud;

    public HUDScreen(FTWGame game, Batch batch, Viewport viewport)
    {
        super(game);
        m_hud = new Stage(viewport, batch);
    }

    public Stage hud()
    {
        return m_hud;
    }

    @Override
    public void render(float delta)
    {
        m_hud.act(delta);
        m_hud.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        m_hud.getViewport().update(width, height, true);
    }

    @Override
    public void resume()
    {
        Gdx.input.setInputProcessor(m_hud);
    }

    @Override
    public void dispose()
    {
        m_hud.dispose();
    }
}
