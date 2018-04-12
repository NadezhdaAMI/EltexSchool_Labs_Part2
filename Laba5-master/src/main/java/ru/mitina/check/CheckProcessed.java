package ru.mitina.check;

import ru.mitina.Main;
import ru.mitina.orders.Order;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by Nadezhda on 17.11.2017.
 */
public class CheckProcessed extends Acheck {

    public CheckProcessed(TreeMap<UUID, Order> ordersProc) {
        super();
    }

    public void run() {
        testOrdersPros(Main.ordersProc);
    }

    private void testOrdersPros(TreeMap<UUID, Order> ordersLoc) throws NullPointerException {
        System.out.println(Thread.currentThread().getName() + "начал работу: ");
        try {
            for (Map.Entry<UUID, Order> element: ordersLoc.entrySet()){
                if ((element.getValue().getisAwaiting()) && !(element.getValue().getisProcessed())){
                    System.out.println("Заказ удален! ID удаленного заказа: " + element.getValue().getIDOrder());
//                    ordersLoc.remove(element.getKey());
//                    element.getValue().setIsProcessed(true);
                } else {
//                    System.out.println("Время ожидания заказа еще не истекло: " + element.getValue().getIDOrder());
                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("Заказов для обработки пока нет! Ждем регистрации... ");
        }
    }
}
