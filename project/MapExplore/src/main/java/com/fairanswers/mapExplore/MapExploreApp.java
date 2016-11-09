package com.fairanswers.mapExplore;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MapExploreApp {

	private static final String SEED_OPT = "seed";
	private static final String AGENTS_OPT = "agents";
	private static final String X_OPT = "x";
	private static final String Y_OPT = "y";
	private static CommandLine line;
	private static String[] req = {SEED_OPT, AGENTS_OPT, X_OPT, Y_OPT};

	public static void main(String[] args) {
		Options options = new Options();

		options.addOption( new Option(SEED_OPT, true, "seed for random") );
		options.addOption( new Option(AGENTS_OPT, true, "number of agents") );
		options.addOption( new Option(X_OPT, true, "size of map x") );
		options.addOption( new Option(Y_OPT, true, "size of map y") );

		CommandLineParser parser = new DefaultParser();
		try {
			line = parser.parse( options, args);
			for(String s: req){
				if(!line.hasOption(s)){
					System.out.println(s+" is required");
				}
			}
		} catch (ParseException e) {
			System.out.println(e);
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "MapExploreApp", options );
			return;
		}
		System.out.println(line.getOptionValue(SEED_OPT));
		System.out.println(line.getOptionValue(AGENTS_OPT));
		System.out.println(line.getOptionValue(X_OPT));
		System.out.println(line.getOptionValue(Y_OPT));
		System.out.println("END");
	}

}
