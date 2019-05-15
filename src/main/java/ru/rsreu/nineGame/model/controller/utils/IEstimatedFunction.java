package ru.rsreu.nineGame.model.controller.utils;

import ru.rsreu.nineGame.model.data.State;

public interface IEstimatedFunction {

    int estimate(State stateToEstimate);
}
