package ru.mitina;
//Вариант 2
//        Список объектов продажи элетроники 3-х видов: телефоны, сматфоны и планшеты.

import ru.mitina.check.CheckAwaiting;
import ru.mitina.check.CheckProcessed;
import ru.mitina.check.GenerateOrders;
import ru.mitina.file.ManagerOrderFile;
import ru.mitina.file.ManagerOrderJSON;
import ru.mitina.items.Smartphone;
import ru.mitina.items.Tablet;
import ru.mitina.items.Telephone;
import ru.mitina.orders.Credentials;
import ru.mitina.orders.Order;
import ru.mitina.orders.ShoppingCart;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

public class Main {

    public static int COUNTPEOPLE = 7;              //исправить на случайную величину заказов

    public static TreeMap<UUID, Order> ordersProc = new TreeMap<>();

    public static GenerateOrders oneOrders = new GenerateOrders(COUNTPEOPLE);

    public static Credentials randomCredential(Credentials cred){

        Random random = new Random();
        cred.IDClient = UUID.randomUUID();

        String[] surnameArr = {"Ivanov", "Petrov", "Makarov"};
        int index_surname = random.nextInt(surnameArr.length);
        cred.Surname = surnameArr[index_surname];

        String[] nameArr = {"Alex", "Djon", "Gena"};
        int index_name = random.nextInt(nameArr.length);
        cred.Name = nameArr[index_name];

        String[] middlenameArr = {"A.", "D.", "B."};
        int index_middlename = random.nextInt(middlenameArr.length);
        cred.MiddleName = middlenameArr[index_middlename];

        String[] emailArr = {"nn19005@mail.ru", "ooolkl003@yandex.ru", "spanchbob3@gmail.com"};
        int index_email = random.nextInt(emailArr.length);
        cred.Email = emailArr[index_email];

        return cred;
    }
    public static ShoppingCart randomShoppingCart(ShoppingCart shopcart){
        UUID ID;
        int k = 5;
        Random random= new Random();
        int T = random.nextInt(k);
        for (int i = 0; i < T; i++) {
            ID = UUID.randomUUID();
            Telephone telephone = new Telephone(ID);
            telephone.create();
            shopcart.add(telephone);
        }
        int S = random.nextInt(k);
        for (int i = 0; i < S; i++) {
            ID = UUID.randomUUID();
            Smartphone smartphone = new Smartphone(ID);
            smartphone.create();
            shopcart.add(smartphone);
        }
        int Tb = random.nextInt(k);
        for (int i = 0; i < Tb; i++) {
            ID = UUID.randomUUID();
            Tablet tablet = new Tablet(ID);
            tablet.create();
            shopcart.add(tablet);
        }
        return shopcart;
    }

    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(oneOrders);
        threadOne.start();
        try {
            threadOne.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CheckAwaiting checkAwaiting = new CheckAwaiting(ordersProc);
        Thread threadAwaiting = new Thread(checkAwaiting);
        threadAwaiting.setName("Поток №1 поиска оформленных заказов ");
        threadAwaiting.start();
        CheckProcessed checkProcessed = new CheckProcessed(ordersProc);
        Thread threadProcessed = new Thread(checkProcessed);
        threadProcessed.setName("Поток №2 поиска обработанных заказов ");
        threadProcessed.start();

        threadAwaiting.join();
        threadProcessed.join();

        ManagerOrderFile manFile = new ManagerOrderFile();
        manFile.saveAll(ordersProc);

        ManagerOrderFile managerOrderFile = new ManagerOrderFile();
        try {
            managerOrderFile.readAll();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UUID idOldOrder = null;
        Order orderNew = null;
        for (Map.Entry<UUID, Order> element: ordersProc.entrySet()){ //берем первый заказ и проверим, если ли он в файле
            idOldOrder = element.getKey();
            orderNew = element.getValue();
            break;
        }
//        Order orderOld = managerOrderFile.readById(idOldOrder);
//        orderOld.setCart(new ShoppingCart());
        managerOrderFile.readById(idOldOrder);
        ShoppingCart shopcartNew = new ShoppingCart();
        orderNew.setCart(randomShoppingCart(shopcartNew));
        managerOrderFile.saveById(idOldOrder, orderNew);

        ManagerOrderJSON managerOrderJSON = new ManagerOrderJSON();
        managerOrderJSON.saveAll(ordersProc);
        try {
            managerOrderJSON.readAll();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
