package ftw.game.graphics.base;

import java.util.EnumSet;

import com.badlogic.gdx.ScreenAdapter;

import ftw.game.FTWGame;

public class Screen extends ScreenAdapter
{
    public enum Event
    {
        NONE, RESET, POP, PUSH, MAIN_MENU, WORLD, QUEST, BATTLE, TEST
    }

    private FTWGame m_game;

    public Screen(FTWGame game)
    {
        m_game = game;
    }

    public FTWGame game()
    {
        return m_game;
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
    
    public void init ()
    {
    	
    }
}
