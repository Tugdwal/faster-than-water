package ftw.game.quest;

import java.util.Arrays;

import ftw.game.item.Item;

public class Reward
{
    private int m_money;
    private Item[] m_items;

    public Reward(int money, Item... items)
    {
        m_money = money;
        m_items = items;
    }

    public int money()
    {
        return m_money;
    }

    public Item[] items()
    {
        return m_items;
    }

    @Override
    public String toString()
    {
        return String.format("Reward [%s, %s]", m_money, Arrays.toString(m_items));
    }
}
