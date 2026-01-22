package com.michaelverdon.pokemonteambuilder.client;

import com.michaelverdon.pokemonteambuilder.dto.pokeapi.PokemonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PokeApiClient {

    private final WebClient webClient;

    @Autowired
    public PokeApiClient(WebClient webClient){
        this.webClient = webClient.mutate()
                .baseUrl("https://pokeapi.co/api/v2")
                .build();
    }

    public Mono<PokemonDto> getPokemonByName(String name){
        return this.webClient
                .get()
                .uri("/pokemon/{name}", name.toLowerCase())
                .retrieve()
                .bodyToMono(PokemonDto.class);
    }
}
