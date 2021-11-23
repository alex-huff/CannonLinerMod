package dev.phonis.cannonliner.schemutils;

import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.util.BlockPos;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;

public class SchemUtils {

    public static BlockPos schemOrigin = new BlockPos(0, 0, 0);
    public static BlockPos currentPosition = new BlockPos(0, 0, 0);
    public static boolean isTiedToSchem = false;

    public static Pair<ISchematic, BlockPos> getLoadedSchem() {
        try {
            Class<?> renderSchematicClass = Class.forName("com.github.lunatrius.schematica.client.renderer.RenderSchematic");
            Field worldField = renderSchematicClass.getDeclaredField("world");

            worldField.setAccessible(true);

            Field instanceField = renderSchematicClass.getDeclaredField("INSTANCE");
            Object renderSchematic = worldField.get(instanceField.get(null));
            Field schematicField = renderSchematic.getClass().getDeclaredField("schematic");

            schematicField.setAccessible(true);

            Field positionField = renderSchematic.getClass().getDeclaredField("position");

            positionField.setAccessible(true);

            BlockPos position = (BlockPos) positionField.get(renderSchematic);

            return new ImmutablePair<>((ISchematic) schematicField.get(renderSchematic), position);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

}
