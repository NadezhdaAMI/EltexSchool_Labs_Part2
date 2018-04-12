package ru.mitina.check;

import ru.mitina.orders.Order;

import java.util.TreeMap;
import java.util.UUID;

public abstract class Acheck implements Runnable {
    private TreeMap<UUID, Order> orders;
    private int countAwaiting;
    private int countProcessed;
}
