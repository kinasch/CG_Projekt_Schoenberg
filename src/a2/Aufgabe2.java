package a2;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;

public class Aufgabe2 extends AbstractOpenGLBase {

	private int ecken;

	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		// folgende Zeile läd automatisch "aufgabe2_v.glsl" (vertex) und "aufgabe2_f.glsl" (fragment)
		ShaderProgram shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);

		float [] dreiecksKoordinaten = new float[]{
				0.0f,0.0f,
				0.5f,0.0f,
				0.0f,0.5f,
				0.0f,0.0f,
				-0.5f,0.0f,
				0.0f,-0.5f
		};
		this.ecken = (dreiecksKoordinaten.length/2);
		VBOAnleger(dreiecksKoordinaten,0,2);

		float [] dreiecksFarben = new float[]{
				1.0f,0.0f,0.0f,
				0.0f,1.0f,0.0f,
				0.0f,0.0f,1.0f,
				1.0f,0.0f,0.0f,
				0.0f,1.0f,0.0f,
				0.0f,0.0f,1.0f
		};
		VBOAnleger(dreiecksFarben,1,3);
	}

	@Override
	public void update() {
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT); // Zeichenfläche leeren

		// hier vorher erzeugte VAOs zeichnen
		glDrawArrays(GL_TRIANGLES, 0, this.ecken);
	}

	@Override
	public void destroy() {
	}

	public void VBOAnleger(float[] VBOarray, int indexVAO, int size){
		glBindBuffer(GL_ARRAY_BUFFER, glGenBuffers());

		glBufferData(GL_ARRAY_BUFFER, VBOarray, GL_STATIC_DRAW);
		glVertexAttribPointer(indexVAO, size, GL_FLOAT,false, 0, 0);
		glEnableVertexAttribArray(indexVAO);
	}
}
