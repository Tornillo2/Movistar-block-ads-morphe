package app.template.patches.example

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.string
import com.android.tools.smali.dexlib2.AccessFlags

object InitializePlayerFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "V",
    parameters = listOf(
        "Landroid/net/Uri;",
        "J",
        "Z",
        "Lcom/movistarplus/androidtv/models/PlayerDataModel;",
        "Ljava/lang/String;"
    ),
    filters = listOf(
        string("initializePlayer")
    ),
    custom = { _, classDef ->
        classDef.type == "Lcom/movistarplus/androidtv/player/PlayerTV;"
    }
)
