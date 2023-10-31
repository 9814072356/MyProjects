package de.uni_hannover.hci.Ky_Anh;

import de.uni_hannover.hci.Ky_Anh.Perceptron.*;
import de.uni_hannover.hci.Ky_Anh.datareader.*;
/**
 * Das Programm implimentiert ein einfaches Perzeptron, um schlechtes
 * bzw. gutes Wetter zu bestimmen.
 * @author Ky Anh Nguyen
 * @since 22/4/2019
 */
public class Main {
	static double[][] TRAIN_DATA = { { 10.7, 6.1 }, { 4.9, 3.4 }, { 3.8, 5.9 }, { 1.7, 4.7 }, { 8.9, 2.6 },
			{ 8.9, 1.8 }, { 8.8, 1.9 }, { 13.4, 1.7 }, { 19.8, 0.0 }, { 18.9, 0.1 }, { 15.8, 0.0 }, { 12.1, 1.4 },
			{ 8.6, 3.8 }, { 5.8, 1.6 }, { 2.3, 2.9 }, { 3.3, 5.3 }, { 14.9, 0.5 }, { 18.8, 0.3 }, { 22.8, 0.0 },
			{ 27.3, 0.3 }, { 30.7, 0.0 }, { 30.6, 0.0 }, { 27.6, 0.0 }, { 23.2, 0.2 }, { 0.8, 5.4 }, { 16.4, 0.7 },
			{ 25.8, 1.2 }, { 23.6, 1.0 }, { 21.0, 0.3 }, { 29.0, 1.4 } };
	/**
	 * Erwartete Werte - 1 = schlecht, 0 = gut
	 */
	static int[] TRAIN_OUTPUT = { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
	static double[][] TEST_DATA = { { 30.0, 0.9 }, { 11.2, 2.1 }, { 23.2, 0.1 }, { 16.0, 0.0 }, { 0.5, 1.7 },
			{ 14.9, 1.7 } };
	static int weightLength = 2;
	static int epoch = 10000;
	static double learnRate = 0.0005;
	/**
	 * Zuweisst und trainiert ein Perzeptron von vorgegebenen Daten und Ergebnisse
	 * @param args Unused
	 */
	public static void main(String[] args) {
		Perceptron perceptron = new Perceptron(weightLength, epoch, learnRate);
		perceptron.train(DataReader.read(TRAIN_DATA, TRAIN_OUTPUT));
		double[] weights = perceptron.getWeights();
		int iter = perceptron.getEpoch();
		double accuRate = perceptron.getAccu();
		perceptron.printStats(weights,iter,accuRate,TEST_DATA); //Ergebnisse ausdruecken
	}
}
