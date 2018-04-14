package ftw.game.quest;

public class Good
{
    public enum Type
    {
        Gold
    }

    private String m_name;

    private int m_size;
    private int m_value;
    private int m_quantity;

    public Good(String name, int size, int value, int quantity)
    {
        m_name = name;
        m_size = size;
        m_value = value;
        m_quantity = quantity;
    }

    public String name()
    {
        return m_name;
    }

    public int size()
    {
        return m_size;
    }

    public int value()
    {
        return m_value;
    }

    public int quantity()
    {
        return m_quantity;
    }

    @Override
    public String toString()
    {
        return String.format("Good [%s, Size: %s, Value: %s, Quantity: %s]", m_name, m_size, m_value, m_quantity);
    }

    public static Good create(Type type, int quantity)
    {
        switch (type) {
            case Gold:
                return new Good(type.name(), 10, 1000, quantity);
            default:
                return new Good("None", 0, 0, quantity);
        }
    }

}
