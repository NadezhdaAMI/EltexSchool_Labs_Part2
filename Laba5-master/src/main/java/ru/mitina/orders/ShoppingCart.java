package ru.mitina.orders;

import ru.mitina.items.Electronics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ShoppingCart<T extends Electronics> implements Serializable {

    private List<T> shopCart = new LinkedList<>();

    public T search(String id){  // поиск товара в корзине по айди.
        for (T k: shopCart) {
            if (k.ID.toString().equals(id)){
                return k;
            }
        }
        return null;
    }

    public void showOrderIn(){
        for (Electronics k: shopCart) {
            System.out.println(k.toString());
        }
    }

    public ShoppingCart() {
        this.shopCart = shopCart;
    }

    public List<T> getShopCart() {
        return shopCart;
    }

    public void add(T object){
        shopCart.add(object);
    }

    public int shopCartSize(){
        return shopCart.size();
    }

    @Override                             //чтобы посмотреть заказы полностью с содерж корзины
    public String toString() {
        return " *:" + getShopCart();
    }
//
//    @Override
//    public String toString() {
//        return " *: There are electronics!";
//    }
}
