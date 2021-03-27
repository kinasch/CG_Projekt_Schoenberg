#version 330

out vec3 pixelFarbe;
in vec3 farbUebertragung;
void main(){
    pixelFarbe = farbUebertragung;
}