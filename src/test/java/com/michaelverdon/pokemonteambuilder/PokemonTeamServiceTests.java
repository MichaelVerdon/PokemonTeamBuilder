package com.michaelverdon.pokemonteambuilder;

import com.michaelverdon.pokemonteambuilder.client.PokeApiClient;
import com.michaelverdon.pokemonteambuilder.domain.Pokemon;
import com.michaelverdon.pokemonteambuilder.repository.PokemonTeamRepository;
import com.michaelverdon.pokemonteambuilder.service.PokemonTeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PokemonTeamServiceTests {

    @Autowired
    private PokeApiClient pokeApiClient;
    @Autowired
    private PokemonTeamRepository pokemonTeamRepository;

    @Test
    public void pokemonTeamRepositoryInit(){
        PokemonTeamService pokemonTeamService = new PokemonTeamService(pokemonTeamRepository, pokeApiClient);
        assertThat(pokemonTeamService).isNotNull();
    }

    @Test
    public void pokemonTeamRepositoryFetchPokemonByName(){
        PokemonTeamService pokemonTeamService = new PokemonTeamService(pokemonTeamRepository, pokeApiClient);
        Pokemon pokemon = pokemonTeamService.getPokemonByName("pikachu");
        assertThat(pokemon).isNotNull();
        assertThat(pokemon.getName()).isEqualTo("pikachu");
        assertThat(pokemon.getTyping()).isEqualTo(List.of("electric"));
        assertThat(pokemon.getStats()).isEqualTo(Map.of(
                "hp", 35,
                "attack", 55,
                "defense", 40,
                "special-attack", 50,
                "special-defense", 50,
                "speed", 90
        ));
        assertThat(pokemon.getSpriteURL()).isEqualTo("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png");
    }

    @Test
    public void pokemonTeamRepositoryFetchPokemonByNameWithTwoTypes(){
        PokemonTeamService pokemonTeamService = new PokemonTeamService(pokemonTeamRepository, pokeApiClient);
        Pokemon pokemon = pokemonTeamService.getPokemonByName("charizard");
        assertThat(pokemon).isNotNull();
        assertThat(pokemon.getName()).isEqualTo("charizard");
        assertThat(pokemon.getTyping()).isEqualTo(List.of("fire", "flying"));
        assertThat(pokemon.getStats()).isEqualTo(Map.of(
                "hp", 78,
                "attack", 84,
                "defense", 78,
                "special-attack", 109,
                "special-defense", 85,
                "speed", 100
        ));
        assertThat(pokemon.getSpriteURL()).isEqualTo("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png");
    }
}
