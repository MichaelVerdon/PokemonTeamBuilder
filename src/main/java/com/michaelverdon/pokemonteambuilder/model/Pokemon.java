package com.michaelverdon.pokemonteambuilder.model;

import java.util.List;
import java.util.Map;

public class Pokemon {
    private int slot;
    private String name;
    private List<String> typing;
    private List<String> moveset;
    private Map<String, Integer> stats;

    public Pokemon() {}

    public Pokemon(int slot, String name, List<String> typing, List<String> moveset, Map<String, Integer> stats){
        this.slot = slot;
        this.name = name;
        this.typing = typing;
        this.moveset = moveset;
        this.stats = stats;
    }

    // Setters
    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    }

    public void setMoveset(List<String> moveset) {
        this.moveset = moveset;
    }

    public void setTyping(List<String> typing) {
        this.typing = typing;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters
    public int getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public List<String> getTyping() {
        return typing;
    }

    public List<String> getMoveset() {
        return moveset;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    @Override
    public String toString(){
        return String.format(
                "Slot: %d, Pokemon: %s, Typing: %s, Moveset: %s, Stats: %s",
                slot, name, typing, moveset, stats
        );
    }

}
