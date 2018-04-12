package ru.mitina.laba7.check;

import ru.mitina.laba7.Main;
import ru.mitina.laba7.orders.Credentials;
import ru.mitina.laba7.orders.Order;
import ru.mitina.laba7.orders.Orders;
import ru.mitina.laba7.orders.ShoppingCart;

public class GenerateOrders implements Runnable {

    private int CountClients;

    public GenerateOrders(int countClients) {
        CountClients = countClients;
    }

    public void run() {
        createOrders(CountClients);
    }

    public int getCountClients() {
        return CountClients;
    }

    public synchronized void createOrders(int countClients) {
        System.out.println("Автоматическая генерация заказов: ");

        Orders orders = new Orders();
        for (int i = 0; i < countClients; i++) {
            System.out.println("Регистрация клиента №" + (i + 1) + ": ");

            Credentials credential = new Credentials();
            ShoppingCart shoppingCart = new ShoppingCart();
            Main.randomCredential(credential);
            Main.randomShoppingCart(shoppingCart);
            orders.checkout(credential, shoppingCart);

            Order order = (Order) orders.returnOrderONE(i);
            Main.ordersProc.put(order.getIDOrder(), order);
        }
        System.out.println("Все заказы: ");
        orders.showOrdersAll();
    }
}
