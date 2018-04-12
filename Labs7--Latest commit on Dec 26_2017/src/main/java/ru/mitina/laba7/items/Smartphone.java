package ru.mitina.laba7.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

@Entity
@Table(name = "smartphone")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"SimcardCase"},
        allowGetters = true)

public class Smartphone extends Electronics implements ICrudAction, Serializable {   //2 type

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    enum SimcardCase {usual, micro_Sim};

    @NotBlank
    SimcardCase TypeofSimcard;

    @NotBlank
    int NumberOfSimCard;

    @NotBlank
    public transient String mDevice;

    public Smartphone(UUID IDelectronics) {
        super(IDelectronics);
    }

    public Smartphone() {
    }

    public SimcardCase getTypeofSimcard() {
        return TypeofSimcard;
    }

    public int getNumberOfSimCard() {
        return NumberOfSimCard;
    }

    @Override
    public void create() {
        Random random = new Random();

        ID = UUID.randomUUID();

        TypeofSimcard = SimcardCase.values()[random.nextInt(SimcardCase.values().length)];

        int[] numerSimCa = {1, 2};
        int index_number = random.nextInt(numerSimCa.length);
        NumberOfSimCard = numerSimCa[index_number];


        String[] nameArr = {"Apple", "Samsung", "Nokia", "ASUS"};
        int index_Name = random.nextInt(nameArr.length);
        Name = nameArr[index_Name];

        int[] priceArr = {1400, 1450, 1500, 1700};
        int index_Price = random.nextInt(priceArr.length);
        Price = priceArr[index_Price];

        String[] nameCoArr = {"Apple Co.", "Samsung Co.", "Nokia Co.", "ASUS Co."};
        int index_NameCo = random.nextInt(nameCoArr.length);
        NameCompany = nameCoArr[index_NameCo];

        String[] modelArr = {"sm1", "sm2", "sm3", "sm5"};
        int index_model = random.nextInt(modelArr.length);
        Model = modelArr[index_model];

        String[] NameOperSystemArr = {"5.2", "3.5", "97.8", "3.1"};
        int index_NameOperSystem = random.nextInt(NameOperSystemArr.length);
        NameOperSystem = NameOperSystemArr[index_NameOperSystem];

        CountElectronics++;
    }

    @Override
    public void read() {

        mDevice = ID + ", " + TypeofSimcard.toString() + ", " + NumberOfSimCard + "simka(i), "
                + Name + ", " + Price + "$, " + NameCompany + ", " + Model + ", " + NameOperSystem;

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
        NumberOfSimCard = 0;
        Name = null;
        Price = 0;
        NameCompany = null;
        Model = null;
        NameOperSystem = null;
        CountElectronics--;
    }

    @Override
    public String toString() {
        return "Smartphone: " + ID + ", " + TypeofSimcard.toString() + ", " + NumberOfSimCard + "simka(i), "
                + Name + ", " + Price + "$, " + NameCompany + ", " + Model + ", " + NameOperSystem + "\n";
    }
}
