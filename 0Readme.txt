Pour l'errreur du JAVAFX il faut faire :

 --module-path="C:\Program Files\Java\javafx-sdk-11\lib" \
    --add-modules=javafx.controls,javafx.fxml
    
    
    
    
//https://stackoverflow.com/questions/16111496/java-how-can-i-write-my-arraylist-to-a-file-and-read-load-that-file-to-the
You should use Java's built in serialization mechanism. To use it, you need to do the following:

    Declare the Club class as implementing Serializable:

    public class Club implements Serializable {
        ...
    }

    This tells the JVM that the class can be serialized to a stream. You don't have to implement any method, since this is a marker interface.

    To write your list to a file do the following:

    FileOutputStream fos = new FileOutputStream("t.tmp");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(clubs);
    oos.close();

    To read the list from a file, do the following:

    FileInputStream fis = new FileInputStream("t.tmp");
    ObjectInputStream ois = new ObjectInputStream(fis);
    List<Club> clubs = (List<Club>) ois.readObject();
    ois.close();

    