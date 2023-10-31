package de.uni_hannover.hci.Ky_Anh.Perceptron;
import java.util.Random;
import de.uni_hannover.hci.Ky_Anh.datareader.*;

public class Perceptron {
	private double[] weights;
	private double learningRate;
	private int epoch;
	private int epochCount = 0;
	private double accuracyRate = 0;
	/**
	 * Konstruktorfunktion - realisiert Objekt des Typs Perceptron
	 * @param weightSize Laenge von Weights
	 * @param epoch Anzahl der Iterationschritte
	 * @param learningRate Einfach Lernrate
	 */
	public Perceptron(int weightSize, int epoch, double learningRate){
		this.epoch = epoch;
		this.learningRate = learningRate;
		weights = new double[weightSize];
	}
	/**
	 * This is where the magic happens. Dies berechnet jede Epoch den sogenannten 'error', dann
	 * gibt die richtige Weights zurrückt durch den Formular weights[k] += learningrate * error * traindata[j][k].
	 * @param trainData Eingaben, mit der Perzeptron trainiert wird.
	 */
	public void train(Data[] trainData) {
		Random rand = new Random();
		int error;
		int accuracyCount;
		for(int i = 0; i < weights.length; i++) {
			weights[i] = rand.nextDouble();
		}

		for (int k = 0; k < epoch; k++)
		{
			accuracyCount = 0;

			for (int j = 0; j < trainData.length; j++) {
				double[] trainDataArray = trainData[j].getTrainData();
				int computedValue = output(trainDataArray);
				error = trainData[j].getTrainOutput() - computedValue;

				if (error == 0){
					accuracyCount++;

				}

				for (int i = 0; i < weights.length; i++) {
					trainDataArray = trainData[j].getTrainData();
					weights[i] += learningRate * error * trainDataArray[i];
				}
			}
			this.epochCount++;
			accuracyRate = (double)accuracyCount / trainData.length;
			if(accuracyRate >= 0.95)return;
		}
	}
	public void printStats(double[] weights, int epoch, double accuracyRate,double[][] testData){
		System.out.println("------------------------------TRAINING BEGINS------------------------------\n");
		for(int i = 0; i < weights.length; i++){
			if(i == 0){
				System.out.print("Weights: [ " + String.format("%.2f",weights[i]));
			}else{
				System.out.print(" " + String.format("%.2f",weights[i]) + " ]");
			}
		}
		System.out.print("\nEpochs needed: " + String.format("%d",epoch) + ", accuracy: " + String.format("%.2f", accuracyRate));
		System.out.println("\n\n------------------------------TEST RESULTS------------------------------");
		for (int i = 0; i < testData.length; i++){
			int output = output(testData[i]);
			String result = ((output == 0) ? "gutes Wetter" : "schlechtes Wetter");
			System.out.println("Temperatur: " + testData[i][0] + ", Niederschlag: " + testData[i][1] + " -> " + result);
		}
	}
	/**
	 * Gibt die Werte von aktuelen Weights zurückt
	 * @return weights
	 */
	public double[] getWeights(){
		return weights;
	}
	/**
	 * 
	 * @return Epoch
	 */
	public int getEpoch(){
		return epochCount;
	}
	/**
	 * 
	 * @return accuracy rate
	 */
	public double getAccu(){
		return accuracyRate;
	}
	/**
	 * Gibt das Ergebnis aus
	 * @param input Zu ueberpruefenden Eingaben
	 * @return 1 wenn input * weight groesser 0, sonst 0
	 */
	public int output(double[] input) {
		double sum = 0;

		for(int i = 0; i < input.length; i++) {
			sum += input[i] * weights[i]; 
		}

		return sum > 0 ? 1 : 0;
	}
}
