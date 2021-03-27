#version 330
//author: Janik Schönberg

// Faerben des Pixels durch Nutzen einer "out"-Variable
out vec3 pixelFarbe;
// Variable zum Eingeben der Farbe oder Zwischenspeichern
vec3 farbe;

/* 
	Funktion die eine Kreisflaeche faerbt, mit:
	mittex und mittey als Koordinaten des Kreismittelpunkts
	radius als Kreisradius
	pixelXY als momentan angesteuerter Pixel der Flaeche
	farbeC als Farbe der Kreisflaeche
*/
void kreis (float mittex, float mittey, float radius, vec2 pixelXY, vec3 farbeC){
	// Abfrage ob betrachteter Pixel Teil der Kreisflaeche ist,
	// wenn ja, wird er mit in farbeC gefaerbt
    if(distance(vec2(mittex,mittey),pixelXY)<radius){
        pixelFarbe = farbeC;
    }
}


/*
	Funktion die eine Rechtecksflaeche faerbt, mit
	anfang als Anfangspunkt/-vektor
	ende als Endpunkt/-vektor
	pixelXY als momentan angesteuerter Pixel der Flaeche
	farbeC als Farbe der Rechtecksflaeche
*/
void rechteck (vec2 anfang, vec2 ende, vec2 pixelXY, vec3 farbeC){
	// Abfrage ob betrachteter Pixel Teil der Rechtecksflaeche ist,
	// wenn ja, wird er mit in farbeC gefaerbt
    if(pixelXY.x > anfang.x && pixelXY.x<ende.x && pixelXY.y>anfang.y && pixelXY.y<ende.y){
        pixelFarbe = farbeC;
    }
}

void main(){

	// Entfernen der "//" mit STRG+/(NUMPAD) und der "/**/" mit STRG+SHIFT+/(NUMPAD)


	// pixelXY: momentan betrachteter Pixel, wird sich aus Bildflaeche durch eine gl_ Funktion genommen
    vec2 pixelXY = gl_FragCoord.xy;
	// Einfaerben des Hintergrunds
    pixelFarbe = vec3(0.0,0.0,1.0);
	// 
    vec2 anfang,ende;

    // Aufgabe 1
    /*
	//Setzen der Farbe, Anfangs- und Endkoordinaten
    farbe = vec3(1.0,0.5,0.5);
	anfang = vec2(0.0,0.0);
    ende = vec2(100.0,200.0);
    rechteck(anfang,ende,pixelXY,farbe);
	*/


    // Aufgabe 2
    /*
    farbe = vec3(1.0,0.0,1.0);
    kreis(350.0,350.0,200.0,pixelXY,farbe);
	*/


    // Aufgabe 3
    /*
	farbe = vec3(1.0,0.7,0.1);
    a = vec2(0.0,0.0);
    e = vec2(70.0,70.0);
    rechteck(anfang,ende,pixelXY,farbe);

    farbe = vec3(0.5,0.2,0.3);
    a = vec2(300.0,600.0);
    e = vec2(600.0,700.0);
    rechteck(anfang,ende,pixelXY,farbe);

    farbe = vec3(0.0,0.0,0.8);
    kreis(350.0,350.0,60.0,pixelXY,farbe);

    farbe = vec3(1.0,1.0,1.0);
    kreis(90.0,600.0,10.0,pixelXY,farbe);
	*/


    // Aufgabe 4
	// Winkel (w) in radiant
	/*float w = 0.2;
    anfang = vec2(100.0,100.0);
    ende = vec2(200.0,200.0);
    farbe = vec3(1.0,1.0,1.0);
	// "Rotation der Flaeche": man dreht die Flaeche, faerbt die Pixel ein und dreht die Flaeche zurueck
    mat2 m = mat2(cos(w),sin(w),-sin(w),cos(w));
    pixelXY = pixelXY * m;       //vec2((pixelXY.x*cos(w)-pixelXY.y*sin(w)),((pixelXY.y*cos(w)+pixelXY.x*sin(w))));
    rechteck(anfang,ende,pixelXY,farbe);*/


    // Aufgabe 5
	/*
		Idee hinter dieser Aufgabe:
			Man erstellt eine Gerade mit dem selben Abstand wie zwischen anfang und ende,
			bloss ist der Endpunkt(Zwischen) dieser auf der selben x Koordinate (+1) wie der Anfangs.
			Dieses eigentliche Rechteck mit der Pixelstärke 1 wird dann rotiert
	*/
    /*
	// Winkel (winkel) in radiant
	float winkel;
    ende = vec2(100.0,200.0);
    anfang = vec2(0.0,0.0);
    float dist = distance(anfang,ende);

    vec2 endeZwischen = vec2(anfang.x+1,anfang.y+dist);
        // x+1 ist ein experimenteller Wert, der die "Strichstaerke" versinnbildlicht und momentan noch unzufriedenstellende Ergebnisse produziert
    float dist2 = distance(ende,endeZwischen);
    winkel = acos(((2*dist*dist-dist2*dist2)/(2*dist*dist)));
    farbe = vec3(1.0,1.0,1.0);
    pixelXY = vec2((pixelXY.x*cos(winkel)-pixelXY.y*sin(winkel)),((pixelXY.y*cos(winkel)+pixelXY.x*sin(winkel))));
    rechteck(anfang,endeZwischen,pixelXY,farbe);
	*/
}