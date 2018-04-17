package ftw.game.ship;

import ftw.game.item.Item;
import ftw.game.stat.Stat;

public class CrewMember extends Entity
{
    public CrewMember(String name)
    {
        super(name);

        m_stats.put(Stat.Type.Health, new Stat(Stat.Type.Health, 100));
        m_stats.put(Stat.Type.Defense, new Stat(Stat.Type.Defense));
        m_stats.put(Stat.Type.Damage, new Stat(Stat.Type.Damage, 10));
    }

    public Stat health()
    {
        return stat(Stat.Type.Health);
    }

    public Stat defense()
    {
        return stat(Stat.Type.Defense);
    }

    public Stat damage()
    {
        return stat(Stat.Type.Damage);
    }

    public boolean equipable(Item item)
    {
        return item.type() == Item.Type.Sword;
    }

    @Override
    public CrewMember equip(Item item)
    {
        if (equipable(item)) {
            super.equip(item);
        }

        return this;
    }

    @Override
    public CrewMember remove(Item item)
    {
        super.remove(item);

        return this;
    }

    @Override
    public String toString()
    {
        return String.format("CrewMember [%s]", super.toString());
    }
}
