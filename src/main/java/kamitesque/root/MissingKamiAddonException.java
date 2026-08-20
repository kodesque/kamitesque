package kamitesque.root;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;

public class MissingKamiAddonException extends CustomModLoadingErrorDisplayException {

    @Override
    public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {
    }

    @Override
    public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseX, int mouseY, float partialTicks) {

        errorScreen.drawCenteredString(
                fontRenderer,
                TextFormatting.BOLD + "Kamitesque" + TextFormatting.RESET + " requires ONE of the following mods to run:",
                errorScreen.width / 2,
                90,
                0xFFFFFF);

        errorScreen.drawCenteredString(
                fontRenderer,
                TextFormatting.BOLD + "KAMI: Reborn" + TextFormatting.RESET + " (kami-1.0.6 or above)",
                errorScreen.width / 2,
                110,
                0xFFFFFF);

        errorScreen.drawCenteredString(
                fontRenderer,
                TextFormatting.BOLD + "Thaumic Tinkerer Unofficial" + TextFormatting.RESET + " (thaumictinkerer-1.12.2-5.9.15 or above)",
                errorScreen.width / 2,
                130,
                0xFFFFFF);
    }
}