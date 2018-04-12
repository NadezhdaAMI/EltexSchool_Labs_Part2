package ru.mitina.check;

import ru.mitina.orders.Order;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import static ru.mitina.Main.ordersProc;

/**
 * Created by Nadezhda on 17.11.2017.
 */
public class CheckAwaiting extends Acheck {

    public CheckAwaiting(TreeMap<UUID, Order> ordersCreate) {
        super();
    }

    public void run() {
//        for (int i = 0; i < oneOrders.getCountClients() - 5; i++) {
            testOrders(ordersProc);
//        }
    }

    private void testOrders(TreeMap<UUID, Order> ordersLoc) throws NullPointerException {
        System.out.println(Thread.currentThread().getName() + "начал работу:");
        try {
            for (Map.Entry<UUID, Order> item : ordersLoc.entrySet()) {
                if (item.getValue().getisAwaiting()) {
                    long timeObject = item.getValue().getTimeCreation();
                    long t = System.currentTimeMillis();
                    if ((t - timeObject) > item.getValue().getTimeWaiting()) {
//                        item.getValue().setIsAwaiting(false);
                        System.out.println("Заказ обработан! так как с момента создания заказа прошло "
                                + (t - timeObject) / 1000 + "sec," + " заказ: " + item.getValue().getIDOrder());
                    } else {
//                        System.out.println("Заказ еще в процессе обработки: " + item.getValue().getIDOrder());
                        try {
                            Thread.currentThread().sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("Заказов для обработки пока нет! Ждем регистрации... ");
        }
    }
}

