package Komprimierer;

import java.util.LinkedList;

interface CastHelper<G, T> {
    void cast(LinkedList<G> list, T item);
}
