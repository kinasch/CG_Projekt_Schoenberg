#version 330

layout(location=0) in vec4 eckenAusJava;
layout(location=2) in vec2 uvAusJava;

uniform mat4 projektion;
uniform mat4 meineMatrix;
uniform float texAni;
uniform mat4 view;

//out vec3 farbUebertragung;
out vec2 uv;
out float ww;


void main() {
    //farbUebertragung = farbeAusJava;
    ww = texAni;
    uv = uvAusJava;
    gl_Position= projektion * view * meineMatrix * eckenAusJava;
}