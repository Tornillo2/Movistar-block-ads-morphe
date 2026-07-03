package app.template.patches.example

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.shared.Constants.COMPATIBILITY_MOVISTAR

private const val EXTENSION_CLASS = "Lapp/template/extension/ExamplePatch;"

@Suppress("unused")
val blockAdsPatch = bytecodePatch(
    name = "Block Ads",
    description = "Blocks advertisements and promo clips.",
    default = true
) {
    compatibleWith(COMPATIBILITY_MOVISTAR)

    extendWith("extensions/extension.mpe")

    execute {
        InitializePlayerFingerprint.method.addInstructions(
            0,
            """
                invoke-static {p4}, $EXTENSION_CLASS;->shouldBlockAndSkip(Ljava/lang/Object;)Z
                move-result v0
                if-eqz v0, :cond_continue
                return-void
                :cond_continue
            """
        )
    }
}

