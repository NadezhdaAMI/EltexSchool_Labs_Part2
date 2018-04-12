package ru.mitina.laba7.file;

import ru.mitina.laba7.Main;
import ru.mitina.laba7.orders.Order;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class ManagerOrderFile extends AManageOrder {  //для хранения заказов в виде двоичного файла.

    @Override
    public Order readById(UUID idOrder) {
        Order ord = null;
        System.out.println("Чтение заказов по ID: ");
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("OrdersBin.bin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < Main.COUNTPEOPLE; i++) {
                if (idOrder.compareTo((UUID) objectInputStream.readObject()) == 0){
                    System.out.println("Заказ с данным ID найден!");
                    ord = (Order) objectInputStream.readObject();
                    System.out.println("Заказ: ");
                    System.out.println(ord.toString());
                }
                else{
                    objectInputStream.readObject();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ord;
    }

    @Override
    public void saveById(UUID id, Order ord) {
        Order orderLoc = null;
        System.out.println("Сохранение заказа в файл по ID: ");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("OrdersBinTmp.bin"))) {

            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream("OrdersBin.bin"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                UUID tmpUUID;
                while ((tmpUUID = (UUID) objectInputStream.readObject()) != null){
                    orderLoc = (Order) objectInputStream.readObject();
                    if (id.equals(tmpUUID)){
                        objectOutputStream.writeObject(id);
                        objectOutputStream.writeObject(ord);
                        System.out.println("Заказ с данным ID успешно изменен!");
                        System.out.println("Заказ: ");
                        System.out.println(ord.toString());
                    }
                    objectOutputStream.writeObject(tmpUUID);
                    objectOutputStream.writeObject(orderLoc);
                    if (objectInputStream.read() == -1)
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            File file = new File("OrdersBinTmp.bin");
            File fileOut = new File("OrdersBin.bin");
            file.renameTo(fileOut);
        }
    }

    @Override
    public void saveAll(TreeMap<UUID, Order> ordersLoc) {
        System.out.println("Сохранение заказов в файл... ");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("OrdersBin.bin"))) {
            for (Map.Entry<UUID, Order> item : ordersLoc.entrySet()) {
                objectOutputStream.writeObject(item.getKey());
                objectOutputStream.writeObject(item.getValue());
            }
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Сериализация выполнена успешно!");
    }

    @Override
    public void readAll() throws IOException, ClassNotFoundException {
        System.out.println("Чтение заказов из файла... ");
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("OrdersBin.bin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < Main.COUNTPEOPLE; i++) {
                UUID id = (UUID) objectInputStream.readObject();
                System.out.print(id + " ");
                Order ord = (Order) objectInputStream.readObject();
                System.out.println(ord.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Десериализация выполнена успешно!");
    }
}


