package ftw.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class GeneratedFontSkinLoader extends AsynchronousAssetLoader<Skin, GeneratedFontSkinLoader.GeneratedFontSkinParameter>
{
    private SkinLoader loader;

    public GeneratedFontSkinLoader(FileHandleResolver resolver)
    {
        super(resolver);
        loader = new SkinLoader(resolver);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, GeneratedFontSkinParameter parameter)
    {
        Array<AssetDescriptor> deps = loader.getDependencies(fileName, file, parameter);
        deps.addAll(parameter.fonts);

        return deps;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, GeneratedFontSkinParameter parameter)
    {
    }

    @Override
    public Skin loadSync(AssetManager manager, String fileName, FileHandle file, GeneratedFontSkinParameter parameter)
    {
        ObjectMap<String, Object> resources = new ObjectMap<>();
        
        for (AssetDescriptor<BitmapFont> descriptor : parameter.fonts) {
            resources.put(descriptor.fileName, manager.get(descriptor));
        }

        return loader.loadSync(manager, fileName, file, new SkinLoader.SkinParameter(parameter.textureAtlasPath, resources));
    }

    static public class GeneratedFontSkinParameter extends SkinLoader.SkinParameter
    {
        public final AssetDescriptor<BitmapFont>[] fonts;

        @SafeVarargs
        public GeneratedFontSkinParameter(AssetDescriptor<BitmapFont>... descriptors)
        {
            fonts = descriptors;
        }
    }
}
