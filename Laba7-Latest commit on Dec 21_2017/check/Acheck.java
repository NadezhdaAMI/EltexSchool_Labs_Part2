package ru.mitina.laba7.check;

import ru.mitina.laba7.orders.Order;

import java.util.TreeMap;
import java.util.UUID;

public abstract class Acheck implements Runnable {
    private TreeMap<UUID, Order> orders;
    private int countAwaiting;
    private int countProcessed;
}
