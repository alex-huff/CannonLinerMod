package dev.phonis.cannonliner.state;

import dev.phonis.cannonliner.networking.*;

import java.util.*;

public class CTLineManager {

    public static final CTLineManager instance = new CTLineManager();
    private static Set<CTLineType> tntTypes;
    private static Set<CTLineType> sandTypes;
    private static Set<CTLineType> playerTypes;

    static {
        CTLineManager.tntTypes = new TreeSet<CTLineType>();

        CTLineManager.tntTypes.add(CTLineType.TNT);
        CTLineManager.tntTypes.add(CTLineType.TNTENDPOS);

        CTLineManager.sandTypes = new TreeSet<CTLineType>();

        CTLineManager.sandTypes.add(CTLineType.SAND);
        CTLineManager.sandTypes.add(CTLineType.SANDENDPOS);

        CTLineManager.playerTypes = new TreeSet<CTLineType>();

        CTLineManager.playerTypes.add(CTLineType.PLAYER);
    }

    private final List<CTLine> lines = new ArrayList<CTLine>();

    public synchronized void forEachLineInWorld(LineConsumer consumer) {
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
        Iterator<CTLine> iterator = lines.iterator();

        while(iterator.hasNext()) {
            CTLine line = iterator.next();

            if (types.contains(line.type)) {
                iterator.remove();
            }
        }
    }

}
