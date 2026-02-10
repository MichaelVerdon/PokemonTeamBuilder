package com.michaelverdon.pokemonteambuilder;

import com.michaelverdon.pokemonteambuilder.client.PokeApiClient;
import com.michaelverdon.pokemonteambuilder.domain.Pokemon;
import com.michaelverdon.pokemonteambuilder.domain.PokemonTeam;
import com.michaelverdon.pokemonteambuilder.repository.PokemonTeamRepository;
import com.michaelverdon.pokemonteambuilder.service.PokemonTeamService;
import com.michaelverdon.pokemonteambuilder.utils.TestDataUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class PokemonTeamControllerTests {

    @LocalServerPort int port;

    @Autowired
    RestTestClient restTestClient;

    @Autowired
    private PokemonTeamRepository pokemonTeamRepository;
    @Autowired
    private PokeApiClient pokeApiClient;

    private UUID id1 = UUID.randomUUID();
    private UUID id2 = UUID.randomUUID();


    @BeforeEach
    public void populateDatabase() {
        pokemonTeamRepository.deleteAll();
        PokemonTeamService pokemonTeamService = new PokemonTeamService(pokemonTeamRepository, pokeApiClient);

        List<Pokemon> teamList = List.of(TestDataUtils.createPokemon(), TestDataUtils.createUpdatedPokemon());
        PokemonTeam team = new PokemonTeam(id1, "Test-Team-1", teamList);
        PokemonTeam team2 = new PokemonTeam(id2, "Test-Team-2", teamList);
        pokemonTeamService.createPokemonTeam(team);
        pokemonTeamService.createPokemonTeam(team2);
    }
    // [
    // {"id":"7e68de1f-65b8-4713-a703-f14f75260099",
    // "name":"Test-Team-1",
    // "pokemon":[{"name":"pikachu","typing":["electric"],
    // "stats":{"speed":90,"special-attack":50,"hp":35,"attack":55,"defense":40,"special-defense":50},
    // "spriteURL":"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
    // "moveset":["thunderbolt","quick-attack"]},
    // {"name":"pikachu","typing":["electric"],
    // "stats":{"speed":90,"special-attack":50,"hp":35,"attack":55,"defense":40,"special-defense":50},
    // "spriteURL":"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
    // "moveset":["thunder","thunderbolt"]}]},
    // {"id":"be7b3bf8-c04a-49dd-8df0-9f8174769ea4",
    // "name":"Test-Team-2","pokemon":[{"name":"pikachu","typing":["electric"],
    // "stats":{"speed":90,"special-attack":50,"hp":35,"attack":55,"defense":40,"special-defense":50},
    // "spriteURL":"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
    // "moveset":["thunderbolt","quick-attack"]},{"name":"pikachu","typing":["electric"],
    // "stats":{"speed":90,"special-attack":50,"hp":35,"attack":55,"defense":40,"special-defense":50},
    // "spriteURL":"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png","moveset":["thunder","thunderbolt"]}]}
    // ]
    @Test
    public void pokemonTeamControllerGetTeams() {
        restTestClient.get()
                .uri("http://localhost:%d/pokemonteams/team/all".formatted(port))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].name").isEqualTo("Test-Team-1")
                .jsonPath("$[0].pokemon[0].name").isEqualTo("pikachu")
                .jsonPath("$[1].id").exists();
    }

    @Test
    public void pokemonTeamControllerSearchByName() {
        restTestClient.get()
                .uri("http://localhost:%d/pokemonteams/team/search/Test-Team-1".formatted(port))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$.name").isEqualTo("Test-Team-1")
                .jsonPath("$.pokemon[0].name").isEqualTo("pikachu")
                .jsonPath("$.pokemon[1].name").isEqualTo("pikachu")
                .jsonPath("$.id").exists();
    }

    @Test
    public void pokemonTeamControllerCreateTeam() {
        List<Pokemon> pokemon = List.of(TestDataUtils.createPokemon(), TestDataUtils.createUpdatedPokemon());
        PokemonTeam team = new PokemonTeam(UUID.randomUUID(), "Test-Team-3", pokemon);
        restTestClient.post()
                .uri("/pokemonteams")
                .body(team)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").isEqualTo("Test-Team-3");

        restTestClient.get()
                .uri("http://localhost:%d/pokemonteams/team/all".formatted(port))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3);
    }

    @Test
    public void pokemonTeamControllerUpdateTeam() {
        List<Pokemon> pokemon = List.of(TestDataUtils.createPokemon(), TestDataUtils.createUpdatedPokemon());
        UUID teamId = UUID.randomUUID();
        PokemonTeam team = new PokemonTeam(teamId, "Test-Team-3", pokemon);
        restTestClient.post()
                .uri("/pokemonteams")
                .body(team)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").isEqualTo("Test-Team-3");

        pokemon = List.of(TestDataUtils.createPokemon(), TestDataUtils.createUpdatedPokemon(), TestDataUtils.createPokemon());
        PokemonTeam updatedTeam = new PokemonTeam(teamId, "Test-Team-3-renamed", pokemon);

        restTestClient.put()
                .uri("/pokemonteams/team/%s".formatted(teamId))
                .body(updatedTeam)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(teamId.toString())
                .jsonPath("$.name").isEqualTo("Test-Team-3-renamed");

    }

    @Test
    public void pokemonTeamControllerDeleteTeam() {

        AtomicReference<String> teamId = new AtomicReference<>();
        restTestClient.get()
                .uri("http://localhost:%d/pokemonteams/team/search/Test-Team-1".formatted(port))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id")
                .value(id -> teamId.set(id.toString()));

        restTestClient.delete()
                .uri("/pokemonteams/%s".formatted(teamId))
                .exchange()
                .expectStatus().isNoContent();

        restTestClient.get()
                .uri("http://localhost:%d/pokemonteams/team/all".formatted(port))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Test-Team-2")
                .jsonPath("$[0].pokemon[0].name").isEqualTo("pikachu")
                .jsonPath("$[0].id").exists();
    }

}
