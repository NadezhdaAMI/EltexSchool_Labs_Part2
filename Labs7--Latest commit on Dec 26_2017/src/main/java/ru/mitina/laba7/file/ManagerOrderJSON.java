package ru.mitina.laba7.file;

import com.fasterxml.jackson.core.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import ru.mitina.laba7.orders.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class ManagerOrderJSON extends AManageOrder {        //для хранения заказов в виде текстового файла в формате JSON

    private static final String FILENAME = "/home/user/EltexSchool /Java_labs/laba7/Orders.json";
    private static final String FILENAMETMP = "/home/user/EltexSchool /Java_labs/laba7/OrdersTmp.json";

    @Override
    public Order readById(UUID idOrder) {
        System.out.println("Поиск заказа по ID в файле .json: ");
        System.out.println("Ищем... " + idOrder);
        ObjectMapper objectMapper = new ObjectMapper();
        Order ord = null;
        try {
            TreeMap<UUID, Order> ordersLoc = (TreeMap<UUID, Order>)objectMapper.readValue(new File(FILENAME),
                    TreeMap.class);
            for (Map.Entry<UUID, Order> item : ordersLoc.entrySet()) {
                System.out.println(idOrder);
                System.out.println(item.getKey());

                if (idOrder.toString().equals(item.getKey())){
                    System.out.println("Заказ с данным ID найден!");
                    System.out.println("Id: " + item.getKey());
                }
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Поиск заказа по ID в файле .json выполнен успешно!");
        return ord;
    }

    @Override
    public void saveById(UUID id, Order ord) {
        System.out.println("Cохранение нового заказа по ID в файле .json: ");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TreeMap<UUID, Order> ordersLoc = objectMapper.readValue(new File(FILENAME), TreeMap.class);
            for (Map.Entry<UUID, Order> item : ordersLoc.entrySet()) {
                if (id.toString().equals(item.getKey())){
                    item.setValue(ord);
                    System.out.println("Заказ с данным ID изменен!");
                }
            }
            objectMapper.writeValue(new FileOutputStream(FILENAMETMP), ordersLoc);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        File file = new File(FILENAMETMP);
        File fileOut = new File(FILENAME);
        file.renameTo(fileOut);

        System.out.println("Изменение заказа с данным ID в файле .json выполнен успешно.");
    }

    @Override
    public void saveAll(TreeMap<UUID, Order> ordersLoc) {
        System.out.println("Cохранение в формате JSON: ");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("Orders.json"), ordersLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cохранение в формате JSON выполнено успешно! ");
    }

    @Override
    public void readAll() throws IOException, ClassNotFoundException, FileNotFoundException {
        System.out.println("Чтение данных формата json из файла: ");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TreeMap<UUID, Order> ordersLoc = objectMapper.readValue(new File(FILENAME),
                    TreeMap.class);
            for (Map.Entry<UUID, Order> item : ordersLoc.entrySet()) {
                System.out.println(item.getKey());
                System.out.println(item.getValue());
            }
            System.out.println("Данные из файла:");
            System.out.println(ordersLoc);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        }

        System.out.println("Чтение данных формата json из файла выполнено успешно!");

    }
}
