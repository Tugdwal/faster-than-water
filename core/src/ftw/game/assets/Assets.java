package ftw.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;

public class Assets extends AssetManager
{
    private final static String FONT_FOLDER = "fonts/";
    private final static String SKIN_FOLDER = "skins/";
    private final static String TEXTURE_FODLER = "images/";

    private final static String FONT_BLACK_PEARL_FILE = "BlackPearl.ttf";
    private final static String SKIN_FLAT_FILE = "skin_flat.json";
    private final static String TEXTURE_SHIP_SIDE_FILE = "ship_side.png";

    private final static int FONT_SIZE_SMALL = 16;
    private final static int FONT_SIZE_MEDIUM = 32;
    private final static int FONT_SIZE_LARGE = 64;

    public final static AssetDescriptor<BitmapFont> FONT_BLACK_PEARL = new AssetDescriptor<>(FONT_BLACK_PEARL_FILE, BitmapFont.class, TTF_Parameter(FONT_FOLDER + FONT_BLACK_PEARL_FILE, dp(FONT_SIZE_MEDIUM)));
    public final static AssetDescriptor<BitmapFont> FONT_BLACK_PEARL_SMALL = new AssetDescriptor<>(FONT_SIZE_SMALL + FONT_BLACK_PEARL_FILE, BitmapFont.class, TTF_Parameter(FONT_FOLDER + FONT_BLACK_PEARL_FILE, dp(FONT_SIZE_SMALL)));
    public final static AssetDescriptor<BitmapFont> FONT_BLACK_PEARL_LARGE = new AssetDescriptor<>(FONT_SIZE_LARGE + FONT_BLACK_PEARL_FILE, BitmapFont.class, TTF_Parameter(FONT_FOLDER + FONT_BLACK_PEARL_FILE, dp(FONT_SIZE_LARGE)));
    public final static AssetDescriptor<BitmapFont> FONT_DEFAULT = FONT_BLACK_PEARL;

    public final static AssetDescriptor<Skin> SKIN_FLAT = new AssetDescriptor<>(SKIN_FOLDER + SKIN_FLAT_FILE, Skin.class, new GeneratedFontSkinLoader.GeneratedFontSkinParameter(FONT_BLACK_PEARL, FONT_BLACK_PEARL_SMALL, FONT_BLACK_PEARL_LARGE));
    public final static AssetDescriptor<Skin> SKIN_DEFAULT = SKIN_FLAT;

    public final static AssetDescriptor<Texture> TEXTURE_SHIP_SIDE = new AssetDescriptor<>(TEXTURE_FODLER + TEXTURE_SHIP_SIDE_FILE, Texture.class);

    {
        setLogger(new Logger("Debug", Logger.DEBUG));

        FileHandleResolver resolver = new InternalFileHandleResolver();

        setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        setLoader(Skin.class, new GeneratedFontSkinLoader(resolver));
    }

    public float progress()
    {
        return getProgress();
    }

    public Assets load()
    {
        load(SKIN_DEFAULT);
        load(TEXTURE_SHIP_SIDE);

        return this;
    }

    @Override
    public void dispose()
    {
        // Required because Skin doesn't support GeneratedFont dependency
        get(SKIN_DEFAULT).getAll(BitmapFont.class).clear();

        super.dispose();
    }

    private static int dp(int size)
    {
        return (int) (size * 160 * Gdx.graphics.getDensity() / 100);
    }

    private static FreeTypeFontLoaderParameter TTF_Parameter(String filename, int font_size)
    {
        FreeTypeFontLoaderParameter parameter = new FreeTypeFontLoaderParameter();
        parameter.fontFileName = filename;
        parameter.fontParameters.size = font_size;

        return parameter;
    }
}
