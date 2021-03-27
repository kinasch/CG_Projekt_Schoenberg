package projekt;

import java.util.Arrays;

//Alle Operationen ändern das Matrixobjekt selbst und geben das eigene Matrixobjekt zurück
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Matrix4 {

	float [][] intern = new float[4][4];

	public Matrix4() {
		// TODO mit der Identitätsmatrix initialisieren
		for(int spalte=0; spalte<this.intern.length;spalte++){
			for(int zeile=0; zeile<this.intern.length; zeile++){
				if(spalte == zeile){
					this.intern[spalte][zeile] = 1;

				} else{
					this.intern[spalte][zeile] = 0;
				}
			}
		}
	}

	public Matrix4(Matrix4 copy) {
		// TODO neues Objekt mit den Werten von "copy" initialisieren
		for(int spalte=0; spalte<this.intern.length;spalte++){
			for(int zeile=0; zeile<this.intern.length; zeile++){
				this.intern[spalte][zeile] = copy.intern[spalte][zeile];
			}
		}
	}

	public Matrix4(float near, float far) {
		// near = d; far = f
		// TODO erzeugt Projektionsmatrix mit Abstand zur nahen Ebene "near" und Abstand zur fernen Ebene "far", ggf. weitere Parameter hinzufügen
		for(int spalte=0; spalte<this.intern.length;spalte++){
			for(int zeile=0; zeile<this.intern.length; zeile++){
				if(spalte == zeile){
					this.intern[spalte][zeile] = 1;

				} else{
					this.intern[spalte][zeile] = 0;
				}
			}
		}

		changeValues(0,0,near);
		changeValues(1,1,near);
		changeValues(2,2,((-far-near)/(far-near)));
		changeValues(3,2,((-2*near*far)/(far-near)));
		changeValues(2,3,-1);
		changeValues(3,3,0);
	}

	public Matrix4 multiply(Matrix4 other) {
		// TODO hier Matrizenmultiplikation "this = other * this" einfügen (Zeilen * Spalten)
		float[][] temp = new float[4][4];
		for(int spalte=0; spalte<this.intern.length;spalte++){
			for(int zeile=0; zeile<this.intern.length; zeile++){
				for(int i = 0;i<this.intern.length; i++){
					temp[spalte][zeile] += other.intern[i][zeile] * this.intern[spalte][i];
				}
			}
		}

		for(int spalte=0; spalte<this.intern.length;spalte++){
			for(int zeile=0; zeile<this.intern.length; zeile++){
				intern[spalte][zeile] = temp[spalte][zeile];
			}
		}

		return this;
	}

	public Matrix4 translate(float x, float y, float z) {
		// TODO Verschiebung um x,y,z zu this hinzufügen
		float[] values = {x,y,z};
		Matrix4 trans = new Matrix4();
		for(int i = 0; i<3; i++){
			trans.changeValues(3,i,values[i]);
		}
		return this.multiply(trans);
	}

	public Matrix4 scale(float uniformFactor) {
		// TODO gleichmäßige Skalierung um Faktor "uniformFactor" zu this hinzufügen
		Matrix4 scaleU = new Matrix4();
		for(int i = 0; i<this.intern.length-1; i++){
			scaleU.changeValues(i,i,uniformFactor);
		}
		return this.multiply(scaleU);
	}

	public Matrix4 scale(float sx, float sy, float sz) {
		// TODO ungleichförmige Skalierung zu this hinzufügen
		float[] values = {sx,sy,sz};
		Matrix4 scaleNU = new Matrix4();
		for(int i = 0; i<3; i++){
			scaleNU.changeValues(i,i,values[i]);
		}
		return this.multiply(scaleNU);
	}

	public Matrix4 rotateX(float angle) {
		// TODO Rotation um X-Achse zu this hinzufügen
		angle = (float)Math.toRadians(angle);
		// 						1,1						1,2						2,1						2,2
		float[] values = {(float)Math.cos(angle),(float)Math.sin(angle),(float)(-Math.sin(angle)),(float)Math.cos(angle)};
		Matrix4 rotX = new Matrix4();
		/*for(int i = 0; i<4; i++){
			rotX.changeValues(i,i,values[i]);
		}*/

		// unsauber
		rotX.changeValues(1,1,values[0]);
		rotX.changeValues(1,2,values[1]);
		rotX.changeValues(2,1,values[2]);
		rotX.changeValues(2,2,values[3]);


		return this.multiply(rotX);
	}

	public Matrix4 rotateY(float angle) {
		// TODO Rotation um Y-Achse zu this hinzufügen
		angle = (float)Math.toRadians(angle);
		float[] values = {(float)Math.cos(angle),(float)Math.sin(angle),(float)(-Math.sin(angle)),(float)Math.cos(angle)};
		Matrix4 rotY = new Matrix4();
		/*for(int i = 0; i<4; i++){
			rotX.changeValues(i,i,values[i]);
		}*/

		// unsauber
		rotY.changeValues(0,0,values[0]);
		rotY.changeValues(0,2,values[1]);
		rotY.changeValues(2,0,values[2]);
		rotY.changeValues(2,2,values[3]);


		return this.multiply(rotY);

	}

	public Matrix4 rotateZ(float angle) {
		// TODO Rotation um Z-Achse zu this hinzufügen
		angle = (float)Math.toRadians(angle);
		float[] values = {(float)Math.cos(angle),(float)Math.sin(angle),(float)(-Math.sin(angle)),(float)Math.cos(angle)};
		Matrix4 rotZ = new Matrix4();
		/*for(int i = 0; i<4; i++){
			rotX.changeValues(i,i,values[i]);
		}*/

		// unsauber
		rotZ.changeValues(0,0,values[0]);
		rotZ.changeValues(0,1,values[1]);
		rotZ.changeValues(1,0,values[2]);
		rotZ.changeValues(1,1,values[3]);


		return this.multiply(rotZ);
	}

	public float[] getValuesAsArray() {
		float[] intern1d = new float[16];
		for(int spalte=0; spalte<this.intern.length;spalte++){
			for(int zeile=0; zeile<this.intern.length; zeile++){
				intern1d[spalte*4+zeile]=this.intern[spalte][zeile];
			}
		}
		// TODO hier Werte in einem Float-Array mit 16 Elementen (spaltenweise gefüllt) herausgeben
		return intern1d;
	}

	public void changeValues(int spalte, int zeile, float value){
		this.intern[spalte][zeile] = value;
	}
}
