package ftw.game.graphics.base;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface UpdateFunction<T extends Actor, U>
{
    void execute(T actor, U data);
}
