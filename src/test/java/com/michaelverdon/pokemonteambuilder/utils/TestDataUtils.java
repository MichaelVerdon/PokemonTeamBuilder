package com.michaelverdon.pokemonteambuilder.utils;

import com.michaelverdon.pokemonteambuilder.domain.Pokemon;
import com.michaelverdon.pokemonteambuilder.service.PokemonTeamService;

import java.util.List;
import java.util.Map;

public class TestDataUtils {

    public static Pokemon createPokemon() {
        Pokemon pokemon = new Pokemon(
                "pikachu",
                List.of("electric"),
                Map.of(
                        "hp", 35,
                        "attack", 55,
                        "defense", 40,
                        "special-attack", 50,
                        "special-defense", 50,
                        "speed", 90
                ),
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
        );
        pokemon.setMoveset(List.of("thunderbolt", "quick-attack"));
        return pokemon;
    }

    public static Pokemon createUpdatedPokemon() {
        Pokemon pokemon = createPokemon();
        pokemon.setMoveset(List.of("thunder", "thunderbolt"));
        return pokemon;
    }
}
