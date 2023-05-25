package com.alejandro.openweatherapp.model;
import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FavouriteList {
    public static final String FILENAME = "favslist.txt";
    private Context mContext;
    private List<String> mFavsList;
    public FavouriteList(Context context) {
        mContext = context;
        mFavsList = new ArrayList<>();
    }
    public void addItem(String item) throws IllegalArgumentException {
        mFavsList.add(item);
    }
    public void removeItem(String item) throws IllegalArgumentException {
        mFavsList.remove(item);
    }
    public void removeItem(int index) throws IllegalArgumentException {
        mFavsList.remove(index);
    }
    public String[] getItems() {
        String[] items = new String[mFavsList.size()];
        return mFavsList.toArray(items);
    }
    public void clear() {
        mFavsList.clear();
    }
    public void saveToFile() throws IOException {
// Write list to file in internal storage
        FileOutputStream outputStream = mContext.openFileOutput(FILENAME,
                Context.MODE_PRIVATE);
        writeListToStream(outputStream);
    }
    public void readFromFile() throws IOException {
// Read in list from file in internal storage
        FileInputStream inputStream = mContext.openFileInput(FILENAME);
        try (BufferedReader reader = new BufferedReader(new
                InputStreamReader(inputStream))) {
            mFavsList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                mFavsList.add(line);
            }
        }
        catch (FileNotFoundException ex) {
// Ignore
        }
    }
    private void writeListToStream(FileOutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (String item : mFavsList) {
            writer.println(item);
        }
        writer.close();
    }
    public List<String> getList(){
        return mFavsList;
    }
    public void editItem(int position, String string){
        mFavsList.set(position, string);
    }

}