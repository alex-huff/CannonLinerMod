package dev.phonis.cannonliner.state;

import dev.phonis.cannonliner.networking.*;

import java.util.*;
import java.util.function.Consumer;

public class CTLineManager {

    public static final CTLineManager instance = new CTLineManager();
    private static Set<CTLineType> tntTypes;
    private static Set<CTLineType> sandTypes;
    private static Set<CTLineType> playerTypes;

    static {
        CTLineManager.tntTypes = new TreeSet<>();

        CTLineManager.tntTypes.add(CTLineType.TNT);
        CTLineManager.tntTypes.add(CTLineType.TNTENDPOS);

        CTLineManager.sandTypes = new TreeSet<>();

        CTLineManager.sandTypes.add(CTLineType.SAND);
        CTLineManager.sandTypes.add(CTLineType.SANDENDPOS);

        CTLineManager.playerTypes = new TreeSet<>();

        CTLineManager.playerTypes.add(CTLineType.PLAYER);
    }

    private final List<CTLine> lines = new ArrayList<>();

    public synchronized void forEachLineInWorld(Consumer<CTLine> consumer) {
        for (CTLine line : lines) {
            consumer.accept(line);
        }
    }

    public synchronized void addArtifacts(CTNewArtifacts ctNewArtifacts) {
        for (CTArtifact artifact : ctNewArtifacts.artifacts) {
            this.lines.addAll(artifact.makeLines());
        }
    }

    public synchronized void addLines(CTNewLines ctNewLines) {
        this.lines.addAll(ctNewLines.lines);
    }

    public synchronized void clearByType(CTLineType type) {
        if (type.equals(CTLineType.ALL)) {
            this.lines.clear();

            return;
        }

        if (type.equals(CTLineType.TNT)) {
            this.clearByTypes(CTLineManager.tntTypes);
        } else if (type.equals(CTLineType.SAND)) {
            this.clearByTypes(CTLineManager.sandTypes);
        } else if (type.equals(CTLineType.PLAYER)){
            this.clearByTypes(CTLineManager.playerTypes);
        }
    }

    public synchronized void clearByTypes(Set<CTLineType> types) {
        lines.removeIf(line -> types.contains(line.type));
    }

}
