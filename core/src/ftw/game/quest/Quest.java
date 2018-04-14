package ftw.game.quest;

import ftw.game.item.Item;
import ftw.game.world.Location;

public class Quest
{
    private Location m_destination;
    private Cargo m_cargo;
    private Reward m_reward;

    {
        m_cargo = new Cargo(Good.create(Good.Type.Gold, 100));
        m_reward = new Reward(10, Item.create(Item.Type.Sword));
    }

    public Quest(Location destination)
    {
        m_destination = destination;
    }

    public Location destination()
    {
        return m_destination;
    }

    public Cargo cargo()
    {
        return m_cargo;
    }

    public Reward reward()
    {
        return m_reward;
    }

    @Override
    public String toString()
    {
        return String.format("Quest [%s, %s, %s]", m_destination, m_cargo, m_reward);
    }
}
