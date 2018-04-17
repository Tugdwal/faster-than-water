package ftw.game;

import java.util.HashSet;
import java.util.Set;

import ftw.game.quest.Quest;
import ftw.game.quest.Reward;
import ftw.game.ship.Ship;
import ftw.game.world.Location;

public class Player
{
    private int m_money;

    private Ship m_ship;

    private Set<Quest> m_quests;

    {
        m_money = 0;
        m_quests = new HashSet<>();
    }

    public Player(Ship ship, Location location)
    {
        m_ship = ship.move(location);
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
        return m_ship.location();
    }

    public Set<Quest> quests()
    {
        return m_quests;
    }

    public Ship move(Location location)
    {
        return m_ship.move(location);
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

    public void receive(Reward reward)
    {
        m_money += reward.money();
    }

    public void complete(Quest quest)
    {
        if (m_ship.unload(quest.cargo()) && m_quests.remove(quest)) {
            receive(quest.reward());
        }
    }

    @Override
    public String toString()
    {
        return String.format("Player [%n  Money [%s],%n  %s,%n  %s%n]", m_money, m_ship, m_quests);
    }
}
