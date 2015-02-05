package com.rubicon.rtb.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.Random;

public class WeightedCollection<E> {

    private final NavigableMap<Double, E> map;
    private final double total;
    private final Random random = new Random();

    private WeightedCollection(NavigableMap<Double, E> map, double total) {
        this.map = map;
        this.total = total;
    }

    public Collection<E> getValues() {
        return ImmutableList.copyOf(map.values());
    }

    public E next() {
        double value = random.nextDouble() * total;
        return !map.isEmpty() ? map.ceilingEntry(value).getValue() : null;
    }

    public static class Builder<E> {

        private double total = 0;
        private final ImmutableSortedMap.Builder<Double, E> builder = ImmutableSortedMap.naturalOrder();

        public void add(double weight, E item) {
            if (weight <= 0) return;
            total += weight;
            builder.put(total, item);
        }

        public WeightedCollection<E> build() {
            return new WeightedCollection<E>(builder.build(), total);
        }

    }

}
