package utils;

//import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.tensorflow.SavedModelBundle;

public class MachineLearningModelLoader {

	 private SavedModelBundle model;

	    public MachineLearningModelLoader() {
	    	 try (InputStream is = getClass().getClassLoader().getResourceAsStream("best_dppe_model.pkl");
	                 ObjectInputStream ois = new ObjectInputStream(is)) {
	                this.model = (SavedModelBundle) ois.readObject();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	    }

	    public SavedModelBundle getModel() {
	        return model;
	    }
}
