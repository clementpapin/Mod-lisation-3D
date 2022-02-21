package test.ply;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import projet_modelisation.ply.Ply;
import projet_modelisation.ply.PlyFormatException;
import projet_modelisation.ply.PlyHeaderException;


public class TestPly{

	//obtention du fichier ply test
	static Ply airplane = new Ply(new File("src/main/resources/exemples/airplane.ply").getAbsolutePath());
	static Ply apple = new Ply(new File("src/main/resources/exemples/apple.ply").getAbsolutePath());
	static Ply cube = new Ply(new File("src/main/resources/exemples/cube.ply").getAbsolutePath());
	
	@BeforeAll
	static void before() {
		try {
			airplane.loadInformations();
			apple.loadInformations();
			cube.loadInformations();
		} catch (PlyFormatException | PlyHeaderException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetFaces() {
		assertEquals(2452, airplane.getNbFaces());
		assertEquals(1704, apple.getNbFaces());
		assertEquals(6, cube.getNbFaces());
	}
	@Test
	void testGetName() {
		assertEquals("Avion", airplane.getName());
		assertEquals("Pomme", apple.getName());
		assertEquals("Cube", cube.getName());
	}
	
	@Test
	void testGetDescription() {
		 assertEquals("un boeing  737-10 le plus gros des boeing", airplane.getDescription());
		 assertEquals("et non ce n'est pas un iPhone", apple.getDescription());
		 assertEquals("un beau cube pour s'amuser", cube.getDescription());
	}
	@Test
	void testGetCreationDate() {
		assertEquals("cree il y a 2 ans", airplane.getDateCreation());
		assertEquals("cree il y a très longtemps", apple.getDateCreation());
		assertEquals("inconnu", cube.getDateCreation());
	}
	@Test
	void testGetAuteur() {
		assertEquals("moi", airplane.getAutor());
		assertEquals("dieu", apple.getAutor());
		assertEquals("platoply", cube.getAutor());
	}

}

