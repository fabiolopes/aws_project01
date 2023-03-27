package br.com.bios.aws_project01.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DogRepository {

    private static List<String> DOGS = new ArrayList<>();

    public List<String> list(){
        return DOGS;
    }

    public void insert(String name) {
        DOGS.add(name);
    }

    public void remove(String name) {
        DOGS.remove(name);
    }
}
