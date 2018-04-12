package ru.mitina.items;

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;


public class Tablet extends Electronics implements ICrudAction, Serializable { //3 type
    public transient String mDevice;
    enum VideoProc {Nvidia_Tegra, Intel_Atom, RockChip};
    VideoProc TypeofVideoProc;

    String[] ScreenresolArr = {"7dm", "12dm", "16dm"};
    String TypeofScreenresol;

    public Tablet(UUID IDelectronics) {
        super(IDelectronics);
    }

    public VideoProc getTypeofVideoProc() {
        return TypeofVideoProc;
    }

    public String getTypeofScreenresol() {
        return TypeofScreenresol;
    }

    @Override
    public void create()  {
        Random random = new Random();

        ID = UUID.randomUUID();

        TypeofVideoProc = VideoProc.values()[random.nextInt(VideoProc.values().length)];

        TypeofScreenresol = ScreenresolArr[random.nextInt(ScreenresolArr.length)];

        String[] nameArr = {"Apple", "Samsung", "ASUS"};
        int index_Name = random.nextInt(nameArr.length);
        Name = nameArr[index_Name];

        int[] priceArr = {2400, 2450, 2500, 1700};
        int index_Price = random.nextInt(priceArr.length);
        Price = priceArr[index_Price];

        String[] nameCoArr = {"Apple Co.", "Samsung Co.", "ASUS Co."};
        int index_NameCo = random.nextInt(nameCoArr.length);
        NameCompany = nameCoArr[index_NameCo];

        String[] modelArr = {"t1", "t2", "t5"};
        int index_model = random.nextInt(modelArr.length);
        Model = modelArr[index_model];

        String[] NameOperSystemArr = {"1.2", "2.5", "2.8", "7.1"};
        int index_NameOperSystem = random.nextInt(NameOperSystemArr.length);
        NameOperSystem = NameOperSystemArr[index_NameOperSystem];

        CountElectronics++;
    }

    @Override
    public void read() {

        mDevice = ID + ", " + TypeofVideoProc.toString() + ", " + TypeofScreenresol + ", " + Name + ", "
                + Price + "$, " + NameCompany + ", " + Model + ", " + NameOperSystem;

        System.out.println(mDevice);

    }

    @Override
    public void update() {

        System.out.println("Добавим объект:");
        Scanner n = new Scanner(System.in);
        System.out.println("Введите имя: ");
        Name = n.next();
        System.out.println("Введите цену: ");
        Price = n.nextInt();
        System.out.println("Введите название компании: ");
        NameCompany = n.next();
        System.out.println("Введите модель: ");
        Model = n.next();
        System.out.println("Введите название ОС: ");
        NameOperSystem = n.next();

        CountElectronics++;

        System.out.println("Объект добавлен!");

    }

    @Override
    public void delete() {
        ID = null;
        TypeofScreenresol = null;
        Name = null;
        Price = 0;
        NameCompany = null;
        Model = null;
        NameOperSystem = null;
        TypeofVideoProc = null;
        CountElectronics--;
    }

    @Override
    public String toString() {
        return "Tablet: " + ID + ", " + TypeofVideoProc.toString() + ", " + TypeofScreenresol + ", " + Name + ", "
                + Price + "$, " + NameCompany + ", " + Model + ", " + NameOperSystem + "\n";
    }
}
