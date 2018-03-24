package ic.aiczone.katologfilm.models;

import java.util.ArrayList;

/**
 * Created by aic on 23/03/18.
 */

public class FilmsData {
    public static String[][] data = new String[][]{
            {"Soekarno", "Presiden Pertama RI", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/418px-Presiden_Sukarno.jpg"},
            {"Soeharto", "Presiden Kedua RI", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/President_Suharto%2C_1993.jpg/468px-President_Suharto%2C_1993.jpg"},
            {"Bacharuddin Jusuf Habibie", "Presiden Ketiga RI", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Bacharuddin_Jusuf_Habibie_official_portrait.jpg/520px-Bacharuddin_Jusuf_Habibie_official_portrait.jpg"},
            {"Abdurrahman Wahid", "Presiden Keempat RI", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/President_Abdurrahman_Wahid_-_Indonesia.jpg/486px-President_Abdurrahman_Wahid_-_Indonesia.jpg"},
            {"Megawati Soekarnoputri", "Presiden Kelima RI", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/President_Megawati_Sukarnoputri_-_Indonesia.jpg/540px-President_Megawati_Sukarnoputri_-_Indonesia.jpg"},
            {"Susilo Bambang Yudhoyono", "Presiden Keenam RI", "https://upload.wikimedia.org/wikipedia/commons/5/58/Presiden_Susilo_Bambang_Yudhoyono.png"},
            {"Joko Widodo", "Presiden Ketujuh RI", "https://upload.wikimedia.org/wikipedia/commons/1/1c/Joko_Widodo_2014_official_portrait.jpg"}
    };

    public static ArrayList<Films> getListDataNow(){
        Films president = null;
        ArrayList<Films> list = new ArrayList<>();
        for (int i = 0; i <data.length; i+=2) {
            president = new Films();
            president.setTitle(data[i][0]);
            president.setDescription(data[i][1]);
            president.setLinkImage(data[i][2]);

            list.add(president);
        }

        return list;
    }

    public static ArrayList<Films> getListDataNext(){
        Films president = null;
        ArrayList<Films> list = new ArrayList<>();
        for (int i = 1; i <data.length; i+=2) {
            president = new Films();
            president.setTitle(data[i][0]);
            president.setDescription(data[i][1]);
            president.setLinkImage(data[i][2]);

            list.add(president);
        }

        return list;
    }
}

