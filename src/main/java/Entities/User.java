/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Servlets.UploadSevlet;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.media.jfxmedia.logging.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paotoyav
 */
public class User {

    private static List<User> users;

    public static List<User> getUserList() {
        if (users == null) {
            users = new ArrayList<User>();
        }
        return users;
    }

    private int id;
    private String email;
    private String password;

    // El path de cada video del usuario.
    private List<String> pathVideos;
    private List<String> pathThumbnails;

    public User(int id, String name, String password) {
        this.id = id;
        this.email = name;
        this.password = password;
        pathVideos = new ArrayList<String>();
        pathThumbnails = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return email;
    }

    public void setName(String name) {
        this.email = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPathVideos() {

        this.pathVideos.removeAll(pathVideos);

        File[] files = new File(UploadSevlet.UPLOAD_DIRECTORY).listFiles();
        for (File file : files) {
            if (file.getName().split("-EOL-")[0].equals(this.email)) {
                if (file.getName().endsWith(".mp4")) {
                    addVideo("videos" + File.separator + file.getName());
                }
            }
        }

        return pathVideos;
    }

    public List<String> getPathThumbnails() {
        this.pathThumbnails.removeAll(pathThumbnails);

        File[] files = new File(UploadSevlet.UPLOAD_DIRECTORY).listFiles();
        for (File file : files) {
            if (file.getName().split("-EOL-")[0].equals(this.email)) {
                if (file.getName().endsWith(".png")) {
                    this.pathThumbnails.add( "videos" + File.separator + file.getName() );
                }
            }
        }

        return this.pathThumbnails ;
    }

    public void setPathVideos(List<String> pathVideos) {
        this.pathVideos = pathVideos;
    }

    public static User find(String email) {
        for (User user : users) {
            if (user.email.equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void addVideo(String path) {
        if (!this.pathVideos.contains(path)) {
            this.pathVideos.add(path);
        }
    }
}
