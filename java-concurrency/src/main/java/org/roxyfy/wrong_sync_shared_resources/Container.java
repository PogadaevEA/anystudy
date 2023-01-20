package org.roxyfy.wrong_sync_shared_resources;

import java.util.ArrayList;
import java.util.List;

public class Container {
    public static final List<String> list = new ArrayList<>();

    public synchronized void addEntry(String value) {
        list.add(value);
    }
}
