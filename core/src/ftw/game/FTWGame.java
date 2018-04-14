package ftw.game;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.assets.Assets;
import ftw.game.graphics.base.Screen;
import ftw.game.graphics.screens.LoadingScreen;
import ftw.game.graphics.screens.MainMenuScreen;
import ftw.game.graphics.screens.TestScreen;
import ftw.game.graphics.screens.WorldScreen;
import ftw.game.item.Item;
import ftw.game.ship.CrewMember;
import ftw.game.ship.Ship;
import ftw.game.world.World;

public class FTWGame extends Game
{
    private Assets m_assets;

    private Camera m_camera;
    private Viewport m_hud_viewport;
    private Viewport m_game_viewport;
    private SpriteBatch m_batch;

    private LoadingScreen m_loading_screen;
    private int m_loading_screen_frames;

    private Map<Screen.Event, Screen> m_loaded_screens;
    private Stack<Screen> m_screens;

    private World m_world;
    private Player m_player;

    public Assets assets()
    {
        return m_assets;
    }

    public World world()
    {
        return m_world;
    }

    public Player player()
    {
        return m_player;
    }

    @Override
    public void create()
    {
        m_assets = new Assets().load();

        m_camera = new OrthographicCamera(1280, 720);
        m_hud_viewport = new ScreenViewport();
        m_game_viewport = new ExtendViewport(1280, 720, m_camera);
        m_batch = new SpriteBatch();

        m_loading_screen = new LoadingScreen(this, m_batch, m_hud_viewport);
        m_loading_screen_frames = 0;
        m_loaded_screens = new EnumMap<>(Screen.Event.class);
        m_screens = new Stack<>();

        m_world = new World();

        CrewMember captain = new CrewMember("Jack Sparrow");
        CrewMember crew = new CrewMember("Joshamee Gibbs").equip(Item.create(Item.Type.Sword));
        Ship ship = new Ship("Black Pearl", captain, 1000).board(crew);

        m_player = new Player(ship, m_world.startingLocation());
    }

    @Override
    public void render()
    {
        if (m_assets.update()) {
            if (m_loading_screen_frames > 0) {
                if (m_loading_screen_frames == 30) {
                    m_loading_screen.setProgress(1);
                }

                m_loading_screen_frames--;
                m_loading_screen.render(Gdx.graphics.getDeltaTime());

                if (m_loaded_screens.size() == 0) {
                    handleEvent(EnumSet.of(Screen.Event.PUSH, Screen.Event.MAIN_MENU));
                }
            } else {
                super.render();
            }
        } else {
            m_loading_screen_frames = 30;
            m_loading_screen.setProgress(m_assets.progress());
            m_loading_screen.render(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void dispose()
    {
        m_assets.dispose();
        m_batch.dispose();
        m_loading_screen.dispose();

        for (Screen screen : m_screens) {
            screen.dispose();
        }
    }

    public void handleEvent(EnumSet<Screen.Event> event)
    {
        System.out.println(event);

        if (event.contains(Screen.Event.POP) && event.remove(Screen.Event.POP)) {
            m_screens.pop();
            setScreen(m_screens.peek());
            getScreen().resume();
        }

        if (event.contains(Screen.Event.PUSH) && event.remove(Screen.Event.PUSH)) {
            if (event.size() == 1) {
                Screen.Event e = event.iterator().next();

                if (!m_loaded_screens.containsKey(e)) {
                    switch (e) {
                        case MAIN_MENU:
                            m_loaded_screens.put(e, new MainMenuScreen(this, m_batch, m_hud_viewport));
                            break;
                        case WORLD:
                            m_loaded_screens.put(e, new WorldScreen(this, m_batch, m_hud_viewport, m_game_viewport, m_world));
                            break;
                        case TEST:
                            m_loaded_screens.put(e, new TestScreen(this, m_batch, m_hud_viewport));
                            break;
                        default:
                            break;
                    }
                }

                m_screens.push(m_loaded_screens.get(e));
                setScreen(m_screens.peek());
                getScreen().resume();
            }
        }
    }

    @SafeVarargs
    public final void handleEvent(EnumSet<Screen.Event>... events)
    {
        for (EnumSet<Screen.Event> event : events) {
            handleEvent(event);
        }
    }
}
