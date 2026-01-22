package com.michaelverdon.pokemonteambuilder;

import com.michaelverdon.pokemonteambuilder.client.PokeApiClient;
import com.michaelverdon.pokemonteambuilder.dto.pokeapi.PokemonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PokeApiTests {

    @Autowired
    private PokeApiClient pokeApiClient;

    @Test
    public void testGetPokemonByNameResponse() {
        Mono<PokemonDto> monoPokemon = pokeApiClient.getPokemonByName("pikachu");

        StepVerifier.create(monoPokemon)
                .assertNext(pokemon -> {
                    assert pokemon.name().equalsIgnoreCase("pikachu");
                    assert pokemon.types() != null;
                    assert pokemon.stats() != null && !pokemon.stats().isEmpty();
                    assert pokemon.sprites() != null && !pokemon.sprites().front_default().isEmpty();
                })
                .verifyComplete();
    }

    @Test
    public void testGetPokemonByNameResponseWithTwoTypes() {
        Mono<PokemonDto> monoPokemon = pokeApiClient.getPokemonByName("charizard");

        StepVerifier.create(monoPokemon)
                .assertNext(pokemon -> {
                    assert pokemon.name().equalsIgnoreCase("charizard");
                    assert pokemon.types() != null;
                    assert pokemon.stats() != null && !pokemon.stats().isEmpty();
                    assert pokemon.sprites() != null && !pokemon.sprites().front_default().isEmpty();
                })
                .verifyComplete();
    }

    @Test
    public void testGetPokemonByNameResponseDetails() {
        Mono<PokemonDto> monoPokemon = pokeApiClient.getPokemonByName("pikachu");

        StepVerifier.create(monoPokemon)
                .assertNext(pokemon -> {
                    // Exact expected name
                    assertEquals("pikachu", pokemon.name().toLowerCase());

                    // Types - Pikachu has one type: electric
                    assertNotNull(pokemon.types());
                    assertFalse(pokemon.types().isEmpty());
                    assertEquals(1, pokemon.types().size());
                    assertEquals("electric", pokemon.types().get(0).type().name());

                    // Stats - verify presence and some known stats (base_stat values)
                    assertNotNull(pokemon.stats());
                    assertFalse(pokemon.stats().isEmpty());
                    // Example: Pikachu's speed base stat is 90
                    boolean speedStatFound = pokemon.stats().stream()
                            .anyMatch(stat -> stat.stat().name().equals("speed") && stat.base_stat() == 90);
                    assertTrue(speedStatFound, "Speed stat with value 90 should be present");

                    // Sprites - Pikachu's front_default sprite URL
                    assertNotNull(pokemon.sprites());
                    String expectedSpriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png";
                    assertEquals(expectedSpriteUrl, pokemon.sprites().front_default());
                })
                .verifyComplete();
    }

    @Test
    public void testGetPokemonByNameResponseDetailsTwoTypes() {
        Mono<PokemonDto> monoPokemon = pokeApiClient.getPokemonByName("charizard");

        StepVerifier.create(monoPokemon)
                .assertNext(pokemon -> {
                    assertEquals("charizard", pokemon.name().toLowerCase());

                    assertEquals(2, pokemon.types().size());
                    assertEquals("fire", pokemon.types().get(0).type().name());
                    assertEquals("flying", pokemon.types().get(1).type().name());

                    boolean speedStatFound = pokemon.stats().stream()
                            .anyMatch(stat -> stat.stat().name().equals("speed") && stat.base_stat() == 100);
                    assertTrue(speedStatFound, "Speed stat with value 100 should be present");

                    assertNotNull(pokemon.sprites());
                    String expectedSpriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png";
                    assertEquals(expectedSpriteUrl, pokemon.sprites().front_default());
                })
                .verifyComplete();
    }

    @Test
    public void testGetPokemonByNameResponseStats() {
        Mono<PokemonDto> monoPokemon = pokeApiClient.getPokemonByName("pikachu");

        StepVerifier.create(monoPokemon)
                .assertNext(pokemon -> {
                    assertEquals("pikachu", pokemon.name().toLowerCase());

                    boolean hpStatFound = pokemon.stats().stream()
                            .anyMatch(stat -> stat.stat().name().equals("hp") && stat.base_stat() == 35);
                    assertTrue(hpStatFound, "HP stat with value 35 should be present");

                    boolean attackStatFound = pokemon.stats().stream()
                            .anyMatch(stat -> stat.stat().name().equals("attack") && stat.base_stat() == 55);
                    assertTrue(attackStatFound, "Attack stat with value 55 should be present");

                    boolean defenseStatFound = pokemon.stats().stream()
                            .anyMatch(stat -> stat.stat().name().equals("defense") && stat.base_stat() == 40);
                    assertTrue(defenseStatFound, "Defense stat with value 40 should be present");

                    boolean specialAttackStatFound = pokemon.stats().stream()
                            .anyMatch(stat -> stat.stat().name().equals("special-attack") && stat.base_stat() == 50);
                    assertTrue(specialAttackStatFound, "Special Attack stat with value 50 should be present");

                    boolean specialDefenseStatFound = pokemon.stats().stream()
                            .anyMatch(stat -> stat.stat().name().equals("special-defense") && stat.base_stat() == 50);
                    assertTrue(specialDefenseStatFound, "Special Defence stat with value 50 should be present");

                    boolean speedStatFound = pokemon.stats().stream()
                            .anyMatch(stat -> stat.stat().name().equals("speed") && stat.base_stat() == 90);
                    assertTrue(speedStatFound, "Speed stat with value 90 should be present");

                })
                .verifyComplete();
    }
}

