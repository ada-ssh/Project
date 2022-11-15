import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        ObjectMapper mapper = new ObjectMapper();

        List<Users> users = mapper.readValue(new File("C:\\Users\\LENOVO\\Downloads\\users.json"), new TypeReference<>() {
        });

        List<Posts> posts = mapper.readValue(new File("C:\\Users\\LENOVO\\Downloads\\posts.json"), new TypeReference<>() {
        });

        List<Photos> photos = mapper.readValue(new File("C:\\Users\\LENOVO\\Downloads\\photos.json"), new TypeReference<>() {
        });

        List<Comments> comments = mapper.readValue(new File("C:\\Users\\LENOVO\\Downloads\\comments.json"), new TypeReference<>() {
        });

        List<Albums> albums = mapper.readValue(new File("C:\\Users\\LENOVO\\Downloads\\albums.json"), new TypeReference<>() {
        });

        try (Connection connect = connect()) {
            Statement statement = connect.createStatement();
            statement.execute("""
                        Create table if not exists users(
                    	id integer not null ,
                    	username varchar not null,
                    	name varchar not null,
                      	email varchar not null,
                        street varchar not null,
                    	city varchar not null,
                    	suit varchar not null,
                    	zipcode varchar not null,
                    	lng varchar not null,
                    	lat varchar not null,
                        phone varchar not null,
                    	website varchar not null,
                    	companyName varchar not null,
                    	catchPhrase varchar not null,
                    	bs varchar not null,
                    	CONSTRAINT USERS_PK PRIMARY KEY (id)
                                        
                        )
                    """);


            statement.execute("""
                    Create table if not exists posts(
                    id INTEGER NOT NULL,
                    userId INTEGER NOT NULL,
                    title VARCHAR NOT NULL,
                    body VARCHAR NOT NULL,
                    CONSTRAINT POSTS_PK PRIMARY KEY (id),
                    CONSTRAINT posts_FK FOREIGN KEY (userId) REFERENCES users(id)
                                   )
                               """);


            statement.execute("""
                    Create table if not exists albums(
                    id INTEGER NOT NULL,
                    userId INTEGER NOT NULL,
                    title VARCHAR NOT NULL,
                    CONSTRAINT ALBUMS_PK PRIMARY KEY (id),
                    CONSTRAINT albums_FK FOREIGN KEY (userId) REFERENCES users(id)
                                   )
                               """);

            statement.execute("""
                    Create table if not exists comments(
                    id INTEGER NOT NULL,
                    postId INTEGER NOT NULL,
                    name VARCHAR NOT NULL,
                    email VARCHAR NOT NULL,
                    body VARCHAR NOT NULL,
                    CONSTRAINT COMMENTS_PK PRIMARY KEY (id),
                    CONSTRAINT albums_FK FOREIGN KEY (postId) REFERENCES posts(id)
                                   )
                               """);


            statement.execute("""
                    Create table if not exists photos(
                    id INTEGER NOT NULL,
                    albumId INTEGER NOT NULL,
                    title VARCHAR NOT NULL,
                    url VARCHAR NOT NULL,
                    thumbnailURL VARCHAR NOT NULL,
                    CONSTRAINT PHOTOS_PK PRIMARY KEY (id),
                    CONSTRAINT photos_FK FOREIGN KEY (albumId) REFERENCES albums(id)
                                   )
                               """);

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO users(id, username, name, email, street, city, suit, zipcode,  lng, lat, phone, website, companyName, catchPhrase, bs) VALUES");
            for (Users user : users) {
                sb.append("(")
                        .append(user.getId()).append(", ")
                        .append("'").append(user.getUsername()).append("'").append(", ")
                        .append("'").append(user.getName()).append("'").append(", ")
                        .append("'").append(user.getEmail()).append("'").append(", ")
                        .append("'").append(user.getAddress().getStreet()).append("'").append(", ")
                        .append("'").append(user.getAddress().getCity()).append("'").append(", ")
                        .append("'").append(user.getAddress().getSuite()).append("'").append(", ")
                        .append("'").append(user.getAddress().getZipcode()).append("'").append(", ")
                        .append("'").append(user.getAddress().getGeo().getLng()).append("'").append(", ")
                        .append("'").append(user.getAddress().getGeo().getLat()).append("'").append(", ")
                        .append("'").append(user.getPhone()).append("'").append(", ")
                        .append("'").append(user.getWebsite()).append("'").append(", ")
                        .append("'").append(user.getCompany().getCompanyName()).append("'").append(", ")
                        .append("'").append(user.getCompany().getCatchPhrase()).append("'").append(", ")
                        .append("'").append(user.getCompany().getBs()).append("'")
                        .append(")").append(",");
            }

            sb.deleteCharAt(sb.length() - 1);
            statement.execute(sb.toString());


            sb.setLength(0);
            sb.append("INSERT INTO albums(id, userId, title) VALUES");
            for (Albums album : albums) {
                sb.append("(")
                        .append(album.getId()).append(", ")
                        .append(album.getUserId()).append(", ")
                        .append("'").append(album.getTitle()).append("'")
                        .append(")").append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            statement.execute(sb.toString());


            sb.setLength(0);
            sb.append("INSERT INTO comments(id, postId, name, email, body) VALUES");
            for (Comments comment : comments) {
                sb.append("(")
                        .append(comment.getId()).append(", ")
                        .append(comment.getPostId()).append(", ")
                        .append("'").append(comment.getName()).append("'").append(", ")
                        .append("'").append(comment.getEmail()).append("'").append(", ")
                        .append("'").append(comment.getBody()).append("'")
                        .append(")").append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            statement.execute(sb.toString());


            sb.setLength(0);
            sb.append("INSERT INTO posts(id, userId, title, body) VALUES");
            for (Posts post : posts) {
                sb.append("(")
                        .append(post.getId()).append(", ")
                        .append(post.getUserId()).append(", ")
                        .append("'").append(post.getTitle()).append("'").append(", ")
                        .append("'").append(post.getBody()).append("'")
                        .append(")").append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            statement.execute(sb.toString());


            sb.setLength(0);
            sb.append("INSERT INTO photos(id, albumId, title, url, thumbnailUrl) VALUES");
            for (Photos photo : photos) {
                sb.append("(")
                        .append(photo.getId()).append(", ")
                        .append(photo.getAlbumId()).append(", ")
                        .append("'").append(photo.getTitle()).append("'").append(", ")
                        .append("'").append(photo.getUrl()).append("'").append(", ")
                        .append("'").append(photo.getThumbnailUrl()).append("'")
                        .append(")").append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            statement.execute(sb.toString());
            statement.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
        Введите число: 
        1.Добавить пользователя
        2.Добавить пост
        3.Добавить комментарий
        4.Добавить фото
        5.Добавить альбом
        6.Update пользователя
        7.Update поста
        8.Update фото
        9.Update комментария
        10.Update альбома       
        11.Удалить пользователя по id      
        12.Удалить пост по id
        13.Удалить фото по id      
        14.Удалить комментарий по id      
        15.Удалить альбом по id 
        16.Найти пользователя по id
        17.Найти пост по имени пользователя
        18.Найти адрес пользователя
        """);
        int x = scanner.nextInt();
        switch (x){
            case 1 : {
                addUsers();
                break;
            }
            case 2 : {
                addPosts();
                break;
            }
            case 3 : {
                addComments();
                break;
            }
            case 4 : {
                addPhotos();
                break;
            }
            case 5 : {
                addAlbums();
                break;
            }
            case 6 : {
                updateUsers();
                break;
            }
            case 7 : {
                updatePosts();
                break;
            }
            case 8 : {
                updatePhotos();
                break;
            }
            case 9 : {
                updateComments();
                break;
            }
            case 10 : {
                updateAlbums();
                break;
            }
            case 11 : {
                deleteUserByID();
                break;
            }
            case 12 : {
                deletePostsByID();
                break;
            }
            case 13 : {
                deletePhotosByID();
                break;
            }
            case 14 : {
                deleteCommentsByID();
                break;
            }
            case 15 : {
                deleteAlbumsByID();
                break;
            }
            case 16 : {
                userForId();
                break;
            }
            case 17 : {
                postForUser();
                break;
            }
            case 18 : {
                addressForUser();
                break;
            }
        }
    }

    public static void addUsers() {
        try (Connection connection = connect()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите id");
            int id = scanner.nextInt();
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Введите name");
            String name = scanner1.nextLine();
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Введите username");
            String username = scanner2.nextLine();
            Scanner scanner3 = new Scanner(System.in);
            System.out.println("Введите email");
            String email = scanner3.nextLine();
            Scanner scanner4 = new Scanner(System.in);
            System.out.println("Введите street");
            String street = scanner4.nextLine();
            Scanner scanner5 = new Scanner(System.in);
            System.out.println("Введите suite");
            String suite = scanner5.nextLine();
            Scanner scanner6 = new Scanner(System.in);
            System.out.println("Введите city");
            String city = scanner6.nextLine();
            Scanner scanner7 = new Scanner(System.in);
            System.out.println("Введите zipcode");
            String zipcode = scanner7.nextLine();
            Scanner scanner8 = new Scanner(System.in);
            System.out.println("Введите lng");
            double lng = scanner8.nextDouble();
            Scanner scanner9 = new Scanner(System.in);
            System.out.println("Введите lat");
            double lat = scanner9.nextDouble();
            Scanner scanner10 = new Scanner(System.in);
            System.out.println("Введите phone");
            String phone = scanner10.nextLine();
            Scanner scanner11 = new Scanner(System.in);
            System.out.println("Введите website");
            String website = scanner11.nextLine();
            Scanner scanner12 = new Scanner(System.in);
            System.out.println("Введите companyName");
            String companyName = scanner12.nextLine();
            Scanner scanner13 = new Scanner(System.in);
            System.out.println("Введите catchPhrase");
            String catchPhrase = scanner13.nextLine();
            Scanner scanner14 = new Scanner(System.in);
            System.out.println("Введите bs");
            String bs = scanner14.nextLine();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Posts(id, username, companyName, email,  street, city, suite, zipcode,  lng, lat, phone, website, companyName, catchPhrase, bs)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, street);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, suite);
            preparedStatement.setString(8, zipcode);
            preparedStatement.setDouble(9, lng);
            preparedStatement.setDouble(10, lat);
            preparedStatement.setString(11, phone);
            preparedStatement.setString(12, website);
            preparedStatement.setString(13, companyName);
            preparedStatement.setString(14, catchPhrase);
            preparedStatement.setString(15, bs);

            if (preparedStatement.execute()) {
                connection.commit();
            } else {
                connection.rollback();
            }

            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void addPhotos() {
        try (Connection connection = connect()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите userId");
            int albumId = scanner.nextInt();
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Введите id");
            int id = scanner1.nextInt();
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Введите title");
            String title = scanner2.nextLine();
            Scanner scanner3 = new Scanner(System.in);
            System.out.println("Введите url");
            String url = scanner3.nextLine();
            Scanner scanner4 = new Scanner(System.in);
            System.out.println("Введите thumbnailUrl");
            String thumbnailUrl = scanner4.nextLine();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Photos(albumId, id, title, url, thumbnailUrl)VALUES (?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, albumId);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, url);
            preparedStatement.setString(5, thumbnailUrl);
            if (preparedStatement.execute()) {
                connection.commit();
            } else {
                connection.rollback();
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void addComments() {
        try (Connection connection = connect()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите postId");
            int postId = scanner.nextInt();
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Введите id");
            int id = scanner1.nextInt();
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Введите name");
            String name = scanner2.nextLine();
            Scanner scanner3 = new Scanner(System.in);
            System.out.println("Введите email");
            String email = scanner3.nextLine();
            Scanner scanner4 = new Scanner(System.in);
            System.out.println("Введите body");
            String body = scanner4.nextLine();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Comments(postId, id, name, email, body)VALUES (?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, postId);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, body);
            if (preparedStatement.execute()) {
                connection.commit();
            } else {
                connection.rollback();
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void addPosts() {
        try (Connection connection = connect()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите userId");
            int userId = scanner.nextInt();
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Введите id");
            int id = scanner1.nextInt();
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Введите title");
            String title = scanner2.nextLine();
            Scanner scanner3 = new Scanner(System.in);
            System.out.println("Введите body");
            String body = scanner3.nextLine();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Posts(userId, id, title, body)VALUES (?, ?, ?, ?);");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, title);
            preparedStatement.setString(4, body);
            if (preparedStatement.execute()) {
                connection.commit();
            } else {
                connection.rollback();
            }

            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void addAlbums() {
        try (Connection connection = connect()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите userId");
            int userId = scanner.nextInt();
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Введите id");
            int id = scanner1.nextInt();
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("Введите title");
            String title = scanner2.nextLine();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Albums(userId, id, title)VALUES (?, ?, ?);");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, title);
            if (preparedStatement.execute()) {
                connection.commit();
            } else {
                connection.rollback();
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void updateUsers() throws SQLException {
        Connection connect = connect();
        try{
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE users SET name = 'Aida Shabanovich' WHERE id = 3");
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(connect != null){
                    connect.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void updatePosts() throws SQLException {
        Connection connect = connect();
        try{
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE posts SET title = 'aperiam quia dolorem eum magni eos' WHERE id = 6");
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(connect != null){
                    connect.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void updatePhotos() throws SQLException {
        Connection connect = connect();
        try{
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE photos SET title = 'world' WHERE id = 7");
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(connect != null){
                    connect.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void updateComments() throws SQLException {
        Connection connect = connect();
        try{
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE comments SET body = 'veritatis voluptates necessitatibus' WHERE id = 1");
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(connect != null){
                    connect.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void updateAlbums() throws SQLException {
        Connection connect = connect();
        try{
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE albums SET title = 'id non' WHERE id = 27");
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(connect != null){
                    connect.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void deleteUserByID() throws IOException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from users where id = ?")) {
            Scanner scanner = new Scanner(System.in);
            int id0 = scanner.nextInt();
            preparedStatement.setInt(1, id0);

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void deletePostsByID() throws IOException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from posts where id = ?")) {
            Scanner scanner = new Scanner(System.in);
            int id0 = scanner.nextInt();
            preparedStatement.setInt(1, id0);

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void deletePhotosByID() throws IOException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from photos where id = ?")) {
            Scanner scanner = new Scanner(System.in);
            int id0 = scanner.nextInt();
            preparedStatement.setInt(1, id0);

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void deleteCommentsByID() throws IOException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from comments where id = ?")) {
            Scanner scanner = new Scanner(System.in);
            int id0 = scanner.nextInt();
            preparedStatement.setInt(1, id0);

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void deleteAlbumsByID() throws IOException {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from albums where id = ?")) {
            Scanner scanner = new Scanner(System.in);
            int id0 = scanner.nextInt();
            preparedStatement.setInt(1, id0);

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void userForId() {
        try (Connection connection = connect()) {
            Scanner scanner = new Scanner(System.in);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users t WHERE id = ?");

            int userForId = scanner.nextInt();
            preparedStatement.setInt(1, userForId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String username = resultSet.getString(3);
                String email = resultSet.getString(4);
                String street = resultSet.getString(5);
                String city = resultSet.getString(6);
                String suite = resultSet.getString(7);
                String zipcode = resultSet.getString(8);
                String lng = resultSet.getString(9);
                String lat = resultSet.getString(10);
                String phone = resultSet.getString(11);
                String website = resultSet.getString(12);
                String companyName = resultSet.getString(13);
                String catchPhrase = resultSet.getString(14);
                String bs = resultSet.getString(15);
                System.out.println("id: " + id);
                System.out.println("username: " + username);
                System.out.println("name: " + name);
                System.out.println("email: " + email);
                System.out.println("street: " + street);
                System.out.println("city: " + city);
                System.out.println("suite: " + suite);
                System.out.println("zipcode: " + zipcode);
                System.out.println("lng: " + lng);
                System.out.println("lat: " + lat);
                System.out.println("phone: " + phone);
                System.out.println("website: " + website);
                System.out.println("companyName: " + companyName);
                System.out.println("catchPhrase: " + catchPhrase);
                System.out.println("bs: " + bs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();

                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void postForUser() {
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from posts p " +
                    "JOIN users u on p.userId = u.id " +
                    "where u.username = ?");
            Scanner scanner = new Scanner(System.in);
            String postForUser = scanner.nextLine();
            preparedStatement.setString(1, postForUser);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                String title = resultSet.getString(3);
                String body = resultSet.getString(4);
                System.out.println("id: " + id);
                System.out.println("userId: " + userId);
                System.out.println("title: " + title);
                System.out.println("body: " + body);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void addressForUser() {
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT city, street, suit, zipcode, lng, lat, phone FROM users t WHERE Name = ?");
            Scanner scanner = new Scanner(System.in);
            String addressForUser = scanner.nextLine();
            preparedStatement.setString(1, addressForUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String street = resultSet.getString(1);
                String city = resultSet.getString(2);
                String suite = resultSet.getString(3);
                String zipcode = resultSet.getString(4);
                String lng = resultSet.getString(5);
                String lat = resultSet.getString(6);
                String phone = resultSet.getString(7);
                System.out.println("street: " + street);
                System.out.println("city: " + city);
                System.out.println("suite: " + suite);
                System.out.println("zipcode: " + zipcode);
                System.out.println("lng: " + lng);
                System.out.println("lat: " + lat);
                System.out.println("phone: " + phone);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect() != null) {
                    connect().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }



    public static Connection connect() throws SQLException {
        Connection connection;
        String url = "jdbc:sqlite:E:\\IntelliJ IDEA 2022.2.1\\NewProject\\MyBase";
        connection = DriverManager.getConnection(url);
        return connection;
    }
}
