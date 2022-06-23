package it.uniroma3.siw.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainController {

    private static final String AbsPath = "src/main/resources/static";

    //Metodo necessario per il salvataggio dell'immagine
    protected static String SavePicture(String name, String path, MultipartFile image)
    {
        String pictureFolder = "";
        try {
            //Verifico la presenza di un'immagine
            if (!image.isEmpty()) {

                //Ottengo i bytes dell'immagine
                byte[] bytes = image.getBytes();
                File dir = new File(AbsPath +path);

                //Cerco la cartella
                if (!dir.exists())
                    dir.mkdirs();

                //Carico l'immagine
                File uploadFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
                outputStream.write(bytes);
                outputStream.close();

                //Imposto come nome dell'immagine l'id dell'oggetto.
                pictureFolder = path+name;
            }
            else
            {
                //Imposto l'immagine di default
                pictureFolder = path+"default.png";
            }

        } catch (Exception e) {
            //Nel caso in cui è stata lanciata qualche eccezione, imposto l'immagine di default
            pictureFolder = path+"default.png";
        }
        return pictureFolder;
    }

    protected static String SavePicture(String path, MultipartFile image)
    {
        return SavePicture(GenerateRandomString(), path, image);
    }

    private static String GenerateRandomString()
    {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();

        StringBuilder generatedString = new StringBuilder();

        for(int i=0;i<targetStringLength; i++)
        {
            /*
             * Since in java 8 the nextInt method can't accept 2 parameters,
             * This nextInt method starts from 0 and goes to the parameter.
             * So, the little cheat done here is:
             * value = minValue + randomValue(from 0 to maxValue - minValue)
            */
            int value = leftLimit+random.nextInt(rightLimit-leftLimit+1);
            generatedString.append((char) value);
        }
        return generatedString.toString()+".png";
    }
}

