#version 330

layout(location=0) in vec3 eckenAusJava;
layout(location=1) in vec2 uvAusJava;
layout(location=2) in vec3 normalenAusJava;

uniform mat4 projektion;
uniform mat4 meineMatrix;
uniform mat4 view;

out vec2 uv;

mat3 normalMatrix;
out vec3 normale;
out vec3 p;

void main() {
    uv = uvAusJava;
    normalMatrix = transpose(inverse(mat3(meineMatrix)));

    normale = normalMatrix * normalenAusJava;
    p = vec3(meineMatrix*vec4(eckenAusJava,1.0));

    gl_Position =  projektion * view  * meineMatrix * vec4(eckenAusJava,1.0);
}