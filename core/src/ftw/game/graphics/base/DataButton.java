package ftw.game.graphics.base;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class DataButton<T> extends TextButton
{
    private T m_data;

    public DataButton(String text, TextButtonStyle style, T data)
    {
        super(text, style);
        m_data = data;
    }

    public DataButton(String text, Skin skin, T data)
    {
        super(text, skin);
        m_data = data;
    }

    public T data()
    {
        return m_data;
    }
}
