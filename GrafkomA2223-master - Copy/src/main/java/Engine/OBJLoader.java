package Engine;

import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import javax.management.modelmbean.ModelMBean;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class OBJLoader {
//    public static Model loadModel(File f) throws FileNotFoundException, IOException{
//        BufferedReader reader = new BufferedReader(new FileReader(f));
//        Model m = new Model();
//        String line;
//        while((line = reader.readLine()) != null){
//            if(line.startsWith("v ")) {
//                float x = Float.valueOf(line.split(" ")[1]);
//                float y = Float.valueOf(line.split(" ")[2]);
//                float z = Float.valueOf(line.split(" ")[3]);
//                m.vertices.add(new Vector3f(x, y, z));
//            } else if(line.startsWith("vn ")){
//                float x = Float.valueOf(line.split(" ")[1]);
//                float y = Float.valueOf(line.split(" ")[2]);
//                float z = Float.valueOf(line.split(" ")[3]);
//                m.normals.add(new Vector3f(x, y, z));
//            }else if(line.startsWith("f ")) {
//                Vector3f vertexIndices = new Vector3f(
//                        Integer.parseInt(line.split(" ")[1].split("/")[0]),
//                        Integer.parseInt(line.split(" ")[2].split("/")[0]),
//                        Integer.parseInt(line.split(" ")[3].split("/")[0]));
//                Vector3f normalIndices = new Vector3f(
//                        Integer.parseInt(line.split(" ")[1].split("/")[2]),
//                        Integer.parseInt(line.split(" ")[2].split("/")[2]),
//                        Integer.parseInt(line.split(" ")[3].split("/")[2]));
//                m.faces.add(new Face(vertexIndices, normalIndices));
//            }
//        }
//        System.out.println("normals");
//        System.out.println(m.normals);
//        System.out.println("vertex");
//        System.out.println(m.vertices);
//        System.out.println("Faces");
//        for(Face face : m.faces){
//            System.out.print(face.vertex);
//        }
//        reader.close();
//        return m;
//    }

    private static Vector3f parseVertex(String line) {
        String[] xyz = line.split(" ");
        float x = Float.valueOf(xyz[1]);
        float y = Float.valueOf(xyz[2]);
        float z = Float.valueOf(xyz[3]);
        return new Vector3f(x, y, z);
    }

    private static Vector3f parseNormal(String line) {
        String[] xyz = line.split(" ");
        float x = Float.valueOf(xyz[1]);
        float y = Float.valueOf(xyz[2]);
        float z = Float.valueOf(xyz[3]);
        return new Vector3f(x, y, z);
    }

    private static Model.Face parseFace(boolean hasNormals, String line) {
        String[] faceIndices = line.split(" ");
        int[] vertexIndicesArray = {Integer.parseInt(faceIndices[1].split("/")[0]),
                Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
        if (hasNormals) {
            int[] normalIndicesArray = new int[3];
            normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
            normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
            normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
            return new Model.Face(vertexIndicesArray, normalIndicesArray);
        } else {
            return new Model.Face((vertexIndicesArray));
        }
    }

    public static Model loadModel(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        Model m = new Model();
        String line;
        while ((line = reader.readLine()) != null) {
            String prefix = line.split(" ")[0];
            if (prefix.equals("#")) {
                continue;
            } else if (prefix.equals("v")) {
                m.getVertices().add(parseVertex(line));
            } else if (prefix.equals("vn")) {
                m.getNormals().add(parseNormal(line));
            } else if (prefix.equals("f")) {
                m.getFaces().add(parseFace(m.hasNormals(), line));
            } else {
                continue;
            }
        }
        reader.close();
        return m;
    }
}
