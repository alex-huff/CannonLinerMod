package phonis.cannonliner.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import phonis.cannonliner.networking.CTLine;
import phonis.cannonliner.state.CTLineManager;
import phonis.cannonliner.state.LineConsumer;

public class CTWorldRenderer {

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        CTWorldRenderer.drawLines(event.player, event.partialTicks);
    }

    public static void drawLines(EntityPlayer player, float partialTicks) {
        double posx = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
        double posy = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
        double posz = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1f);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glTranslated(-posx, -posy, -posz);
        CTLineManager.instance.forEachLineInWorld( // iterates through lines in world after getting lock
            new LineConsumer() {
                @Override
                public void accept(CTLine line) {
                    GL11.glColor4f(line.getR(), line.getG(), line.getB(), 1f);
                    GL11.glBegin(0x3);
                    GL11.glVertex3d(line.start.x, line.start.y, line.start.z);
                    GL11.glVertex3d(line.finish.x, line.finish.y, line.finish.z);
                    GL11.glEnd();
                }
            }
        );
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glLineWidth(1f);
        GL11.glTranslated(0, 0, 0);
        GL11.glPopMatrix();
    }

}
