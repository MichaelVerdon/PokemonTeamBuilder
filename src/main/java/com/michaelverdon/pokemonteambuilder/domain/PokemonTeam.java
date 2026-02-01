package com.michaelverdon.pokemonteambuilder.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Document(collection="PokemonTeams")
public class PokemonTeam {
    @Id
    private UUID id;
    @Field(name = "name")
    private String name;
    @Field(name = "pokemon")
    private List<Pokemon> pokemon;

    public PokemonTeam(){}

    public PokemonTeam(UUID id, String name, List<Pokemon> pokemon){
        this.id = id;
        this.name = name;
        this.pokemon = pokemon;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    // Getters
    public UUID getId() {
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
