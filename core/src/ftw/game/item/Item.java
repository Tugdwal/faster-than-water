package ftw.game.item;

import ftw.game.stat.Stat;

public class Item
{
    public enum Rarity
    {
        Common, Rare, Epic, Legendary
    }

    public enum Type
    {
        None, Sword
    }

    private Type m_type;
    private String m_name;
    private String m_description;
    private Rarity m_rarity;
    private Stat m_stat;

    {
        m_rarity = Rarity.Common;
    }

    public Item(Type type, String name, String description, Stat stat)
    {
        m_type = type;
        m_name = name;
        m_description = description;
        m_stat = stat;
    }

    public Type type()
    {
        return m_type;
    }

    public String name()
    {
        return m_name;
    }

    public String description()
    {
        return m_description;
    }

    public Rarity rarity()
    {
        return m_rarity;
    }

    public Stat stat()
    {
        return m_stat;
    }

    @Override
    public String toString()
    {
        return String.format("Item [%s, %s]", m_name, m_stat);
    }

    public static Item create(Type type)
    {
        switch (type) {
            case Sword:
                return new Item(type, type.name(), "You don't know what a sword is ?...", new Stat(Stat.Type.Damage, 10));
            default:
                return new Item(Type.None, "None", "No description available", new Stat(Stat.Type.None));
        }
    }
}
