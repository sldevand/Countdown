package fr.sldeveloperand.countdown.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;

import fr.sldeveloperand.countdown.R;

public class ColorUtils {

    private static void darkenStatusBar(AppCompatActivity activity, int color) {
        activity.getWindow().setStatusBarColor(darkenColor(color));
    }

    @ColorInt
    private static int darkenColor(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    public static void setToolbarColorWithBitmap(final AppCompatActivity activity, final Toolbar toolbar, final Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(@NonNull Palette p) {
                Palette.Swatch vibrantSwatch = p.getLightVibrantSwatch();

                int backgroundColor = ContextCompat.getColor(activity,
                        R.color.colorPrimary);
                int textColor = ContextCompat.getColor(activity,
                        android.R.color.primary_text_dark);

                if (vibrantSwatch != null) {
                    backgroundColor = vibrantSwatch.getRgb();
                    textColor = vibrantSwatch.getTitleTextColor();
                }
                darkenStatusBar(activity, backgroundColor);

                toolbar.setBackgroundColor(backgroundColor);
                toolbar.setTitleTextColor(textColor);
            }
        });
    }
}
