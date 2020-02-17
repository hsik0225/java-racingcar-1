package racingcar.domain;

import racingcar.message.Message;
import racingcar.splitter.NameSplitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2020 by 또동페어
 * All rights reserved.
 *
 * Cars.java
 * cars 담당하는 클래스
 *
 * @author      또동페어
 * @version     1.0
 * @date        12 Feb 2020
 *
 */
public class Cars implements Iterable<Car> {
    private List<Car> cars;

    public Cars(String rawCarNames) {
        List<String> carNames = NameSplitter.split(rawCarNames);
        cars = new ArrayList<>();
        for (String name : carNames) {
            cars.add(new Car(name));
        }
        this.cars = Collections.unmodifiableList(cars);
    }

    public Cars(List<Car> cars) {
        this.cars = Collections.unmodifiableList(cars);
    }

    public List<Car> findWinner() {
        Car maxPositionCar = findMaxPositionCar();
        return cars.stream()
                .filter(car -> car.isSamePosition(maxPositionCar))
                .collect(Collectors.toList());
    }

    private Car findMaxPositionCar() {
        return cars.stream()
                .reduce(Car::getFartherCar)
                .orElseThrow(() -> new NullPointerException(Message.EXCEPTION_CAR_IS_NULL.getMessageText()));
    }

    public void run() {
        for (Car car : cars) {
            car.run();
        }
    }

    @Override
    public Iterator<Car> iterator() {
        return cars.iterator();
    }
}
