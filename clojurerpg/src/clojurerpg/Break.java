package clojurerpg;

import clojure.lang.PersistentHashMap;

/**
 * Created by daniel.johnson on 5/19/2017.
 */
public class Break extends Exception {
    private final PersistentHashMap state;
    private final PersistentHashMap commands;
    public Break(PersistentHashMap state, PersistentHashMap commands) {
        super();
        this.state = state;
        this.commands = commands;
    }

    public PersistentHashMap getState() {
        return state;
    }

    public PersistentHashMap getCommands() {
        return commands;
    }
}
