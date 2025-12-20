package org.zyneonstudios.apex.utilities.events;

import java.util.UUID;

public interface Event {

    UUID getUUID();

    boolean execute();
}