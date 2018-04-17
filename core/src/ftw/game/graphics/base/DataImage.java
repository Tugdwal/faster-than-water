package ftw.game.graphics.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class DataImage<T> extends Image
{
    private T m_data;

    public DataImage(Texture texture, T data)
    {
        super(texture);
        m_data = data;
    }

    public DataImage(Drawable drawable, T data)
    {
        super(drawable);
        m_data = data;
    }

    public T data()
    {
        return m_data;
    }

    public void update(UpdateFunction<Image, T> function)
    {
        function.execute(this, m_data);
    }
}
