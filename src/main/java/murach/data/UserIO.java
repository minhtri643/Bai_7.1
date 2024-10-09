
package murach.data;


import java.io.*;
import murach.business.User;

public class UserIO {
    public static void add(User user, String path) {
        try (PrintWriter out = new PrintWriter(new FileWriter(path, true))) {
            out.println(user.getEmail() + "," + user.getFirstName() + "," + user.getLastName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String email, String path) {
        User user = null;
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(email)) {
                    user = new User();
                    user.setEmail(fields[0]);
                    user.setFirstName(fields[1]);
                    user.setLastName(fields[2]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}

