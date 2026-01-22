package com.michaelverdon.pokemonteambuilder.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record PokemonDto(
        String name,
        @JsonProperty List<TypeSlotDto> types,
        @JsonProperty List<StatDto> stats,
        @JsonProperty SpritesDto sprites) {}

