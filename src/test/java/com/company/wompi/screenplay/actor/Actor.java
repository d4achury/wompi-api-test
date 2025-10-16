package com.company.wompi.screenplay.actor;

import com.company.wompi.screenplay.ability.CallAnApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Actor {
    private final String name;
    private final Map<Class<?>, Object> abilities = new HashMap<>();

    public Actor(String name) {
        this.name = name;
    }

    public <T> void can(T ability) {
        abilities.put(ability.getClass(), ability);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> abilityTo(Class<T> clazz) {
        return Optional.ofNullable((T) abilities.get(clazz));
    }

    public String getName() {
        return name;
    }
}
