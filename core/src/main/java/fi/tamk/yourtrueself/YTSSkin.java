package fi.tamk.yourtrueself;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

/**
 * Custom Skin class that adds support for .ttf fonts.
 * Uses {@link FreeTypeFontGenerator} to generate bitmap fonts.
 */
public class YTSSkin extends Skin {
    /**
     * Create new YTSSkin instance with given atlas.
     *
     * @param atlas atlas to use
     */
    public YTSSkin(TextureAtlas atlas) {
        super(atlas);
    }

    /**
     * Overrides BitmapFont loader with a custom one that adds support for .ttf fonts.
     * <p>
     * Copied from libgdx implementation and added .ttf support with a "dpSize" parameter for
     * display independent pixel size.
     *
     * @param skinFile skin file handle to load
     * @return json file to load
     * @see Skin#getJsonLoader(FileHandle)
     */
    @Override
    protected Json getJsonLoader(final FileHandle skinFile) {
        Json json = super.getJsonLoader(skinFile);

        final Skin skin = this;

        json.setSerializer(BitmapFont.class, new Json.ReadOnlySerializer<BitmapFont>() {
            public BitmapFont read(Json json, JsonValue jsonData, Class type) {
                String path = json.readValue("file", String.class, jsonData);
                int scaledSize = json.readValue("scaledSize", int.class, -1, jsonData);
                Boolean flip = json.readValue("flip", Boolean.class, false, jsonData);
                Boolean markupEnabled = json.readValue("markupEnabled", Boolean.class, false, jsonData);

                FileHandle fontFile = skinFile.parent().child(path);
                if (!fontFile.exists()) fontFile = Gdx.files.internal(path);
                if (!fontFile.exists())
                    throw new SerializationException("Font file not found: " + fontFile);

                // new: Add handling for .ttf font generation
                if (path.endsWith(".ttf")) {
                    int dpSize = json.readValue("dpSize", int.class, 16, jsonData);

                    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
                    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                    parameter.size = (int) (dpSize * Gdx.graphics.getDensity());
                    BitmapFont font = generator.generateFont(parameter);
                    generator.dispose();

                    return font;
                }
                // end new

                // Use a region with the same name as the font, else use a PNG file in the same directory as the FNT file.
                String regionName = fontFile.nameWithoutExtension();
                try {
                    BitmapFont font;
                    Array<TextureRegion> regions = skin.getRegions(regionName);
                    if (regions != null)
                        font = new BitmapFont(new BitmapFont.BitmapFontData(fontFile, flip), regions, true);
                    else {
                        TextureRegion region = skin.optional(regionName, TextureRegion.class);
                        if (region != null)
                            font = new BitmapFont(fontFile, region, flip);
                        else {
                            FileHandle imageFile = fontFile.parent().child(regionName + ".png");
                            if (imageFile.exists())
                                font = new BitmapFont(fontFile, imageFile, flip);
                            else
                                font = new BitmapFont(fontFile, flip);
                        }
                    }
                    font.getData().markupEnabled = markupEnabled;
                    // Scaled size is the desired cap height to scale the font to.
                    if (scaledSize != -1) font.getData().setScale(scaledSize / font.getCapHeight());
                    return font;
                } catch (RuntimeException ex) {
                    throw new SerializationException("Error loading bitmap font: " + fontFile, ex);
                }
            }
        });

        return json;
    }
}