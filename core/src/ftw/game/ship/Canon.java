package ftw.game.ship;

public class Canon
{
    private static int RELOAD_TIME = 5000;
    private int m_reload_left;

    {
        m_reload_left = 0;
    }

    public boolean loaded()
    {
        return m_reload_left == 0;
    }

    public void fire()
    {
        m_reload_left = RELOAD_TIME;
    }

    public void reload(int dt)
    {
        m_reload_left -= dt;
        if (m_reload_left <= 0) {
            m_reload_left = 0;
        }
    }

    public int reloadTime()
    {
        return m_reload_left;
    }
}
