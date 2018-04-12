package ru.mitina.orders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Orders<T> implements Serializable { // orders это объединение данных клиента и корзины

    private List<T> ordersAll = new ArrayList<>(); //

    public int size(){
        return ordersAll.size();
    }

    public T returnOrderONE(int i){
        return ordersAll.get(i);
    }

    public void checkout(Credentials cr, ShoppingCart sh){
        T order = (T) new Order(sh, cr);
        ordersAll.add(order);
    }

    public void showOrdersAll(){
        for (Object k: ordersAll) {
        System.out.println(k.toString());
        }
    }

    @Override
    public String toString() {
        return " "+ ordersAll;
    }
}
