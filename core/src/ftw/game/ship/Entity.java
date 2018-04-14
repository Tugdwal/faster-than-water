package ftw.game.ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ftw.game.item.Item;
import ftw.game.stat.Stat;

public class Entity
{
    protected String m_name;
    protected Map<Stat.Type, Stat> m_stats;
    protected ArrayList<Item> m_inventory;

    {
        m_stats = new HashMap<>();
        m_inventory = new ArrayList<>();
    }

    public Entity(String name)
    {
        m_name = name;
    }

    public String name()
    {
        return m_name;
    }

    protected Stat stat(Stat.Type type)
    {
        return m_stats.get(type);
    }

    public boolean equipable(Item item)
    {
        return true;
    }

    protected Entity equip(Item item)
    {
        m_inventory.add(item);

        for (Stat stat : m_stats.values()) {
            stat.add(item.stat());
        }

        return this;
    }

    protected Entity remove(Item item)
    {
        if (m_inventory.remove(item)) {
            for (Stat stat : m_stats.values()) {
                stat.remove(item.stat());
            }
        }

        return this;
    }

    @Override
    public String toString()
    {
        return String.format("%s, %s, %s", m_name, m_stats.values(), m_inventory);
    }
}
