package ftw.game.stat;

public class Stat
{
    public enum Type
    {
        None,
        Health,
        Defense,
        Damage,
        Range,
        Speed,
        Steering
    }

    private Type m_type;
    private int m_base_value;
    private int m_multiplier;

    public Stat(Type type)
    {
        this(type, 0, 0);
    }

    public Stat(Type type, int base_value)
    {
        this(type, base_value, 0);
    }

    public Stat(Type type, int base_value, int multiplier)
    {
        m_type = type;
        m_base_value = base_value;
        m_multiplier = multiplier;
    }

    public int value()
    {
        return (int) (m_base_value * (1.0 + m_multiplier / 100.0));
    }

    public void value(int value)
    {
        m_base_value = value;
    }

    public void add(Stat stat)
    {
        if (m_type == stat.m_type) {
            m_base_value += stat.m_base_value;
            m_multiplier += stat.m_multiplier;
        }
    }

    public void remove(Stat stat)
    {
        if (m_type == stat.m_type) {
            m_base_value -= stat.m_base_value;
            m_multiplier -= stat.m_multiplier;
        }
    }

    @Override
    public String toString()
    {
        return String.format("%s: %s", m_type.name(), value());
    }

}
