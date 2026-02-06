package com.michaelverdon.pokemonteambuilder.service;

import com.michaelverdon.pokemonteambuilder.client.PokeApiClient;
import com.michaelverdon.pokemonteambuilder.domain.Pokemon;
import com.michaelverdon.pokemonteambuilder.domain.PokemonTeam;
import com.michaelverdon.pokemonteambuilder.dto.pokeapi.PokemonDto;
import com.michaelverdon.pokemonteambuilder.dto.pokeapi.SpritesDto;
import com.michaelverdon.pokemonteambuilder.dto.pokeapi.StatDto;
import com.michaelverdon.pokemonteambuilder.dto.pokeapi.TypeSlotDto;
import com.michaelverdon.pokemonteambuilder.exception.DuplicateTeamException;
import com.michaelverdon.pokemonteambuilder.exception.InvalidTeamException;
import com.michaelverdon.pokemonteambuilder.exception.TeamNotFoundException;
import com.michaelverdon.pokemonteambuilder.repository.PokemonTeamRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class PokemonTeamService {

    private final PokemonTeamRepository repository;
    private final PokeApiClient pokeApiClient;

    public PokemonTeamService(PokemonTeamRepository repository, PokeApiClient pokeApiClient){
        this.repository = repository;
        this.pokeApiClient = pokeApiClient;
    }

    public PokemonTeam createPokemonTeam(PokemonTeam pokemonTeam) {
        if (pokemonTeam.getPokemon().size() > 6 || pokemonTeam.getPokemon().isEmpty()){
            throw new InvalidTeamException("Pokemon team needs between 1-6 members");
        }
        if (repository.findByName(pokemonTeam.getName()).isPresent()){
            throw new DuplicateTeamException(String.format("Pokemon team with name: %s already exists, please use a unique name.", pokemonTeam.getName()));
        }
        return repository.save(pokemonTeam);
    }

    public Optional<PokemonTeam> getPokemonTeamByName(String name) {
        return Optional.ofNullable(repository.findByName(name)
                .orElseThrow(() ->
                        new TeamNotFoundException(
                                String.format("Pokemon team with name: %s not found", name)
                        )
                ));
    }

    public List<PokemonTeam> getAllPokemonTeams() {
        return repository.findAll();
    }

    public void deletePokemonTeam(String name) {
        repository.deleteByName(name);
    }

    public void deleteAllTeams() {
        repository.deleteAll();
    }

    public PokemonTeam updatePokemonTeam(PokemonTeam pokemonTeam) {
        if (pokemonTeam.getId() == null) {
            throw new TeamNotFoundException("Cannot update non-existent team");
        }

        repository.findById(pokemonTeam.getId())
                .orElseThrow(() -> new TeamNotFoundException("Team with ID " + pokemonTeam.getId() + " not found"));
        repository.save(pokemonTeam);
        return pokemonTeam;
    }

    // Helper methods
    private Pokemon mapToDomain(PokemonDto dto){
        String name = dto.name();
        List<String> types = this.mapTypes(dto.types());
        Map<String, Integer> stats = this.mapStats(dto.stats());
        String spriteURL = this.mapSprite(dto.sprites());
        return new Pokemon(
                name,
                types,
                stats,
                spriteURL
        );
    }

    public Pokemon getPokemonByName(String name){
        Mono<PokemonDto> dto = this.pokeApiClient.getPokemonByName(name);
        return mapToDomain(Objects.requireNonNull(dto.block()));
    }

    private Map<String, Integer> mapStats(List<StatDto> dto){
        Map<String, Integer> statMap = new HashMap<>();
        for (StatDto statDto : dto) {
            statMap.put(statDto.stat().name(), statDto.base_stat());
        }
        return statMap;
    }

    private List<String> mapTypes(List<TypeSlotDto> dto){
        List<String> typesList = new ArrayList<>();
        for (TypeSlotDto typeSlotDto : dto) {
            typesList.add(typeSlotDto.type().name());
        }
        return typesList;
    }

    private String mapSprite(SpritesDto dto){
        return dto.front_default();
    }
}
