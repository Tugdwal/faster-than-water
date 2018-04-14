package ftw.game.world;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;

import ftw.game.quest.Quest;

public class Location
{
    private String m_name;
    private Vector2 m_position;
    private Set<Quest> m_quests;

    {
        m_quests = new HashSet<>();
    }

    public Location(String name, float x, float y)
    {
        m_name = name;
        m_position = new Vector2(x, y);
    }

    public String name()
    {
        return m_name;
    }

    public Vector2 position()
    {
        return m_position;
    }

    public Set<Quest> quests()
    {
        return m_quests;
    }

    public void assign(Quest quest)
    {
        m_quests.add(quest);
    }

    public void unassign(Quest quest)
    {
        m_quests.remove(quest);
    }

    @Override
    public String toString()
    {
        return String.format("Location [%s]", m_name);
    }
}
