package optmyzr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author pancham
 *
 */
public class AbTesting {
	/**
	 * 
	 * Assume first Ad in the same Adgroup as Control Variation Define random
	 * variable X = p-pc where p is the conversion rate of AdVariation and pc is
	 * conversion rate of Control AdVariation Assume NULL hypothesisas H0 : X <=
	 * 0 and alternate hypothesisas would be X >= 0 Calculate z-score of each
	 * AdVariation (Assumin Normal Distribution) using following formula
	 * 
	 * z = X/sqrt(p(1-p)/n + pc(1-pc)/nc)
	 * 
	 * z-score for 95% confidence level should be 1.96 A particular AdVariation
	 * of z-score having > 1.96 is declared as "Winner" over Control Variation,
	 * else if z-score < 1.96 then it is declared as "Loser" and else if z-score
	 * = 1.96 it is declared as "NO_RESULT" If an Adgroup have only on Ad
	 * Variation then it is declared as "NO_RESULT" as it is not Statistically
	 * significant
	 * 
	 */
	private static final String NO_RESULT = "NO_RESULT";
	private static final String WINNER = "WINNNER";
	private static final String LOSER = "LOSER";
	private static final double z_Score_Confidence = 1.96;

	public static void main(String[] args) throws Exception {
		Map<String, List<AdVariation>> adGroupMap = new HashMap<String, List<AdVariation>>();
		BufferedReader TSVFile = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File("Ad_Performance_Report_(2).tsv")), "UTF-8"));

		String dataRow = TSVFile.readLine(); // Read first line.
		String headerRow = dataRow; // This line contains header

		dataRow = TSVFile.readLine(); // Read next line of data.
		while (dataRow != null) {
			List<AdVariation> adVariations = new ArrayList<AdVariation>();
			String[] dataArray = dataRow.split("\\t");

			if (adGroupMap.containsKey(dataArray[0])) {
				adVariations = adGroupMap.get(dataArray[0]);
			}
			AdVariation adVariationObj = new AdVariation(dataArray[1], Double.parseDouble(dataArray[2]),
					Double.parseDouble(dataArray[3]));
			adVariations.add(adVariationObj);
			adGroupMap.put(dataArray[0], adVariations);
			dataRow = TSVFile.readLine(); // Read next line of data.
		}
		TSVFile.close();
		// Close the file once all data has been read.

		for (String adGroupId : adGroupMap.keySet()) {
			doAbTesting(adGroupMap.get(adGroupId));
		}

		OutputStream writer = new FileOutputStream(new File("output.tsv"));
		headerRow += "\tResult";
		writer.write(headerRow.getBytes());
		for (String adGroupId : adGroupMap.keySet()) {
			StringBuffer lineBuffer = null;
			for (AdVariation adVariationObj : adGroupMap.get(adGroupId)) {
				lineBuffer = new StringBuffer("\n" + adGroupId);
				lineBuffer.append("\t" + adVariationObj.getAdVariationId());
				lineBuffer.append("\t" + (int) (adVariationObj.getNo_of_clicks()));
				lineBuffer.append("\t" + (int) (adVariationObj.getNo_of_impression()));
				lineBuffer.append("\t" + adVariationObj.getResult());
				writer.write(lineBuffer.toString().getBytes());
			}
		}
		writer.close();
	}

	public static void doAbTesting(List<AdVariation> adVariationObjList) {
		double pc, nc, p, n;
		if (adVariationObjList != null && adVariationObjList.isEmpty() == false) {
			if (adVariationObjList.size() == 1) {
				adVariationObjList.get(0).result = NO_RESULT;
				return;
			}
			/*
			 * double totalNoOfClicks = 0.0; double totalNoOfImpressions = 0.0;
			 * for (AdVariation adVariation : adVariationObjList) {
			 * totalNoOfClicks += adVariation.getNo_of_clicks();
			 * totalNoOfImpressions += adVariation.getNo_of_impression(); }
			 */
			AdVariation controlVariation = adVariationObjList.get(0);
			pc = controlVariation.getNo_of_clicks() / controlVariation.getNo_of_impression();
			nc = controlVariation.getNo_of_impression();
			pc = controlVariation.getNo_of_clicks() / controlVariation.getNo_of_impression();
			nc = controlVariation.getNo_of_impression();
			/*
			 * pc = totalNoOfClicks / totalNoOfImpressions; nc =
			 * totalNoOfImpressions;
			 */
			AdVariation adVariationObj = null;
			for (int i = 1; i < adVariationObjList.size(); i++) {
				adVariationObj = adVariationObjList.get(i);
				p = adVariationObj.getNo_of_clicks() / adVariationObj.getNo_of_impression();
				n = adVariationObj.getNo_of_impression();
				double z_Score_Variation = (p - pc) / (Math.sqrt((p * (1 - p)) / n + (pc * (1 - pc)) / nc));
				if (z_Score_Variation > z_Score_Confidence) {
					adVariationObj.setResult(WINNER);
				} else if (z_Score_Variation < z_Score_Confidence) {
					adVariationObj.setResult(LOSER);
				} else {
					adVariationObj.setResult(NO_RESULT);
				}
			}
			/*
			 * if (adVariationObjList.size() == 2) {
			 * controlVariation.setResult(adVariationObjList.get(1).getResult()
			 * == WINNER ? LOSER : WINNER); } int winnerCount = 0, loserCount =
			 * 0; for (int i = 1; i < adVariationObjList.size(); i++) {
			 * AdVariation adVariation = adVariationObjList.get(i); if
			 * (adVariation.getResult() == WINNER) winnerCount++; if
			 * (adVariation.getResult() == LOSER) loserCount++; } if (loserCount
			 * == adVariationObjList.size() - 1)
			 * controlVariation.setResult(WINNER); if (winnerCount ==
			 * adVariationObjList.size() - 1) {
			 * controlVariation.setResult(LOSER); }
			 */
		}
	}
}

class AdVariation {
	String adVariationId;
	double no_of_clicks;
	double no_of_impression;
	String result;

	public AdVariation() {
	}

	public AdVariation(String adVariationId, double no_of_clicks, double no_of_impressions) {
		this();
		this.adVariationId = adVariationId;
		this.no_of_clicks = no_of_clicks;
		this.no_of_impression = no_of_impressions;
		this.result = "NO_RESULT";
	}

	public String getAdVariationId() {
		return adVariationId;
	}

	public void setAdVariationId(String adVariationId) {
		this.adVariationId = adVariationId;
	}

	public double getNo_of_clicks() {
		return no_of_clicks;
	}

	public void setNo_of_clicks(double no_of_clicks) {
		this.no_of_clicks = no_of_clicks;
	}

	public double getNo_of_impression() {
		return no_of_impression;
	}

	public void setNo_of_impression(double no_of_impression) {
		this.no_of_impression = no_of_impression;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
