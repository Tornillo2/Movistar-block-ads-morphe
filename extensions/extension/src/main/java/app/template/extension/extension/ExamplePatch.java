package app.template.extension.extension;

import java.lang.reflect.Method;
import android.util.Log;

@SuppressWarnings("unused")
public class ExamplePatch {

    public static boolean shouldBlockAndSkip(Object playerDataModel) {
        if (playerDataModel == null) {
            return false;
        }
        try {
            // Check if isAds() returns true
            Method isAdsMethod = playerDataModel.getClass().getMethod("isAds");
            Boolean isAds = (Boolean) isAdsMethod.invoke(playerDataModel);

            // Check if getTypeOfContent() returns "PROMO_AD"
            Method getTypeOfContentMethod = playerDataModel.getClass().getMethod("getTypeOfContent");
            String typeOfContent = (String) getTypeOfContentMethod.invoke(playerDataModel);

            if ((isAds != null && isAds) || "PROMO_AD".equals(typeOfContent)) {
                Log.d("AntigravityPatch", "Intercepted ad playback! Blocking and notifying ended.");
                // Notify the webapp that the player has ended (skipped the ad)
                Class<?> mainActivityClazz = Class.forName("com.movistarplus.androidtv.MainActivity");
                Method fireEventMethod = mainActivityClazz.getMethod("fireEvent", String.class);
                fireEventMethod.invoke(null, "ended");
                return true;
            }
        } catch (Exception e) {
            Log.e("AntigravityPatch", "Error checking for ad in PlayerDataModel", e);
        }
        return false;
    }
}

