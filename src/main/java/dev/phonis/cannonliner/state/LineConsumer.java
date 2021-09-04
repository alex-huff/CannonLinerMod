package dev.phonis.cannonliner.state;

import dev.phonis.cannonliner.networking.CTLine;

public interface LineConsumer {

    void accept(CTLine line);

}
