package ftw.game.graphics.base;

import java.util.EnumSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import ftw.game.FTWGame;

public abstract class Screen extends ScreenAdapter
{
    public enum Event
    {
        NONE, EXIT, RESET, POP, PUSH, MAIN_MENU, WORLD, QUEST, BATTLE
    }

    Color m_color;

    private FTWGame m_game;

    {
        m_color = Color.BLACK;
    }

    public Screen(FTWGame game)
    {
        m_game = game;
    }

    public void init()
    {
    }

    public FTWGame game()
    {
        return m_game;
    }

    public void setColor(Color color)
    {
        m_color = color;
    }

    public void clear()
    {
        Gdx.gl.glClearColor(m_color.r, m_color.g, m_color.b, m_color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void pop()
    {
        m_game.handleEvent(EnumSet.of(Event.POP));
    }

    public void push(Screen.Event screen)
    {
        m_game.handleEvent(EnumSet.of(Event.PUSH, screen));
    }

    public void replace(Screen.Event screen)
    {
        m_game.handleEvent(EnumSet.of(Event.POP, Event.PUSH, screen));
    }

    public void exit()
    {
        m_game.handleEvent(EnumSet.of(Event.EXIT));
    }
}
