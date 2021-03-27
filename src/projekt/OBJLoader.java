package projekt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {


    /**
    *
    * Quelle:
     * *      ThinMatrix, "OpenGL 3D Game Tutorial 10: Loading 3D Models", 13.09.2014, https://youtu.be/YKFYtekgnP8, last visited on: 19.01.2021
    *
    *
     * Der Originalcode hat mit Inidices und Vektorklassen gearbeitet die dann an den Projektcode angepasst werden mussten.
     *
     * Dieser Code funktioniert nur, wenn jegliche Informationen wie Ecken, UV-Koordinaten und Normalen
     * über den Informationen zu den Faces stehen und zusätzlich diese 4 Information die einzigen in der .obj Datei sind.
    * */

    // zu übertragende Arrays
    static float[] eckenArray;
    static float[] uvArray;
    static float[] normalsArray;

    // Zähler für die Sortierung
    static int zweier,dreier;

    public static List<float[]> objDaten(String dateiName){
        // erstes Lesen der Datei um die Anzahl der Faces zu zählen
        FileReader count = null;
        try {
            count = new FileReader(new File("src/res/obj/"+dateiName+".obj"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load file");
            e.printStackTrace();
        }
        BufferedReader readerCounter = new BufferedReader(count);
        int counter = 0;
        try{
            String lineCounter;
            while((lineCounter = readerCounter.readLine())!=null){
                if(lineCounter.startsWith("f ")){
                    counter++;
                }
            }
            readerCounter.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        FileReader fr = null;
        try {
            fr = new FileReader(new File("src/res/obj/"+dateiName+".obj"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load file");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);

        String line;
        List<float[]> ecken = new ArrayList<>();
        List<float[]> uvKoordinaten = new ArrayList<>();
        List<float[]> normalen = new ArrayList<>();
        List<float[]> listeDerListen = new ArrayList<>();
        System.out.println(counter);
        eckenArray = new float[counter*9];
        uvArray = new float[counter*9];
        normalsArray = new float[counter*9];

        // lesen
        try{
            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){
                    float[] vertex = new float[]{Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])};
                    ecken.add(vertex);
                } else if(line.startsWith("vt ")){
                    float[] texture = new float[]{Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2])};
                    uvKoordinaten.add(texture);
                }else if(line.startsWith("vn ")){
                    float[] normal = new float[]{Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])};
                    normalen.add(normal);
                }else if(line.startsWith("f ")){
                    break;
                }
            }

            zweier = 0;
            dreier = 0;
            while(line!=null){
                if(!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                sortValues(vertex1,ecken,uvKoordinaten,normalen);
                sortValues(vertex2,ecken,uvKoordinaten,normalen);
                sortValues(vertex3,ecken,uvKoordinaten,normalen);
                line = reader.readLine();
            }
            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        // arrays eintragen

        listeDerListen.add(eckenArray);
        listeDerListen.add(uvArray);
        listeDerListen.add(normalsArray);

        // ausgeben
        return listeDerListen;
    }

    private static void sortValues(String[] vertexData, List<float[]> vertices, List<float[]> textures, List<float[]> normals){

        float[] currentVert = vertices.get((Integer.parseInt(vertexData[0]))-1);
        eckenArray[dreier] = currentVert[0];
        eckenArray[dreier+1] = currentVert[1];
        eckenArray[dreier+2] = currentVert[2];

        float[] currentTex = textures.get((Integer.parseInt(vertexData[1]))-1);
        uvArray[zweier] = currentTex[0];
        uvArray[zweier+1] = currentTex[1];

        float[] currentNorm = normals.get((Integer.parseInt(vertexData[2]))-1);
        normalsArray[dreier] = currentNorm[0];
        normalsArray[dreier+1] = currentNorm[1];
        normalsArray[dreier+2] = currentNorm[2];

        dreier+=3;
        zweier+=2;

    }

}
