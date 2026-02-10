package com.michaelverdon.pokemonteambuilder.controller;

import com.michaelverdon.pokemonteambuilder.domain.PokemonTeam;
import com.michaelverdon.pokemonteambuilder.service.PokemonTeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pokemonteams")
public class PokemonTeamController {

    private final PokemonTeamService pokemonTeamService;

    public PokemonTeamController(PokemonTeamService pokemonTeamService) {
        this.pokemonTeamService = pokemonTeamService;
    }

    @GetMapping("/team/all")
    public ResponseEntity<List<PokemonTeam>> getAllTeams(){
        List<PokemonTeam> teams = pokemonTeamService.getAllPokemonTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/team/search/{name}")
    public ResponseEntity<Optional<PokemonTeam>> getPokemonTeamByName(@PathVariable String name){
        Optional<PokemonTeam> team = pokemonTeamService.getPokemonTeamByName(name);
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<PokemonTeam> createPokemonTeam(@RequestBody PokemonTeam team){
        PokemonTeam createdTeam = pokemonTeamService.createPokemonTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @PutMapping("/team/{id}")
    public ResponseEntity<PokemonTeam> updatePokemonTeam(@PathVariable UUID id, @RequestBody PokemonTeam team){
        team.setId(id);
        PokemonTeam updatedTeam = pokemonTeamService.updatePokemonTeam(team);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePokemonTeam(@PathVariable UUID id){
        pokemonTeamService.deleteByTeamId(id);
        return ResponseEntity.noContent().build();
    }

}
