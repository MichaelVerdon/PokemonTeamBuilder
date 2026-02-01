package com.michaelverdon.pokemonteambuilder.exception;

public class DuplicateTeamException extends RuntimeException {
    public DuplicateTeamException(String message) {
        super(message);
    }
}
