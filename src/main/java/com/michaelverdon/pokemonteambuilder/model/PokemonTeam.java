package com.michaelverdon.pokemonteambuilder.model;

import java.util.List;

//@Document(collection = "teams")
public class PokemonTeam {
    private String id;
    private String name;
    private List<Pokemon> pokemon;

    public PokemonTeam(){}

    public PokemonTeam(String id, String name, List<Pokemon> pokemon){
        this.id = id;
        this.name = name;
        this.pokemon = pokemon;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    @Override
    public String toString(){
        return String.format(
                "Team: %s, Pokemon: %s",
                this.id,
                this.pokemon.toString()
        );
    }
}
