package ftw.game;

import ftw.game.item.Item;
import ftw.game.ship.CrewMember;
import ftw.game.ship.Ship;
import ftw.game.world.World;

public class FTWTest
{
    public static void main(String[] args)
    {
        World world = new World();

        Ship ship = new Ship("Black Pearl", new CrewMember("Jack Sparrow"), 1000);
        ship.board(new CrewMember("Joshamee Gibbs").equip(Item.create(Item.Type.Sword)));

        Player player = new Player(ship, world.startingLocation());

        System.out.println(world);
        System.out.println(player);
    }
}
