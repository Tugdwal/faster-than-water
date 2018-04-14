package ftw.game.graphics.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;

public class GameScreen extends HUDScreen
{
    private Stage m_stage;
    private InputMultiplexer m_multiplexer;

    public GameScreen(FTWGame game, Batch batch, Viewport hud_viewport, Viewport game_viewport)
    {
        super(game, batch, hud_viewport);
        m_stage = new Stage(game_viewport, batch);
        m_multiplexer = new InputMultiplexer(hud(), m_stage);
    }

    public Stage stage()
    {
        return m_stage;
    }

    @Override
    public void render(float delta)
    {
        m_stage.act(delta);
        m_stage.draw();
        super.render(delta);
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        m_stage.getViewport().update(width, height, true);
    }

    @Override
    public void resume()
    {
        Gdx.input.setInputProcessor(m_multiplexer);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        m_stage.dispose();
    }
}
