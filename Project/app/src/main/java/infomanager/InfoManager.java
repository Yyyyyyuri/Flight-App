package infomanager;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * An InfoManager managing T objects.
 *
 * @param <T> the type of object in this InfoManager.
 */
public abstract class InfoManager<T> implements Serializable {

    /**
     * The path of the serialized data file.
     * This is new.  It gets a value upon construction, and that path value
     * is used whenever we save or load this InfoManager.
     */
    private String filePath;




    private static final Logger logger =
            Logger.getLogger(FlightManager.class.getPackage().getName());

    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

    /**
     * Creates a new empty InfoManager.
     *
     * @param file a File to read from
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public InfoManager(File file) throws ClassNotFoundException, IOException {
        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Populate the object HashMap using serialized data stored in file,
        // if it exists.  If not, create a new empty file for it to be saved in
        // later.
        if (file.exists()) {
            this.readFromFile(file.getPath());
        } else {
            file.createNewFile();
        }

        // Records the path of the serialized data file.
        filePath = file.getPath();
    }

    /**
     * empty constructor
     */
    public InfoManager(){

    }

    /* Abstract methods to be implemented in child class*/
    protected abstract T createObject(String[] str);

    protected abstract void add(T item);

    protected abstract Map<String, T> getMap();

    protected abstract void setMap(Map<String, T> map);

    /**
     * Populates the records map from the serfile at path filePath.
     *
     * @param filePath the path of the data file *.ser
     */
    public void readFromFile(String filePath) {
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // Deserialize the Map.
            this.setMap((Map<String, T>) input.readObject());
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Cannot find class.", ex);
        }
    }

    /**
     * Populates the records map from the file at path filePath.
     *
     * @param fileInputStream the FileInputStream we are reading
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void readFromCSVFile(InputStream fileInputStream) throws
            FileNotFoundException {
        // FileInputStream can be used for reading raw bytes, like an image.
        Scanner scanner = new Scanner(fileInputStream);
        String[] record;
        T item;
        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            // initiate each flight object in csv file in manager
            item = this.createObject(record);
            // map it
            this.add(item);
        }
        scanner.close();
    }

    /**
     * Add all the T objects in csv file at filepath to this InfoManager.
     *
     * @param filepath a filepath to be added.
     * @throws FileNotFoundException
     */
    public void add(String filepath) throws FileNotFoundException {
        readFromCSVFile(new FileInputStream(filepath));
    }

    /**
     * Add all the T objects in csv file at filepath to this InfoManager.
     *
     * @param inputStream an inputStream to be added.
     * @throws FileNotFoundException
     */
    public void add(InputStream inputStream) throws FileNotFoundException {
        readFromCSVFile(inputStream);
    }

    /**
     * Writes the objects to file at filePath.
     */
    public void saveToFile() {
        try {
            FileOutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // Serialize the corresponding Map.
            output.writeObject(this.getMap());
            logger.log(Level.FINE, "Serialized */manager.");
            output.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE,
                    "Cannot perform output. File I/O failed.", ex);
        }
    }

    /**
     * Get the logger
     *
     * @return the Logger logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Returns a string representing all the T objects in InfoManager.
     *
     * @return a string representing all the T objects in InfoManager.
     */
    @Override
    public String toString() {
        String result = "";
        for (T item : getMap().values()) {
            result += item.toString() + "\n";
        }
        return result;
    }


}
