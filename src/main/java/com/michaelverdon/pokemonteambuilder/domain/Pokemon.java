package com.michaelverdon.pokemonteambuilder.domain;

import java.util.List;
import java.util.Map;

public class Pokemon {
    private int slot;
    private String name;
    private List<String> typing;
    private List<String> moveset;
    private Map<String, Integer> stats;
    private String spriteURL;

    public Pokemon() {}

    public Pokemon(String name, List<String> typing, Map<String, Integer> stats, String spriteURL){
        this.name = name;
        this.typing = typing;
        this.stats = stats;
        this.spriteURL = spriteURL;
    }

    // Setters
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

    public void setSpriteURL(String spriteURL){ this.spriteURL = spriteURL; }

    // Getters

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

    public String getSpriteURL(){ return this.spriteURL; }

    @Override
    public String toString(){
        return String.format(
                "Pokemon: %s, Typing: %s, Moveset: %s, Stats: %s, SpriteURL: %s",
                name, typing, moveset, stats, spriteURL
        );
    }

}
