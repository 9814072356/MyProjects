package de.uni_hannover.hci.Ky_Anh.datareader;

public class DataReader {
	/**
	 * Erstellt ein array von Data von vorgegebenen Daten
	 * @param trainData TrainingArray
	 * @param trainOutputs Erwartetet Ausgaben von trainData
	 * @return Ein array besteht aus arrays von Data
	 */
	public static Data[] read(double[][] trainData, int[] trainOutputs) {
		Data[] d = new Data[trainData.length];

		for(int i = 0; i < d.length; i++) {
			d[i] = new Data(trainData[i], trainOutputs[i]);
		}

		return d;
	}
}
