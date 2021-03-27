package projekt;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;
import java.util.List;

public class Projekt extends AbstractOpenGLBase {

	private static String title = "Kamerakontrolle [W/S: Zoom+/- | A/D: links/rechts | Space: Zuruecksetzen]";

	// Translationsmatrizen der einzelnen Objekte
	// mit m für die Pyramide, m2 für den Tetraeder und m3 für den Ball
	Matrix4 m = new Matrix4();
	Matrix4 m2 = new Matrix4();
	Matrix4 m3 = new Matrix4();
	// Viewmatrix für die Kamerasteuerung
	Matrix4 view = new Matrix4();
	// Bestimmung der Ecken für das eingelesene Objekt
	private int ecken;
	//
	private ShaderProgram shaderProgram;
	private ShaderProgram shaderProgramPhong;
	private ShaderProgram shaderProgramPhongText;
	// Teil der Tastaturerfassung, Quelle siehe KeyboardHandler
	private GLFWKeyCallback keyCallback;
	// IDs der VAO (Nummerierung wie für Translationsmatrizen) und der Texturen, mit 1 für die Pyramide und 2 für den Ball
	private int vaoId1,vaoId2,vaoId3,texID2,texID1;

	public static void main(String[] args) { new Projekt().start(title, 700, 700);
	}

	@Override
	protected void init() {

		glfwSetKeyCallback(super.getWindow(), keyCallback = new KeyboardHandler());

		shaderProgram = new ShaderProgram("projekt");
		shaderProgramPhong = new ShaderProgram("phong");
		shaderProgramPhongText = new ShaderProgram("phongtex");

		/** Quelle der Wassertextur
		 * https://www.deviantart.com/simplybackgrounds/art/Water-Texture-49283686
		 */
		Texture woodTexture = new Texture("water.jpg",1,false);
		texID1 = woodTexture.getId();

		/** Quelle der Holztextur
		 * https://www.photos-public-domain.com/2018/08/23/wood-grain-texture-4/
		 */
		Texture redTexture = new Texture("wood2.jpg",128,false);
		texID2 = redTexture.getId();

		// Übergabe der Projektionsmatrix (p) an alle Shaderprogramme
		Matrix4 p = new Matrix4(1,200);
		int loc = glGetUniformLocation(shaderProgram.getId(), "projektion");
		glUseProgram(shaderProgram.getId());
		glUniformMatrix4fv(loc,false,p.getValuesAsArray());

		int loc2 = glGetUniformLocation(shaderProgramPhong.getId(), "projektion");
		glUseProgram(shaderProgramPhong.getId());
		glUniformMatrix4fv(loc2,false,p.getValuesAsArray());

		int loc3 = glGetUniformLocation(shaderProgramPhongText.getId(), "projektion");
		glUseProgram(shaderProgramPhongText.getId());
		glUniformMatrix4fv(loc3,false,p.getValuesAsArray());

		// Übertragen der Arraywerte an den Grafikspeicher
		pyramideMitTextur();
		tetraederMitPhong();
		ballMitPhongUndTextir();

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
	}

	public void pyramideMitTextur(){
		glUseProgram(shaderProgram.getId());
		vaoId1 = glGenVertexArrays();
		glBindVertexArray(vaoId1);

		float [] dreiecksKoordinaten = new float[]{
				-1,0,0,
				0,-1,0,		// C,D,E
				0,0,1,
				0,-1,0,
				1,0,0,		// D,A,E
				0,0,1,
				1,0,0,
				0,1,0,		// A,B,E
				0,0,1,
				0,1,0,
				-1,0,0,		//B,C,E
				0,0,1,
				1,0,0,
				0,-1,0,		//A,D,C
				-1,0,0,
				-1,0,0,
				0,1,0,		//C,B,A
				1,0,0
		};
		VBOAnleger(dreiecksKoordinaten,0,3);

		float[] textureCoords = {
				0,1,	//C
				1,1,	//D
				0,0,	//E
				1,1,	//D
				0,0,	//A
				0,1,	//E
				0,0,	//A
				1,0,	//B
				0,1,	//E
				1,0,	//B
				0,1,	//C
				0,0,	//E
				0,0,	//A
				1,1,	//D
				0,1,	//C
				0,1,	//C
				1,0,	//B
				0,0		//A
		};
		VBOAnleger(textureCoords,2,2);

		glActiveTexture(texID1);
		glBindTexture(GL_TEXTURE_2D, texID1);
		// Veränderung der Texturwiederholung für flüssigere "Animation"
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
		// Filtermodus
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
		glGenerateMipmap(GL_TEXTURE_2D);

	}

	public void ballMitPhongUndTextir(){
		List<float[]> objList = new ArrayList<>();
		objList = OBJLoader.objDaten("es");

		glUseProgram(shaderProgramPhongText.getId());
		vaoId3 = glGenVertexArrays();
		glBindVertexArray(vaoId3);

		float[] eckenArray = objList.get(0);
		this.ecken = (eckenArray.length/3);
		VBOAnleger(eckenArray,0,3);

		float[] uvs = objList.get(1);
		VBOAnleger(uvs,1,2);

		float[] normalen = objList.get(2);
		VBOAnleger(normalen,2,3);
		glActiveTexture(texID2);
		glBindTexture(GL_TEXTURE_2D, texID2);

		// Filtermodus
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glGenerateMipmap(GL_TEXTURE_2D);

	}

	public void tetraederMitPhong(){
			glUseProgram(shaderProgramPhong.getId());
			vaoId2 = glGenVertexArrays();
			glBindVertexArray(vaoId2);

			float [] dreiecksKoordinaten = new float[]{
					-1,0,0,
					0,-1,0,		// C,D,E
					0,0,1,
					0,-1,0,
					1,0,0,		// D,A,E
					0,0,1,
					1,0,0,
					0,-1,0,		//A,D,C
					-1,0,0,
					1,0,0,
					-1,0,0,
					0,0,1
			};
			VBOAnleger(dreiecksKoordinaten,0,3);

			float [] dreiecksFarben = new float[]{
					1.0f,0.0f,0.0f,
					1.0f,0.0f,0.0f,
					1.0f,0.0f,0.0f,
					1.0f,1.0f,0.0f,
					1.0f,1.0f,0.0f,
					1.0f,1.0f,0.0f,
					1.0f,0.0f,1.0f,
					1.0f,0.0f,1.0f,
					1.0f,0.0f,1.0f,
					1.0f,1.0f,1.0f,
					1.0f,1.0f,1.0f,
					1.0f,1.0f,1.0f

			};
			VBOAnleger(dreiecksFarben,1,3);

			float[] normalen = new float[]{
					-1,-1,1,
					-1,-1,1,
					-1,-1,1,
					1,-1,1,
					1,-1,1,
					1,-1,1,
					0,0,-1,
					0,0,-1,
					0,0,-1,
					0,0,1,
					0,0,1,
					0,0,1
			};
			VBOAnleger(normalen,2,3);
	}

	// Winkel für Translationen
	public float w=0;
	@Override
	public void update() {

		// KeyHandling
		if(KeyboardHandler.isKeyDown(87)){ //W
			view.translate(0f,0f,0.01f);
		}
		if(KeyboardHandler.isKeyDown(83)){ //S
			view.translate(0f,0f,-0.01f);
		}
		if(KeyboardHandler.isKeyDown(65)){ //A
			view.translate(0.01f,0f,0f);
		}
		if(KeyboardHandler.isKeyDown(68)){ //D
			view.translate(-0.01f,0f,0f);
		}
		if(KeyboardHandler.isKeyDown(32)){ //Space
			view = new Matrix4();
		}

		// Winkel
		w+=0.1f;

		m = new Matrix4();
		m.rotateY(-w).rotateZ(w);
		m.translate(1f,1f,-6f);

		m2 = new Matrix4();
		m2.rotateX(w).rotateZ(w);
		m2.scale((float)Math.sin(w*0.1)+1);
		m2.translate((float)Math.sin(w*0.02)*2,(float)Math.cos(w*0.02)*2,1);

		m3 = new Matrix4();
		m3.rotateZ(-w*10).rotateX(w*10);
		m3.scale(0.5f);
		m3.translate(-1,-1,-2f);
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		int loc = glGetUniformLocation(shaderProgram.getId(), "meineMatrix");
		int floc = glGetUniformLocation(shaderProgram.getId(),"texAni");
		int vloc = glGetUniformLocation(shaderProgram.getId(),"view");
		glUseProgram(shaderProgram.getId());
		glUniformMatrix4fv(loc,false,m.getValuesAsArray());
		glUniform1f(floc,w);
		glUniformMatrix4fv(vloc,false,view.getValuesAsArray());
		glBindVertexArray(vaoId1);
		glActiveTexture(texID1);
		glBindTexture(GL_TEXTURE_2D, texID1);
		glDrawArrays(GL_TRIANGLES, 0, 18);


		m2 = m2.multiply(m); // Dadurch bewegt sich das Tetraeder in den Lokalkoordinaten der Pyramide
		int loc2 = glGetUniformLocation(shaderProgramPhong.getId(),"meineMatrix");
		vloc = glGetUniformLocation(shaderProgramPhong.getId(),"view");
		glUseProgram(shaderProgramPhong.getId());
		glUniformMatrix4fv(loc2,false,m2.getValuesAsArray());
		glUniformMatrix4fv(vloc,false,view.getValuesAsArray());
		glBindVertexArray(vaoId2);
		glDrawArrays(GL_TRIANGLES, 0, 18);


		int loc3 = glGetUniformLocation(shaderProgramPhongText.getId(),"meineMatrix");
		vloc = glGetUniformLocation(shaderProgramPhongText.getId(),"view");
		glUseProgram(shaderProgramPhongText.getId());
		glUniformMatrix4fv(loc3,false,m3.getValuesAsArray());
		glUniformMatrix4fv(vloc,false,view.getValuesAsArray());
		glBindVertexArray(vaoId3);
		glActiveTexture(texID2);
		glBindTexture(GL_TEXTURE_2D, texID2);
		glDrawArrays(GL_TRIANGLES, 0, ecken);
	}

	@Override
	public void destroy() {
	}

	// Funktion zum Anlegen eines VBOs
	public void VBOAnleger(float[] VBOarray, int indexVBO, int size){
		glBindBuffer(GL_ARRAY_BUFFER, glGenBuffers());

		glBufferData(GL_ARRAY_BUFFER, VBOarray, GL_STATIC_DRAW);
		glVertexAttribPointer(indexVBO, size, GL_FLOAT,false, 0, 0);
		glEnableVertexAttribArray(indexVBO);
	}
}
