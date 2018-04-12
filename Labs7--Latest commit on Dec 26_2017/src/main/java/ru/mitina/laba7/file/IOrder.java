package ru.mitina.laba7.file;

import ru.mitina.laba7.orders.Order;

import java.io.IOException;
import java.util.TreeMap;
import java.util.UUID;

public interface IOrder {

    public void saveById(UUID id, Order ord);

    public Order readById(UUID id);

    public void saveAll(TreeMap<UUID, Order> ordersLoc);

    public void readAll() throws IOException, ClassNotFoundException;

}
