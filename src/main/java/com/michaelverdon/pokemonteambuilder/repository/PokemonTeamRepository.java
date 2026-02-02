package com.michaelverdon.pokemonteambuilder.repository;

import com.michaelverdon.pokemonteambuilder.domain.PokemonTeam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PokemonTeamRepository extends MongoRepository<PokemonTeam, UUID> {
    Optional<PokemonTeam> findByName(String name);
    void deleteByName(String name);
}
