package de.uni_hannover.hci.Ky_Anh.datareader;

public class Data {
	private double[] trainData ;
	private int trainOutput ;
/**
 * realisiert ein Data Objekt.
 * @param trainData Train array, mit der Perzeptron die richtige weights finden kann
 * @param trainOutput Zu erwartete Ausgaben
 */
	public Data(double[] trainData, int trainOutput){
		this.trainData = trainData;
		this.trainOutput = trainOutput;
	}
/**
 * Getter Funktion
 * @return trainData
 */
	public double[] getTrainData(){
		return trainData;
	}
/**
 * Setter Funktion
 * @return trainOUtput
 */
	public int getTrainOutput() {
		return trainOutput;
	}
}
