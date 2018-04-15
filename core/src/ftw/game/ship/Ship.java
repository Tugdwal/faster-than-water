package ftw.game.ship;

import java.util.ArrayList;

import ftw.game.item.Item;
import ftw.game.quest.Cargo;
import ftw.game.stat.Stat;

public class Ship extends Entity
{
    private CrewMember m_captain;
    private ArrayList<CrewMember> m_crew;

    private int m_cargo_space;
    private ArrayList<Cargo> m_cargo;

    {
        m_crew = new ArrayList<>();
        m_cargo = new ArrayList<>();
    }

    public Ship(String name, CrewMember captain, int hold_size)
    {
        super(name);

        m_stats.put(Stat.Type.Health, new Stat(Stat.Type.Health, 2000));
        m_stats.put(Stat.Type.Defense, new Stat(Stat.Type.Defense, 100));
        m_stats.put(Stat.Type.Speed, new Stat(Stat.Type.Speed, 100));
        m_stats.put(Stat.Type.Steering, new Stat(Stat.Type.Steering, 20));

        m_captain = captain;
        m_cargo_space = hold_size;
    }

    public Stat health()
    {
        return stat(Stat.Type.Health);
    }

    public Ship health(int health)
    {
    	m_stats.put(Stat.Type.Health, new Stat(Stat.Type.Health, health));
        return this;
    }
    
    public int damage (int damage) 
    {
    	damage -= stat(Stat.Type.Defense).value();
    	int health = stat(Stat.Type.Health).value();
    	if (damage > 0)
    		stat(Stat.Type.Health).value(health - damage);
    	
    	return stat(Stat.Type.Health).value();
    }

    public Stat defense()
    {
        return stat(Stat.Type.Defense);
    }

    public Ship defense(int defense)
    {
    	m_stats.put(Stat.Type.Defense, new Stat(Stat.Type.Defense, defense));
        return this;
    }

    public Stat speed()
    {
        return stat(Stat.Type.Speed);
    }

    public Ship speed(int speed)
    {
    	m_stats.put(Stat.Type.Speed, new Stat(Stat.Type.Speed, speed));
        return this;
    }

    public Stat steering()
    {
        return stat(Stat.Type.Steering);
    }

    public Ship steering(int steering)
    {
    	m_stats.put(Stat.Type.Steering, new Stat(Stat.Type.Steering, steering));
        return this;
    }

    @Override
    public boolean equipable(Item item)
    {
        return false;
    }

    @Override
    public Ship equip(Item item)
    {
        if (equipable(item)) {
            super.equip(item);
        }

        return this;
    }

    @Override
    public Ship remove(Item item)
    {
        super.remove(item);

        return this;
    }

    public Ship board(CrewMember crew_member)
    {
        m_crew.add(crew_member);
        return this;
    }

    public int cargoSpace()
    {
        return m_cargo_space;
    }

    public int cargoSize()
    {
        int size = 0;
        for (Cargo cargo : m_cargo) {
            size += cargo.size();
        }

        return size;
    }

    public int availableCargoSpace()
    {
        return m_cargo_space - cargoSize();
    }

    public boolean loadable(Cargo cargo)
    {
        return availableCargoSpace() - cargo.size() >= 0;
    }

    public void load(Cargo cargo)
    {
        m_cargo.add(cargo);
    }

    @Override
    public String toString()
    {
        return String.format("Ship [%s, %s, %s, %s]", super.toString(), m_captain, m_crew, m_cargo);
    }
}
