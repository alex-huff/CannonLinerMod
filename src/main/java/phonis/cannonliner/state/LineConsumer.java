package phonis.cannonliner.state;

import phonis.cannonliner.networking.CTLine;

public interface LineConsumer {

    void accept(CTLine line);

}
