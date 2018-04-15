package ftw.game.world;

import java.util.ArrayList;

import ftw.game.quest.Quest;

public class World
{
    private ArrayList<Location> m_harbors;
    private ArrayList<Location> m_locations;
    private ArrayList<Seaway> m_seaways;
    private ArrayList<Quest> m_quests;

    private Location m_starting_location;

    {
        m_harbors = new ArrayList<>();
        m_locations = new ArrayList<>();
        m_seaways = new ArrayList<>();
        m_quests = new ArrayList<>();

        Location tortuga = new Location("Tortuga", 200, 50);
        Location singapore = new Location("Singapore", 1080, 50);
        Location port_royal = new Location("Port Royal", 640, 600);
        Location bermuda_triangle = new Location("Bermuda Triangle", 640, 300);

        m_starting_location = port_royal;

        m_harbors.add(tortuga);
        m_harbors.add(singapore);
        m_harbors.add(port_royal);

        m_locations.add(tortuga);
        m_locations.add(singapore);
        m_locations.add(port_royal);
        m_locations.add(bermuda_triangle);

        m_seaways.add(new Seaway(tortuga, bermuda_triangle));
        m_seaways.add(new Seaway(singapore, bermuda_triangle));
        m_seaways.add(new Seaway(port_royal, bermuda_triangle));
        m_seaways.add(new Seaway(port_royal, singapore));

        Quest q1 = new Quest(tortuga);
        Quest q2 = new Quest(singapore);

        m_quests.add(q1);
        m_quests.add(q2);

        port_royal.assign(q1);
        port_royal.assign(q2);
    }

    public ArrayList<Location> harbors()
    {
        return m_harbors;
    }

    public ArrayList<Location> locations()
    {
        return m_locations;
    }

    public ArrayList<Seaway> seaways()
    {
        return m_seaways;
    }

    public ArrayList<Location> range(Location location)
    {
        ArrayList<Location> locations = new ArrayList<>();

        for (Seaway seaway : m_seaways) {
            if (location == seaway.start()) {
                locations.add(seaway.end());
            }
            if (location == seaway.end()) {
                locations.add(seaway.start());
            }
        }

        return locations;
    }

    public Location startingLocation()
    {
        return m_starting_location;
    }

    @Override
    public String toString()
    {
        return String.format("World [%n  %s,%n  %s,%n  %s,%n  %s%n]", m_harbors, m_locations, m_seaways, m_quests);
    }
}
