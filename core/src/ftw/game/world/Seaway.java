package ftw.game.world;

public class Seaway
{
    private Location m_start;
    private Location m_end;

    public Seaway(Location start, Location end)
    {
        m_start = start;
        m_end = end;
    }

    public Location start()
    {
        return m_start;
    }

    public Location end()
    {
        return m_end;
    }

    @Override
    public String toString()
    {
        return String.format("Seaway [%s <-> %s]", m_start.name(), m_end.name());
    }
}
