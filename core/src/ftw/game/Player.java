package ftw.game;

import java.util.HashSet;
import java.util.Set;

import ftw.game.quest.Quest;
import ftw.game.ship.Ship;
import ftw.game.world.Location;

public class Player
{
    private int m_money;

    private Ship m_ship;
    private Location m_location;

    private Set<Quest> m_quests;

    {
        m_money = 0;
        m_quests = new HashSet<>();
    }

    public Player(Ship ship, Location location)
    {
        m_ship = ship;
        m_location = location;
    }

    public int money()
    {
        return m_money;
    }

    public Ship ship()
    {
        return m_ship;
    }

    public Location location()
    {
        return m_location;
    }

    public Set<Quest> quests()
    {
        return m_quests;
    }

    public void move(Location location)
    {
        m_location = location;
    }

    public boolean selectable(Quest quest)
    {
        return m_ship.loadable(quest.cargo());
    }

    public void select(Quest quest)
    {
        m_quests.add(quest);
        m_ship.load(quest.cargo());
    }

    @Override
    public String toString()
    {
        return String.format("Player [%n  Money [%s],%n  %s,%n  %s,%n  %s%n]", m_money, m_ship, m_location, m_quests);
    }
}
