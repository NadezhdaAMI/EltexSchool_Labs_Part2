package ru.mitina.laba7;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mitina.laba7.check.CheckAwaiting;
import ru.mitina.laba7.check.CheckProcessed;
import ru.mitina.laba7.check.GenerateOrders;
import ru.mitina.laba7.file.ManagerOrderFile;
import ru.mitina.laba7.file.ManagerOrderJSON;
import ru.mitina.laba7.items.Smartphone;
import ru.mitina.laba7.items.Tablet;
import ru.mitina.laba7.items.Telephone;
import ru.mitina.laba7.orders.Credentials;
import ru.mitina.laba7.orders.Order;
import ru.mitina.laba7.orders.ShoppingCart;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

@RestController
@RequestMapping(value = "/")

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

    public static void start() throws InterruptedException {

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

        managerOrderFile.readById(idOldOrder);
        ShoppingCart shopcartNew1 = new ShoppingCart();
        orderNew.setCart(randomShoppingCart(shopcartNew1));
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

        managerOrderJSON.readById(idOldOrder);
        ShoppingCart shopcartNew2 = new ShoppingCart();
        orderNew.setCart(randomShoppingCart(shopcartNew2));
        managerOrderJSON.saveById(idOldOrder, orderNew);

        for (Map.Entry<UUID, Order> element: ordersProc.entrySet()){
            System.out.println("ID заказа равен: " + element.getKey());
        }
    }

    //http://localhost:[port]/1?command=readall возвращаются все заказы в виде JSON.
    @RequestMapping(value = "/1", params = {"command"})
    public TreeMap<UUID, Order> readallJSON(@RequestParam("command") String cmd, Model map) {

        try {
            start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ordersProc;
    }

    // http://localhost:[port]/2?command=readById&order_id=
    @RequestMapping(value = "/2", params = {"command", "order_id"})
    public Order readOrderByIdJSON(@RequestParam("command") String cmd, @RequestParam("order_id") UUID id, Model map) {

        System.out.println("Поиск Id по заказам, ищем : ... " + id);
        Order order = new Order();
        for (Map.Entry<UUID, Order> item : ordersProc.entrySet()) {
            if (id.toString().equals(item.getKey().toString())){
                order = item.getValue();
                System.out.println("По вашему id запросу заказ найден!");
            }
        }
        return order;
    }

    // http://localhost:[port]/3?command=addToCard&card_id=871ce89e-b201-4469-8384-b45cb16ee8e8
    @RequestMapping(value = "/3", params = {"command", "card_id"})
    public String addToCardJSON(@RequestParam("command") String cmd, @RequestParam("card_id") UUID id, Model map) {

        Telephone telephone = new Telephone();
        telephone.create();
        ShoppingCart cart = new ShoppingCart();
        telephone.setID(id);
        cart.add(telephone);

        System.out.println("Новый товар доваблен в корзину с идентификатором: " + id);
        return "Новый товар доваблен в корзину с идентификатором: "+ id;
    }

    //http://localhost:[port]/4?command=delById&order_id=id
    @RequestMapping(value = "/4", params = {"command", "order_id"})
    public int delByIdJSON(@RequestParam("command") String cmd, @RequestParam("order_id") UUID id, Model map) {

        TreeMap<UUID, Order> ordersProcTmp = new TreeMap<>();
        int result = 404;

        System.out.println("Ищем заказ с id равным : ... " + id);
        for (Map.Entry<UUID, Order> item : ordersProc.entrySet()) {
            if (id.toString().equals(item.getKey().toString())){
                System.out.println("По вашему id запросу заказ удален! Id удаленного заказа " + id);
                result = 0;
            }
            else ordersProcTmp.put(item.getKey(), item.getValue());
        }

        System.out.println("Id оставшихся товаров: ");
        for (Map.Entry<UUID, Order> item : ordersProcTmp.entrySet()){
            System.out.println(item.getKey());
        }

        return result;
    }
}
