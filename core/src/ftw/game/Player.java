package ftw.game;

import java.util.ArrayList;

import ftw.game.quest.Quest;
import ftw.game.ship.Ship;
import ftw.game.world.Location;

public class Player
{
    private Ship m_ship;
    private int m_money;

    private Location m_location;

    private ArrayList<Quest> m_quests;

    {
        m_money = 0;
        m_quests = new ArrayList<>();
    }

    public Player(Ship ship, Location location)
    {
        m_ship = ship;
        m_location = location;

        select(m_location.quests().iterator().next());
    }

    public Location location()
    {
        return m_location;
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
    
    public Ship ship() 
    { 
    	return m_ship; 
    }

    @Override
    public String toString()
    {
        return String.format("Player [%n  Money [%s],%n  %s,%n  %s,%n  %s%n]", m_money, m_ship, m_location, m_quests);
    }
}
