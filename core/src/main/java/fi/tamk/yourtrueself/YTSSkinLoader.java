package fi.tamk.yourtrueself;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Custom Skin loader that creates a {@link YTSSkin}.
 */
public class YTSSkinLoader extends SkinLoader {
    /**
     * Create a custom skin object.
     *
     * @param resolver file resolver to use
     */
    public YTSSkinLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    /**
     * Overrides newSkin to create a YTSSkin instance instead of Skin.
     *
     * @param atlas atlas for the skin
     * @return Skin object
     */
    @Override
    protected Skin newSkin(TextureAtlas atlas) {
        return new YTSSkin(atlas);
    }
}
