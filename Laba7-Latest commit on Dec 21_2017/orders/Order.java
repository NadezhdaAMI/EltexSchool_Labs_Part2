package ru.mitina.laba7.orders;

import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {

    private ShoppingCart cart;

    private Credentials cred;

    private UUID IDOrder;

    private transient long TimeCreation;
    private transient long TimeWaiting;

    private transient final long TimeWaitingONE = 100; // время обработки одного товара в корзине

    private transient long dateCreation;

    public transient static volatile boolean isAwaiting = false;
    public transient static volatile boolean isProcessed = false;

    public Order() {
    }

    public Credentials getCred() {
        return cred;
    }

    public ShoppingCart getCart() {
        return cart;
    }


    public boolean getisAwaiting() {
        return isAwaiting;
    }

    public boolean getisProcessed() {
        return isProcessed;
    }

    public static void setIsAwaiting(boolean isAwaiting) {
        Order.isAwaiting = isAwaiting;
    }

    public static void setIsProcessed(boolean isProcessed) {
        Order.isProcessed = isProcessed;
    }

    public Order(ShoppingCart cart, Credentials cred) {
        this.cart = cart;
        this.cred = cred;
        this.dateCreation = System.currentTimeMillis();
        setTimeCreation(this.dateCreation);
        this.setTimeWaiting(TimeWaitingONE*(cart.shopCartSize()));
        this.IDOrder = UUID.randomUUID();
        isAwaiting = true;
    }

    public void setTimeWaiting(long timeWaiting) {
        TimeWaiting = timeWaiting;
    }

    public long getTimeWaiting() {
        return TimeWaiting;
    }

    public void setTimeCreation(long timeCreation) {
        TimeCreation = timeCreation;
    }

    public long getTimeCreation() {
        return TimeCreation;
    }

    public UUID getIDOrder() {
        return IDOrder;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return " " + /*getIDOrder() + ", " +*/ cred.toString() + getCart();
    }
}
