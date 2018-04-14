package ftw.game.quest;

import java.util.Arrays;

public class Cargo
{
    private Good[] m_goods;

    public Cargo(Good... goods)
    {
        m_goods = goods;
    }

    public int size()
    {
        int size = 0;
        for (Good good : m_goods) {
            size += good.size() * good.quantity();
        }

        return size;
    }

    public int value()
    {
        int value = 0;
        for (Good good : m_goods) {
            value += good.value() * good.quantity();
        }

        return value;
    }

    @Override
    public String toString()
    {
        return String.format("Cargo [Size: %s, Value: %s, Content: %s]", size(), value(), Arrays.toString(m_goods));
    }
}
