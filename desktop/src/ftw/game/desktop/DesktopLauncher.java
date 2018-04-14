package ftw.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ftw.game.FTWGame;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Faster Than Water";

        config.width = 1920;
        config.height = 1080;

        new LwjglApplication(new FTWGame(), config);
    }
}
