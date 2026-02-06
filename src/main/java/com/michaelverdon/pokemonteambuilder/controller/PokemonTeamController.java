package com.michaelverdon.pokemonteambuilder.controller;

import com.michaelverdon.pokemonteambuilder.domain.PokemonTeam;
import com.michaelverdon.pokemonteambuilder.service.PokemonTeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PokemonTeamController {

    private final PokemonTeamService pokemonTeamService;

    public PokemonTeamController(PokemonTeamService pokemonTeamService) {
        this.pokemonTeamService = pokemonTeamService;
    }

    @GetMapping
    public ResponseEntity<List<PokemonTeam>> getAllTeams(){
        List<PokemonTeam> teams = pokemonTeamService.getAllPokemonTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("team/{name}")
    public ResponseEntity<Optional<PokemonTeam>> getPokemonTeamByName(@PathVariable String name){
        Optional<PokemonTeam> team = pokemonTeamService.getPokemonTeamByName(name);
        return ResponseEntity.ok(team);
    }
// TODO: Implement this in service
//    @GetMapping("team/{id}")
//    public ResponseEntity<Optional<PokemonTeam>> getPokemonTeamById(@PathVariable UUID id){
//        Optional<PokemonTeam> team = pokemonTeamService.getPokemonTeamById(id);
//        return ResponseEntity.ok(team);
//    }

    @PostMapping
    public ResponseEntity<PokemonTeam> createPokemonTeam(@RequestBody PokemonTeam team){
        PokemonTeam createdTeam = pokemonTeamService.createPokemonTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(team);
    }

    @PutMapping("team/{id}")
    public ResponseEntity<PokemonTeam> updatePokemonTeam(@PathVariable UUID id, @RequestBody PokemonTeam team){
        team.setId(id);
        PokemonTeam updatedTeam = pokemonTeamService.updatePokemonTeam(team);
        return ResponseEntity.ok(team);
    }
// TODO: Make delete by name delete by Id, better safety
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> deletePokemonTeam(@PathVariable UUID id){
//        pokemonTeamService.deletePokemonTeam(id);
//        return ResponseEntity.status(HttpStatus.OK);
//    }

}
