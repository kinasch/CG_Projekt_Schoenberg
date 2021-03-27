#version 330

layout(location=0) in vec2 eckenAusJava;
layout(location=1) in vec3 farbeAusJava;
out vec3 farbUebertragung;
void main() {
    farbUebertragung = farbeAusJava;

    float winkel = 0.3;
    mat2 rot = mat2(cos(winkel),sin(winkel),-sin(winkel),cos(winkel));

    gl_Position = vec4(rot*eckenAusJava, 0.0, 1.0);
}