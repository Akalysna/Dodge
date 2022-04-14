package controler;

import java.util.ArrayList;
import java.util.List;

import game.element.Cuby;
import game.element.machine.Machine;
import game.element.zone.Zone;
import game.niveau.Stage;
import game.niveau.World;
import ihm.componant.element.CubyShape;
import ihm.componant.element.ThrowballShape;
import ihm.componant.element.ZoneShape;
import javafx.scene.shape.Shape;
import util.EmptyLevelException;
import util.Position;

public class GameCtrl {

	private World world;

	private Stage currentStage;
	private int indexStage;

	private ArrayList<CubyShape> players;
	private ArrayList<ThrowballShape> throwballs;
	private ArrayList<ZoneShape> zones;


	/**
	 * @param world
	 * @param cubies
	 */
	public GameCtrl(World world, List<Cuby> cubies) {

		this.world = world;
		this.players = new ArrayList<>();
		this.throwballs = new ArrayList<>();
		this.zones = new ArrayList<>();

		if (world.getNbStage() > 0) {
			indexStage = 0;
			currentStage = world.getStage(indexStage);
		}
		
		players.add(new CubyShape(cubies.get(0)));

//		// Cuby en shape
//		for (Cuby cuby : cubies) {
//			System.out.println(cuby);
//			cuby.setPosition(new Position(100, 100));
//			players.add(new CubyShape(cuby));
//		}
		
	}
	
	/**
	 * @throws EmptyLevelException 
	 * 
	 */
	public void start() throws EmptyLevelException {
		
		if(currentStage != null) {
			initStageElement();
		} else 
			throw new EmptyLevelException("Ce monde est null ou ne comporte pas de stage");
	}

	private void initStageElement() {

		this.throwballs.clear();
		this.zones.clear();

		// Machine & Zone
		for (Machine machine : currentStage.getMachines()) {
			throwballs.add(new ThrowballShape(machine));

			for (Zone zone : machine.getZones()) {
				zones.add(new ZoneShape(zone));
			}
		}

		// ----------

		// Cuby

		// TODO Ajouté plusieurs position pour tous les cubys

		// Met à jour la position des cubys pour le stage
		for (CubyShape cubyShape : players) {
			
			cubyShape.setPosition(currentStage.getCubyPos());
			
			cubyShape.xProperty().addListener((obj, oldV, newV) -> positionCubyZone(cubyShape));
			cubyShape.yProperty().addListener((obj, oldV, newV) -> positionCubyZone(cubyShape));
		}
	}

	private void cubyHoveredZone(CubyShape cuby) {

	}

	private void positionCubyZone(CubyShape cuby) {
		
//		System.out.println("Le cuby bouge");
		
		for (ZoneShape zone : zones) {
			
			// Si le cuby entre dans la zone
			if (!Shape.intersect(zone, cuby).getBoundsInLocal().isEmpty()) {

				// Si la zone n'était pas survolé, activé la
				if (!zone.isHovered())
					zone.active();
			}

			else {
				if (zone.isHovered())
					zone.stop();
			}
		}
	}

	/**
	 * 
	 */
	public boolean nextStage() {

		if (indexStage < world.getNbStage() - 1) {
			indexStage++;
			currentStage = world.getStage(indexStage);

			initStageElement();
			return true;
		} else {
			endWorld();
			return false;
		}
	}

	/**
	 * 
	 */
	private void endWorld() {
		// TODO Auto-generated method stub
	}

	// --------------------

	/**
	 * Retourne
	 * 
	 * @return the players
	 */
	public List<CubyShape> getCubys() { return players; }

	/**
	 * Retourne
	 * 
	 * @return the throwballs
	 */
	public List<ThrowballShape> getThrowballs() { return throwballs; }

	/**
	 * Retourne
	 * 
	 * @return the zones
	 */
	public List<ZoneShape> getZones() { return zones; }



}
