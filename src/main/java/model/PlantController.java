package model;

import core.LSystemRule;
import core.impl.trees.BinaryTree;
import core.impl.trees.KochSnowflake;
import core.impl.trees.SimplePlant;
import core.impl.trees.StochasticBinaryTree;

import java.awt.Color;
import java.util.ArrayList;

public class PlantController {
    private ArrayList<Plant> plants;
    private int currentIndex = 0;
    private static PlantController instance;

    private PlantController() {
        plants = new ArrayList<>();
        setDefaultPlants();
        PlantController.instance = this;
    }

    public Plant getPlant() {
        return plants.get(currentIndex);
    }

    private void setDefaultPlants() {
        registerPlant(
                PlantBuilder.start(new StochasticBinaryTree(), "Stochastic Binary Tree")
                        .setLineColor(Color.getHSBColor(0.083f, 0.6f, 0.4f))
                        .build());
        registerPlant(
                PlantBuilder.start(new BinaryTree(), "Binary Tree")
                        .setLineColor(Color.getHSBColor(0.083f, 0.6f, 0.4f))
                        .build());
        registerPlant(
                PlantBuilder.start(new SimplePlant(), "Simple Plant")
                        .setLineColor(Color.getHSBColor(0.3333f, 0.8f, 0.7f))
                        .build());
        registerPlant(
                PlantBuilder.start(new KochSnowflake(), "Koch Snow Flake")
                        .setLineColor(Color.BLUE)
                        .build());
    }

    public PlantController shiftRight() {
        currentIndex = (currentIndex + 1) % plants.size();

        return this;
    }

    public PlantController shiftLeft() {
        currentIndex = (currentIndex - 1 + plants.size()) % plants.size();

        return this;
    }

    private void registerPlant(Plant plant) {
        if (plant != null) plants.add(plant);
    }

    public LSystemRule getRule() {
        return plants.get(currentIndex).getRule();
    }

    public String getName() {
        return plants.get(currentIndex).getName();
    }

    public static PlantController getInstance() {
        if (PlantController.instance != null) return PlantController.instance;
        return new PlantController();
    }
}
