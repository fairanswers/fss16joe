package com.fairanswers.mapExplore;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.fairanswers.mapExplore.fsm.Model;

public class MapExploreApp {

	private static final String SEED_OPT = "seed";
	private static final String AGENTS_OPT = "agents";
	private static final String WID = "width";
	private static final String LEN = "length";
	private static final String MAP_PATH = "map_path";
	private static final String AGENT_SPEED = "agent_speed"; 
	private static final String AGENT_SEE = "agent_see";
	private static final String AGENT_WIGGLE = "agent_wiggle";
	private static final String AGENT_CHANCE_FWD = "agent_chance_fwd";
	private static final String AGENT_BORED_LIMIT = "agent_bored_limit";
	private static final String TICKS = "ticks";
	
	private static CommandLine line;

	public static void main(String[] args) throws IOException {
		Options options = new Options();

		options.addOption( new Option(SEED_OPT, true, "seed for random") );
		options.addOption( new Option(AGENTS_OPT, true, "number of agents") );
		options.addOption( new Option(WID, true, "size of map x") );
		options.addOption( new Option(LEN, true, "size of map y") );
		options.addOption( new Option(MAP_PATH, true, "map path") );
		options.addOption( new Option(TICKS, true, "number of moves") );

		options.addOption( new Option(AGENT_SPEED, true, "distance per tick (1)") );
		options.addOption( new Option(AGENT_SEE, true, "distance agent can see") );
		options.addOption( new Option(AGENT_WIGGLE, true, "degrees agent may add/sub per tick") );
		options.addOption( new Option(AGENT_CHANCE_FWD, true, "likelyhood that it doesn't turn at random (exept for wiggle)") );
		options.addOption( new Option(AGENT_BORED_LIMIT, true, "How many ticks until it's bored") );
		
		CommandLineParser parser = new DefaultParser();
		try {
			line = parser.parse( options, args);
		} catch (ParseException e) {
			System.out.println(e);
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "MapExploreApp", options );
			return;
		}
		if(line.getOptionValue(SEED_OPT)==null){
			Model.setRandomSeed(1L);
		}
		Map map = null;
		if(line.getOptionValue(MAP_PATH)!=null){
			map = Map.load(line.getOptionValue(MAP_PATH));
		}
		if ( line.getOptionValue(WID)!=null 
				&& line.getOptionValue(LEN)!=null) {
			map = new Map(Integer.parseInt(line.getOptionValue(WID) ), Integer.parseInt(line.getOptionValue(LEN) ));
		}
		int agents = Integer.parseInt(line.getOptionValue(AGENTS_OPT));
		double dirWiggle = Double.parseDouble(line.getOptionValue(AGENT_WIGGLE));
		double chanceFwd = Double.parseDouble(line.getOptionValue(AGENT_CHANCE_FWD));
		for(int i=0; i<agents; i++){
			int x=Model.getRandomIntRange(0, map.getWid());
			int y=Model.getRandomIntRange(0, map.getLen());
			Agent a = new Agent("A", x, y, dirWiggle, chanceFwd, 0, map);
			map.getAgents().add(a);
		}
		int ticks=1000;
		if(line.getOptionValue(TICKS)!=null){
			ticks = Integer.parseInt(line.getOptionValue(TICKS));
		}
		for(int t=0; t<ticks; t++){
			map.tick();
		}
		System.out.println("END");
	}

}
